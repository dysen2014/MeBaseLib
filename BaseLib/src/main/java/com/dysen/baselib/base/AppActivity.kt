package com.dysen.baselib.base

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.dysen.baselib.R
import com.dysen.baselib.utils.NetUtils
import com.dysen.baselib.utils.Tools.obtainNoNullText
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * dysen.
 * dy.sen@qq.com    11/6/20  9:57 AM

 * Info：
 */
open class AppActivity : AppCompatActivity() {

    protected var defaultContent = "--"

    protected fun transAty(cls: Class<*>?) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    protected fun transAty(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, cls)
        intent.putExtra("data", bundle)
        startActivity(intent)
    }

    protected fun transAty4Result(cls: Class<*>?) {
        val intent = Intent(this, cls)
        startActivityForResult(intent, RESULT_OK)
    }

    protected fun transAty4Result(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, cls)
        intent.putExtra("data", bundle)
        startActivityForResult(intent, RESULT_OK)
    }

    fun setIsEnable(view: View?) {
        setIsEnable(view, false)
    }

    fun setIsEnable(view: View?, falg: Boolean) {
        view?.isEnabled = falg
    }

    fun setGone(view: View?) {
        view?.visibility = View.GONE
    }

    fun setVisible(view: View?) {
        view?.visibility = View.VISIBLE
    }

    fun setIsVisible(view: View?) {
        setIsVisible(view, false)
    }

    fun setIsVisible(view: View?, flag: Boolean = false) {
        view?.visibility = if (flag) View.VISIBLE else View.GONE
    }

    fun setIsInVisible(view: View?) {
        setIsInVisible(view, false)
    }

    fun setIsInVisible(view: View?, flag: Boolean = false) {
        view?.visibility = if (flag) View.VISIBLE else View.INVISIBLE
    }

    fun setInvisible(view: View?) {
        view?.visibility = View.INVISIBLE
    }

    fun gText(textView: TextView): String {
        return textView.text.toString()
    }

    fun gText(editText: EditText): String {
        return editText.text.toString()
    }

    protected fun sText(textView: TextView, content: String? = defaultContent): AppActivity {
        if (isNull(textView)) {
            return this
        }
        textView.text = obtainNoNullText(content, "")
        return this
    }

    protected fun sText(textView: EditText, content: String? = defaultContent): AppActivity {
        if (isNull(textView)) {
            return this
        }
        textView.setText(obtainNoNullText(content, ""))
        return this
    }

    protected fun sTextColor(textView: TextView, colorId: Int): AppActivity {
        if (isNull(textView)) {
            return this
        }
        textView.setTextColor(colorId)
        return this
    }

    protected fun sTextSize(textView: TextView, size: Float): AppActivity {
        if (isNull(textView)) {
            return this
        }
        textView.textSize = size
        return this
    }

    protected fun sHintText(textView: TextView, content: String?): AppActivity {
        if (isNull(textView)) {
            return this
        }
        textView.hint = obtainNoNullText(content!!, "")
        return this
    }

    protected fun sHintTextColor(textView: TextView, colorId: Int): AppActivity {
        if (isNull(textView)) {
            return this
        }
        textView.setHintTextColor(colorId)
        return this
    }

    fun isNull(obj: Any?): Boolean {
        return obj == null
    }

    fun isNotNull(obj: Any?): Boolean {
        return !isNull(obj)
    }

    fun isEmpty(str: String?): Boolean {
        return TextUtils.isEmpty(str)
    }

    fun showTip(str: String) {
        var makeText = Toast.makeText(this, str, Toast.LENGTH_LONG)
        makeText.setText(str)
        makeText.duration = Toast.LENGTH_SHORT
        makeText.setGravity(Gravity.CENTER, 0, 0)
        makeText.show()
//        showTip(str, Toast.LENGTH_SHORT)
    }

    fun showTip(str: String, duration: Int) {
        if (duration == 1)
            ToastUtils.showLong(str)
        else
            ToastUtils.showShort(str)
    }

    fun showTipInput(str: String) {
        showTip("请输入${str}")
    }

    fun showTipSelect(str: String) {
        showTip("请选择${str}")
    }

    fun showTipInput(str: String, endStr: String = "") {
        showTip("请输入${str}${endStr}")
    }

    fun showTipSelect(str: String, endStr: String = "") {
        showTip("请选择${str}${endStr}")
    }

    fun showTipFormatInvalid(str: String) {
        showTip("${str}格式无效，请重新输入")
    }

    fun showErrorMsg(e: Throwable) {

        var srt_message = ""

        if (!NetUtils.isNetConnected(this) || e is UnknownHostException) {
            srt_message = "网络中断，请检查您的网络状态"
        } else if (e is ConnectException) {
            srt_message = "请求超时，请检查您的网络状态"
        } else if (e is SocketTimeoutException) {
            srt_message = "响应超时，请检查您的网络状态"
        } else
            srt_message = getString(R.string.requestErrorPleaseRetry)
        showTip(srt_message)
    }
}