package com.example.projectforworktesting.webview

import android.content.res.Resources.NotFoundException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WebViewModel: ViewModel(), ViewModelProvider.Factory {

    private val _uiState = MutableStateFlow<WebUiState>(WebUiState.Loading())
    val uiState = _uiState.asStateFlow()
    private val firebaseRemoteConfig = Firebase.remoteConfig


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WebViewModel::class.java)){
            val viewModel = WebViewModel()
            viewModel.init()
            return viewModel as T
        }
        throw NotFoundException("WebViewModel not found")
    }

    private fun init() {
        val url = firebaseRemoteConfig.getString("url")
        if (url.isEmpty()){
            _uiState.value = WebUiState.Error(Exception("Url is empty"))
        }
        else{
            _uiState.value = WebUiState.Ready(url)
        }
    }

    open class WebUiState(){
        class Loading(): WebUiState()
        class Ready<T>(val data: T): WebUiState()
        class Error( val e: Exception) : WebUiState()
    }
}