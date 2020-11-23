package com.dysen.baselib.utils

import android.util.Log
import java.util.regex.Pattern

/**
 * dysen.
 * dy.sen@qq.com    10/29/20  2:59 PM

 * Info：
 */
object RexUtils {
    val TAG = javaClass.simpleName

    /**
     * 强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
     *
     * ^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$
     */

    fun isPassword(password: String): Boolean {
        val regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$"
        val p = Pattern.compile(regex);
        val m = p.matcher(password);
        var isMatch = m.matches();

        Log.i(TAG, "isPassword: 是否密码正则匹配$isMatch");
        return isMatch
    }
}