package com.example.projectforworktesting.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.projectforworktesting.databinding.FragmentWebViewBinding
import kotlinx.coroutines.launch


class WebViewFragment : Fragment() {

    private lateinit var url: String
    private var binding: FragmentWebViewBinding? = null
    private lateinit var backPresetCallback: OnBackPressedCallback
    private lateinit var viewModel: WebViewModel

    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString("url", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        webView = binding?.webView!!
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, WebViewModel())[WebViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
                 if (it is WebViewModel.WebUiState.Ready<*>){
                     webViewInitSetting(savedInstanceState, it.data as String)
                 }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        backPresetCallback.remove()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    private val webClientCallback = object : AppWebViewClient.WebViewCallBack {
        override fun loading() {

        }
        override fun finished() {
            showWebUi()
        }

    }

    private fun showWebUi(){
        binding?.let {
            it.progressBar.visibility = View.INVISIBLE
            it.webView.visibility = View.VISIBLE
        }
    }

    private fun webViewInitSetting(savedInstanceState: Bundle?, url: String) {
        val webClient = AppWebViewClient()
        webClient.setListener(webClientCallback)
        webView.webViewClient = webClient
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        if (savedInstanceState != null){
            webView.restoreState(savedInstanceState)
        }
        else {
            webView.loadUrl(url)
        }


        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

        backPresetCallback = requireActivity().onBackPressedDispatcher.addCallback {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                ///TO DO
            }
        }
    }




}