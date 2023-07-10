package com.example.projectforworktesting.webview

import android.webkit.WebView
import android.webkit.WebViewClient

class AppWebViewClient : WebViewClient() {

    interface WebViewCallBack{
        fun loading()
        fun finished()
    }


    private var callback: WebViewCallBack? = null

    fun setListener(callBack: WebViewCallBack){
        this.callback = callBack
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        callback?.finished()
    }
}