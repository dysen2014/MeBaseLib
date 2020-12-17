package com.dysen.baselib.utils

import android.app.Activity
import android.content.Intent
import com.dysen.baselib.ui.country_code.CountryActivity

/**
 * dysen.
 * dy.sen@qq.com    11/6/20  9:30 AM

 * Info：应用程序UI工具包：封装UI相关的一些操作
 */
object UIHelper {

    val TAG = "UIHelper"

    val RESULT_OK = 0x00
    val REQUEST_CODE = 0x01

    var REQUEST_CODE_GET_COUNTRY = 10001
    val RESULT_CODE_GET_COUNTRY = 20001

    /**
     * 选择地区
     * */
    fun showCountry(context: Activity) {
        val intent = Intent(context, CountryActivity::class.java)
        intent.putExtra("type", "0")
        context.startActivity(intent)
    }

    fun showChooseCountry(context: Activity) {
        val intent = Intent(context, CountryActivity::class.java)
        context.startActivityForResult(intent, REQUEST_CODE_GET_COUNTRY)
    }
}