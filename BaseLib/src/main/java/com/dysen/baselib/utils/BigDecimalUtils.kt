package com.dysen.baselib.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.regex.Pattern

/**
 * dysen.
 * dy.sen@qq.com    2020/5/27  12:41

 * Info：
 */
object BigDecimalUtils {

    open val funm = createDecimalFormat()

    open val pattern = Pattern.compile("[^-0-9.]")

    /**
     * 金额相加
     * @method
     * @date: 2020/5/21 19:13
     * @author: moran
     * @param valueStr 基础值
     * @param addStr 被加数
     *
     * @return 金额
     */
    fun moneyAdd(valueStr: String, addStr: String): String {

        val value = BigDecimal(formatMoney(valueStr))

        val augend = BigDecimal(formatMoney(addStr))

        return funm.format(value.add(augend))

    }


    /**
     * 金额相加
     * @method
     * @date: 2020/5/21 19:13
     * @author: moran
     * @param valueStr 基础值
     * @param addStr 被加数
     * @param funm 金额格式化类
     *
     * @return 金额
     */
    fun moneyAdd(valueStr: String, addStr: String, funm: DecimalFormat): String {

        val value = BigDecimal(formatMoney(valueStr))

        val augend = BigDecimal(formatMoney(addStr))

        return funm.format(value.add(augend))

    }

    /**
     * 金额相减
     * @method
     * @date: 2020/5/21 19:24
     * @author: moran
     * @param valueStr 基础值
     * @param minusStr 被减数
     *
     * @return 金额
     */
    fun moneySub(valueStr: String, minusStr: String): String {

        val value = BigDecimal(formatMoney(valueStr))

        val minuValue = BigDecimal(formatMoney(minusStr))

        return funm.format(value.subtract(minuValue))

    }


    /**
     * 金额相减
     * @method
     * @date: 2020/5/21 19:24
     * @author: moran
     * @param valueStr 基础值
     * @param minusStr 被减数
     * @param funm 金额格式化类
     * @return 金额
     */
    fun moneySub(valueStr: String, minusStr: String, funm: DecimalFormat): String {

        val value = BigDecimal(formatMoney(valueStr))

        val minuValue = BigDecimal(formatMoney(minusStr))

        return funm.format(value.subtract(minuValue))
    }

    /**
     * 金额相乘
     * @method
     * @date: 2020/5/21 19:27
     * @author: moran
     * @param valueStr 基础值
     * @param mulStr 被乘数
     *
     * @return 金额
     */
    fun moneyMul(valueStr: String, mulStr: String): String {

        val value = BigDecimal(formatMoney(valueStr))

        val mulValue = BigDecimal(formatMoney(mulStr))

        return funm.format(value.multiply(mulValue))

    }

    /**
     * 金额相乘
     * @method
     * @date: 2020/5/21 19:27
     * @author: moran
     * @param valueStr 基础值
     * @param mulStr 被乘数
     * @param funm 金额格式化类
     *
     * @return 金额
     */
    fun moneyMul(valueStr: String, mulStr: String, funm: DecimalFormat): String {

        val value = BigDecimal(formatMoney(valueStr))

        val mulValue = BigDecimal(formatMoney(mulStr))

        return funm.format(value.multiply(mulValue))

    }

    /**
     * 金额相除
     * @method
     * @date: 2020/5/21 19:29
     * @author: moran
     * @param valueStr 除数
     * @param divideStr 被除数
     * @param scale 小数点位数 默认为2
     * @param roundingMode 舍入模式:
     * 1.ROUND_CEILING  向正无穷方向舍入
     * 2.ROUND_DOWN  向零方向舍入
     * 3.ROUND_FLOOR 向负无穷方向舍入
     * 4.ROUND_HALF_DOWN 向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向下舍入, 例如1.55 保留一位小数结果为1.5
     * 5.ROUND_HALF_EVEN  向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP，如果是偶数，使用ROUND_HALF_DOWN
     * 6.ROUND_HALF_UP    向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
     * 7.ROUND_UNNECESSARY  计算结果是精确的，不需要舍入模式
     * 8.ROUND_UP    向远离0的方向舍入
     *
     * @return 金额
     */
    fun moneyDiv(valueStr: String, divideStr: String, scale: Int = 2, roundingMode: Int = BigDecimal.ROUND_HALF_UP): String {
        val value = BigDecimal(formatMoney(valueStr))
        val divideValue = BigDecimal(formatMoney(divideStr))
        return funm.format(value.divide(divideValue, scale, roundingMode))
    }

    /**
     * 金额相除
     * @method
     * @date: 2020/5/21 19:29
     * @author: moran
     * @param valueStr 除数
     * @param divideStr 被除数
     * @param scale 小数点位数 默认为2
     * @param roundingMode 舍入模式:
     * 1.ROUND_CEILING  向正无穷方向舍入
     * 2.ROUND_DOWN  向零方向舍入
     * 3.ROUND_FLOOR 向负无穷方向舍入
     * 4.ROUND_HALF_DOWN 向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向下舍入, 例如1.55 保留一位小数结果为1.5
     * 5.ROUND_HALF_EVEN  向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP，如果是偶数，使用ROUND_HALF_DOWN
     * 6.ROUND_HALF_UP    向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
     * 7.ROUND_UNNECESSARY  计算结果是精确的，不需要舍入模式
     * 8.ROUND_UP    向远离0的方向舍入
     * @param funm DecimalFormat 金额格式化
     *
     * @return 金额
     */

    fun moneyDiv(valueStr: String, divideStr: String, scale: Int = 2, roundingMode: Int = BigDecimal.ROUND_HALF_UP, funm: DecimalFormat): String {
        val value = BigDecimal(formatMoney(valueStr))
        val divideValue = BigDecimal(formatMoney(divideStr))
        return funm.format(value.divide(divideValue, scale, roundingMode))
    }


    /**
     * 去除非数字内容，如：0.12元，0,01，1,11
     * @date: 2020/5/21 19:16
     * @author: moran
     * @param value 格式化金额类
     * @return
     */
    fun formatMoney(value: String?): String {

        if (null == value || "" == value) {
            return "0.00"
        }
        var money = ""
        if (value.contains("E")) {
            return value
//            money = pattern.matcher(BigDecimal(value).toString()).replaceAll("").trim()
        } else
            money = pattern.matcher(value).replaceAll("").trim()
        if ("" == money) {
            return "0.00"
        }
        return money
    }


    /**
     * 格式化金额,
     * @method
     * @date: 2020/5/21 19:48
     * @author: moran
     * @param value 金额内容
     * @param pattern 金额格式化形式
     * @return 金额
     */
    fun decimalFormatMoney(value: String?, pattern: String = "0.00"): String {

        val format = DecimalFormat(pattern)
        return format.format(BigDecimal(formatMoney(value)))
    }

    fun decimalFormatMoney(value: String?, pattern: String = "0.00", mode: RoundingMode = RoundingMode.UP): String {

        val format = DecimalFormat(pattern)

        format.roundingMode = mode
        return format.format(BigDecimal(formatMoney(value)))
    }

    fun decimalFormatMoney2(value: String?, pattern: String = "0.00", mode: RoundingMode = RoundingMode.DOWN): String {

        val format = DecimalFormat(pattern)

        format.roundingMode = mode
        return format.format(BigDecimal(formatMoney(value)))
    }


    /**
     * 创建金额格式化对象
     * @method
     * @date: 2020/5/21 19:50
     * @author: moran
     * @param
     * @return
     */
    fun createDecimalFormat(pattern: String = "0.00"): DecimalFormat {

        val funm = DecimalFormat(pattern)

        return funm
    }
}