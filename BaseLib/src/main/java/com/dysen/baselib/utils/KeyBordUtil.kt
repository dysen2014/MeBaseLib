package com.dysen.baselib.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
/**
 * dysen.
 * dy.sen@qq.com    9/16/20  11:11 AM

 * Info：输入框弹出管理
 */

object KeyBordUtil {
    /**
     * 显示和隐藏软键盘 View ： EditText、TextView isShow : true = show , false = hide
     *
     * @param context
     * @param view
     * @param isShow
     */
    fun popSoftKeyboard(context: Context, view: View,
                        isShow: Boolean) {
        val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isShow) {
            view.requestFocus()
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } else {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    fun showSoftKeyboard(view: View) {
        val context = view.context
        val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    fun hideSoftKeyboard(view: View) {
        val context = view.context
        val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}