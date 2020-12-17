package com.dysen.baselib.utils

import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

/**
 * dysen.
 * dy.sen@qq.com    10/15/20  09:11 AM
 *
 * Info：
 */
object WebUtils {
    fun setWebViewHtml(view: WebView, html: String) {
        view.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
    }

    fun getHtmlData(bodyHTML: String): String {
        val head =
            "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto!important;}</style></head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }

    fun setWebViewHtml2(view: WebView, html: String) {
        //final String css_style = "<style>* {font-size:14px;line-height:20px;} p{color:#5D5D5D;} ol,li{ margin:0;padding:0;}</style>";
        val css_style = "<style>* {font-size:14px;line-height:20px;} p{color:#5D5D5D;} ol,li{ margin:0;padding-left:10px}</style>"
        val head =
            "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto!important;}</style></head>"
        val finalHtml = "<html>$head<body>$css_style$html</body></html>"
        view.loadDataWithBaseURL(null, finalHtml, "text/html", "utf-8", null)
    }

    fun loadUrl(webView: WebView, url: String, webProgress: ProgressBar? = null) {
        var webSettings = webView.settings

        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportMultipleWindows(true)
        webView.webViewClient = WebViewClient()

        webSettings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        webSettings.domStorageEnabled = true //设置适应Html5 重点是这个设置

        webSettings.setAppCacheMaxSize(1024 * 1024 * 8.toLong())
        webSettings.javaScriptEnabled = true
        webSettings.setRenderPriority(WebSettings.RenderPriority.NORMAL)
        webSettings.blockNetworkImage = false

        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。

        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放

        webSettings.displayZoomControls = false //隐藏原生的缩放控件

//
//        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                webProgress?.progress = newProgress
                Tools.setIsVisible(webProgress, newProgress < 100)
            }
        }
        webView.loadUrl(url)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
//            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW;
        }

    }
}