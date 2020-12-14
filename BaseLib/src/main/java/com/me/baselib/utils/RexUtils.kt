package com.me.baselib.utils

import com.dysen.baselib.utils.LogUtils
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * dysen.
 * dy.sen@qq.com    10/29/20  3:21 PM

 * Info：
 */
object RexUtils {

    /**
     *强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)：
     *
     * ^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$
     *
     * 帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
     * 密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)：^[a-zA-Z]\w{5,17}$
     */

    fun checkPwd(password: String): Boolean {
        val regex = "^(?=.*\\d)(?=.*[._!@#\$%])(?=.*[a-zA-Z]).{8,20}\$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(password)
        val isMatch: Boolean = m.matches()
        LogUtils.i("RexUtils", "isPassword: 是否密码正则匹配$isMatch")

        return isMatch
    }
}