package com.example.projectforworktesting

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.projectforworktesting.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.util.*


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var sharedPref: SharedPreferences
    private lateinit var navController: NavController

    companion object {
        const val WEB_VIEW_URL = "WebViewUrl"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initFirebaseRemoteConfig()

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val url = sharedPref.getString(WEB_VIEW_URL, "")!!

        if (url.isEmpty()) {
            fetchAndActivateFirebaseConfig()
        } else {
            checkInternetConnection()
        }

    }


    private fun fetchAndActivateFirebaseConfig() {
        try {
            remoteConfig.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    val urlFromRemote = remoteConfig.getString("url")
                    if (urlFromRemote.isEmpty() || checkIsEmu()) {
                        navigateToMock()
                    } else {
                        setStringToSharedPreference(WEB_VIEW_URL, urlFromRemote)
                    }

                } else {
                    throw Exception()
                }
            }
        } catch (e: Exception) {
            navigateToMock()
        }
    }

    private fun navigateToMock() {
        navController
            .navigate(R.id.action_webViewFragment_to_mockViewFragment)
    }

    private fun navigateToNetworkError() {
        navController
            .navigate(R.id.action_webViewFragment_to_internetErrorFragment)
    }


    private fun initFirebaseRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

    }


    private fun setStringToSharedPreference(key: String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }


    private fun isInternet(): Boolean {
        return try {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                capabilities != null
            } else {
                val activeNetwork = connectivityManager.activeNetworkInfo
                activeNetwork?.isConnectedOrConnecting!! && activeNetwork.isAvailable
            }
        } catch (e: Exception) {
            false
        }
    }


    private fun checkInternetConnection() {
        if (!isInternet()) {
            navigateToNetworkError()
        }
    }

    private fun checkIsEmu(): Boolean {
        if (BuildConfig.DEBUG) return false
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND;
        var result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || Build.BRAND.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
        if (result) return true
        result = result || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
        if (result) return true
        result = result || ("google_sdk" == buildProduct)
        return result
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}