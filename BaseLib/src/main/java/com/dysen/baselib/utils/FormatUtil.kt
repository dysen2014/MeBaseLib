package com.dysen.baselib.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
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
        val telRegex: Regex = Regex.fromLiteral("[1][34578]\\d{9}") //"[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
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


    /**
     * 判断身份证格式
     */
    fun isIdCardNo(idNum: String?): Boolean {
        //定义判别用户身份证号的正则表达式（要么是15位或18位，最后一位可以为字母）
        val idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])")
        //通过Pattern获得Matcher
        val idNumMatcher = idNumPattern.matcher(idNum)
        return idNumMatcher.matches()
    }


    /**
     * 验证固话号码
     *
     * @param telephone
     * @return
     */
    fun checkTelephone(telephone: String): Boolean {
        val regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$"
        val p = Pattern.compile(telephone)
        return p.matcher(regex).matches()
    }


    /**
     * 验证固话号码
     *
     * @param telephone
     * @return
     */
    fun checkPwd(telephone: String): Boolean {
        val regex = "^(?:(?=.*[0-9].*)(?=.*[A-Za-z].*)(?=.*[\\W].*))[\\W0-9A-Za-z]{8,16}$"
        val p = Pattern.compile(telephone)

        return p.matcher(regex).matches()
    }


    /**
     * 验证是否由数字和字母组成
     *
     * @param  str 号码
     * @return 验证通过返回true
     */
    fun isNumberAndCharcater(str: String?): Boolean {
        var p: Pattern? = null
        var m: Matcher? = null
        var b = false
        p = Pattern.compile("^[A-Za-z0-9]+$") // 验证是否由数字和字母组成
        m = p.matcher(str)
        b = m.matches()
        return b
    }


    /**
     * 正则：汉字
     */
    const val REGEX_ZH = "^[\\u4e00-\\u9fa5]+$"


    /**
     * 正则：双字节字符(包括汉字在内)
     */
    const val REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]"

    /**
     * 验证是否是汉字
     */
    fun isDouble(str: String?): Boolean {
        var p: Pattern? = null
        var m: Matcher? = null
        var b = false
        p = Pattern.compile(REGEX_DOUBLE_BYTE_CHAR) // 验证是否由数字和字母组成
        m = p.matcher(str)
        b = m.matches()
        return b
    }


    /**
     * 验证是否是汉字
     */
    fun isCode(str: String?): Boolean {
        var p: Pattern? = null
        var m: Matcher? = null
        var b = false
        p = Pattern.compile(REGEX_ZH) // 验证是否由数字和字母组成
        m = p.matcher(str)
        b = m.matches()
        return b
    }


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    fun isChinese(c: Char): Boolean {
        val ub = Character.UnicodeBlock.of(c)
        return ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    fun checkNameChese(name: String): Boolean {
        var res = true
        val cTemp = name.toCharArray()
        for (i in 0 until name.length) {
            if (!isChinese(cTemp[i])) {
                res = false
                break
            }
        }
        return res
    }

    /**
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    fun checkBankCard(cardId: String): Boolean {
        val bit = getBankCardCheckCode(
            cardId
                .substring(0, cardId.length - 1)
        )
        return if (bit == 'N') {
            false
        } else cardId[cardId.length - 1] == bit
    }

    private fun getBankCardCheckCode(nonCheckCodeCardId: String?): Char {
        val p = Pattern.compile(nonCheckCodeCardId)

        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim { it <= ' ' }.isEmpty() || !p.matcher("\\d+").matches()) {
            // 如果传的不是数据返回N
            return 'N'
        }
        val chs = nonCheckCodeCardId.trim { it <= ' ' }.toCharArray()
        var luhmSum = 0
        var i = chs.size - 1
        var j = 0
        while (i >= 0) {
            var k = chs[i] - '0'
            if (j % 2 == 0) {
                k *= 2
                k = k / 10 + k % 10
            }
            luhmSum += k
            i--
            j++
        }
        return if (luhmSum % 10 == 0) '0' else (10 - luhmSum % 10 + '0'.toInt()).toChar()
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun IDCardValidate(IDStr: String): Boolean {
        var errorInfo = "" // 记录错误信息
        val ValCodeArr = arrayOf(
            "1", "0", "x", "9", "8", "7", "6", "5", "4",
            "3", "2"
        )
        val Wi = arrayOf(
            "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
            "9", "10", "5", "8", "4", "2"
        )
        var Ai = ""
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length != 15 && IDStr.length != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。"
            LogUtils.e("ID:errorInfo=$errorInfo")
            return false
        }
        // =======================(end)========================

        // ================ 数字 除最后一位都为数字 ================
        if (IDStr.length == 18) {
            Ai = IDStr.substring(0, 17)
        } else if (IDStr.length == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15)
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。"
            LogUtils.e("ID:errorInfo=$errorInfo")
            return false
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        val strYear = Ai.substring(6, 10) // 年份
        val strMonth = Ai.substring(10, 12) // 月份
        val strDay = Ai.substring(12, 14) // 月份
        if (isDataFormat("$strYear-$strMonth-$strDay") == false) {
            errorInfo = "身份证生日无效。"
            LogUtils.e("ID:errorInfo=$errorInfo")
            return false
        }
        val gc = GregorianCalendar()
        val s = SimpleDateFormat("yyyy-MM-dd")
        try {
            if (gc[Calendar.YEAR] - strYear.toInt() > 150
                || gc.time.time - s.parse(
                    "$strYear-$strMonth-$strDay"
                ).time < 0
            ) {
                errorInfo = "身份证生日不在有效范围。"
                LogUtils.e("ID:errorInfo=$errorInfo")
                return false
            }
        } catch (e: NumberFormatException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        if (strMonth.toInt() > 12 || strMonth.toInt() == 0) {
            errorInfo = "身份证月份无效"
            LogUtils.e("ID:errorInfo=$errorInfo")
            return false
        }
        if (strDay.toInt() > 31 || strDay.toInt() == 0) {
            errorInfo = "身份证日期无效"
            LogUtils.e("ID:errorInfo=$errorInfo")
            return false
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        val h: MutableMap<String, String> = GetAreaCode()
        if (h[Ai.substring(0, 2)] == null) {
            errorInfo = "身份证地区编码错误。"
            LogUtils.e("ID:errorInfo=$errorInfo")
            return false
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        var TotalmulAiWi = 0
        for (i in 0..16) {
            TotalmulAiWi = (TotalmulAiWi
                    + Ai[i].toString().toInt() * Wi[i].toInt())
        }
        val modValue = TotalmulAiWi % 11
        val strVerifyCode = ValCodeArr[modValue]
        Ai += strVerifyCode
        if (IDStr.length == 18) {
            if (!(Ai == IDStr)) {
                errorInfo = "身份证无效，不是合法的身份证号码"
                LogUtils.e("ID:errorInfo=$errorInfo")
                return false
            }
        } else {
            return true
        }
        // =====================(end)=====================
        return true
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private fun GetAreaCode(): MutableMap<String, String> {
        var map: MutableMap<String, String> = mutableMapOf()
        map["11"] = "北京"
        map["12"] = "天津"
        map["13"] = "河北"
        map["14"] = "山西"
        map["15"] = "内蒙古"
        map["21"] = "辽宁"
        map["22"] = "吉林"
        map["23"] = "黑龙江"
        map["31"] = "上海"
        map["32"] = "江苏"
        map["33"] = "浙江"
        map["34"] = "安徽"
        map["35"] = "福建"
        map["36"] = "江西"
        map["37"] = "山东"
        map["41"] = "河南"
        map["42"] = "湖北"
        map["43"] = "湖南"
        map["44"] = "广东"
        map["45"] = "广西"
        map["46"] = "海南"
        map["50"] = "重庆"
        map["51"] = "四川"
        map["52"] = "贵州"
        map["53"] = "云南"
        map["54"] = "西藏"
        map["61"] = "陕西"
        map["62"] = "甘肃"
        map["63"] = "青海"
        map["64"] = "宁夏"
        map["65"] = "新疆"
        map["71"] = "台湾"
        map["81"] = "香港"
        map["82"] = "澳门"
        map["91"] = "国外"
        return map
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    private fun isDataFormat(str: String): Boolean {
        var flag = false
        // String
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        val regxStr =
            "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$"
        val pattern1 = Pattern.compile(regxStr)
        val isNo = pattern1.matcher(str)
        if (isNo.matches()) {
            flag = true
        }
        return flag
    }

    /**
     * 空值null返回"",防止脏数据奔溃
     */
    fun checkValue(str: String?): String = if (TextUtils.isEmpty(str)) "" else str ?: "--"

    fun checkValue(str: String, len: Int = 10): String = if (TextUtils.isEmpty(str)) "" else if (str.length > len && len >= 3) str.substring(0, len - 3) + "..." else str
}