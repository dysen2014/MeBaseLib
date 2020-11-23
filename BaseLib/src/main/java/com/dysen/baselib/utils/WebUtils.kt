package com.dysen.baselib.utils

import android.webkit.WebView

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
        val head = "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto!important;}</style></head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }

    fun setWebViewHtml2(view: WebView, html: String) {
        //final String css_style = "<style>* {font-size:14px;line-height:20px;} p{color:#5D5D5D;} ol,li{ margin:0;padding:0;}</style>";
        val css_style = "<style>* {font-size:14px;line-height:20px;} p{color:#5D5D5D;} ol,li{ margin:0;padding-left:10px}</style>"
        val head = "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto!important;}</style></head>"
        val finalHtml = "<html>$head<body>$css_style$html</body></html>"
        view.loadDataWithBaseURL(null, finalHtml, "text/html", "utf-8", null)
    }
}