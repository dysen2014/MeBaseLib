package com.me.baselib.utils

import android.text.TextUtils
import java.util.regex.Pattern

/**
 *
 * @email dy.sen@qq.com
 * created by dysen on 2018/9/1 - 下午2:18
 * @info
 */
object FormatUtil {
    /**
     * 验证手机格式
     */
    fun isMobileNO(mobiles: String): Boolean {
        /*
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、178
	    联通：130、131、132、152、155、156、185、186、176
	    电信：133、153、180、189、（1349卫通）、177
	    总结起来就是第一位必定为1，第二位必定为3或5或8或7，其他位置的可以为0-9
		 */
        val telRegex:Regex = Regex.fromLiteral("[1][34578]\\d{9}") //"[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return if (TextUtils.isEmpty(mobiles)) false else mobiles.matches(telRegex)
    }

    /**
     * 判断email格式是否正确
     * @param email
     * @return
     */
    fun isEmail(email: String?): Boolean {
        val str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str)
        val m = p.matcher(email)
        return m.matches()
    }

    /**
     * 判断是否全是数字
     * @param str
     * @return
     */
    fun isNumeric(str: String?): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }
}