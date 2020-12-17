package com.dysen.baselib.utils

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期处理工具
 * BASE_DATE_FORMAT     yyyy-MM-dd HH:mm:ss
 * NORMAL_DATE_FORMAT   yyyy-MM-dd
 * SHORT_DATE_FORMAT    yyyyMMdd
 * FULL_DATE_FORMAT     yyyyMMddHHmmss
 * Created by Luis on 2018/3/13.
 */
object DateUtils  {
    /**
     * yyyy-MM-dd HH:mm:ss字符串
     */
    var BASE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    /**
     * yyyy-MM-dd字符串
     */
    var NORMAL_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    /**
     * yyyyMMdd字符串
     */
    @JvmField
    var SHORT_DATE_FORMAT = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

    /**
     * yyyyMMddHHmmss字符串
     */
    var FULL_DATE_FORMAT = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())

    /**
     * HH:mm:ss字符串
     */
    const val DEFAULT_FORMAT_TIME = "HH:mm:ss"
    const val HHmm = "HH:mm"
    const val MMdd = "MM-dd"
    fun getHHmm(date: Date?): String {
        return dateSimpleFormat(date, SimpleDateFormat(HHmm))
    }

    fun getMMdd(date: Date?): String {
        return dateSimpleFormat(date, SimpleDateFormat(MMdd))
    }

    /**
     * 获取当前时间的前或后（年 月 日 时 分 秒）
     *
     * @param date     当前时间
     * @param dateType 年0   月1  日2  时3  分4  秒5
     * @param number   前或后个数
     * @return
     */
    fun getBeforeOrAfter(date: Date?, dateType: Int, number: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        when (dateType) {
            0 -> calendar.add(Calendar.YEAR, number)
            1 -> calendar.add(Calendar.MONTH, number)
            2 -> calendar.add(Calendar.DAY_OF_YEAR, number)
            3 -> calendar.add(Calendar.HOUR, number)
            4 -> calendar.add(Calendar.MINUTE, number)
            5 -> calendar.add(Calendar.SECOND, number)
        }
        //        String dateStr = SHORT_DATE_FORMAT.format(calendar.getTime());
        return calendar.time
    }

    /**
     * 获取一个月前的日期
     *
     * @param date     传入的日期
     * @param monthSum 前或后的月数
     * @return
     */
    fun getMonthBeforeOrAfter(date: Date?, monthSum: Int): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd")
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, monthSum)
        return simpleDateFormat.format(calendar.time)
    }

    fun getMonthBeforeOrAfter(monthSum: Int): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd")
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MONTH, monthSum)
        return simpleDateFormat.format(calendar.time)
    }

    /**
     * 判断日期是否为今天
     *
     * @param dateString 日期字符串, 格式：yyyy-MM-dd HH:mm:ss
     * @return 如果是同一天则返回true, 否则返回false
     */
    fun isToday(dateString: String?): Boolean {
        return try {
            val date = BASE_DATE_FORMAT.parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val calendar1 = Calendar.getInstance()
            (calendar[Calendar.YEAR] == calendar1[Calendar.YEAR]
                    && calendar[Calendar.DAY_OF_YEAR] == calendar1[Calendar.DAY_OF_YEAR])
        } catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

    fun isToday(timeInMills: Long): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMills
        val calendar1 = Calendar.getInstance()
        return (calendar[Calendar.YEAR] == calendar1[Calendar.YEAR]
                && calendar[Calendar.DAY_OF_YEAR] == calendar1[Calendar.DAY_OF_YEAR])
    }

    /**
     * 判断日期是否在当前时间之前
     *
     * @param dateString 日期字符串, 格式：yyyy-MM-dd HH:mm:ss
     * @return 如果在当前之前，返回true，否则返回false
     */
    fun isBeforeNow(dateString: String?): Boolean {
        return try {
            val date = BASE_DATE_FORMAT.parse(dateString)
            System.currentTimeMillis() > date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 判断日期是否在当前时间之后
     *
     * @param dateString 日期字符串, 格式：yyyy-MM-dd HH:mm:ss
     * @return 如果在当前之前，返回true，否则返回false
     */
    fun isAfterNow(dateString: String?): Boolean {
        return try {
            val date = BASE_DATE_FORMAT.parse(dateString)
            System.currentTimeMillis() < date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

    fun getDateString(timestamp: Long): String {
        return BASE_DATE_FORMAT.format(Date(timestamp))
    }

    @JvmStatic
    fun getNormalDateString(timestamp: Long): String {
        return NORMAL_DATE_FORMAT.format(Date(timestamp))
    }

    fun getShortDateString(timestamp: Long): String {
        return SHORT_DATE_FORMAT.format(Date(timestamp))
    }

    fun getFullDateString(timestamp: Long): String {
        return FULL_DATE_FORMAT.format(Date(timestamp))
    }

    private val sdf = SimpleDateFormat("yyyy-MM-dd")

    /**
     * 计算时间方法
     *
     * @param beginDayStr 开始时间
     * @param endDayStr   结束时间
     * @return
     */
    fun subDay(beginDayStr: String?, endDayStr: String?): Long {
        var beginDate: Date? = null
        var endDate: Date? = null
        var day: Long = 0
        try {
            beginDate = sdf.parse(beginDayStr)
            endDate = sdf.parse(endDayStr)

            // 计算天数
            day = (endDate.time - beginDate.time) / (24 * 60 * 60 * 1000)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        // 当天入住、当天离开算1天时间
        if (day == 0L) {
            day = 1
        }
        return day
    }// 订单编号

    /**
     * 获取当天日期
     *
     * @return
     */
    val todayStr: String
        get() {
            val format4 = SimpleDateFormat("yyyyMMddhhmmss")

            // 订单编号
            val c = Calendar.getInstance()
            val today = c.time
            return format4.format(today)
        }

    /**
     * 获取当天日期
     *
     * @return
     */
    fun getToday(format: SimpleDateFormat): String {
        val c = Calendar.getInstance()
        val today = c.time
        return format.format(today)
    }

    /**
     * 获取当天日期
     *
     * @param pattern "yyyy-mm-dd"
     * @return
     */
    fun getToday(pattern: String?): String {
        val format = SimpleDateFormat(pattern)
        val c = Calendar.getInstance()
        val today = c.time
        return format.format(today)
    }

    /**
     * 获取当天日期
     *
     * @return
     */
    val todayDate: String
        get() {
            val format4 = SimpleDateFormat("yyyyMMdd")
            val c = Calendar.getInstance()
            val today = c.time
            return format4.format(today)
        }
    val todayF: String
        get() {
            val format4 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val c = Calendar.getInstance()
            val today = c.time
            return format4.format(today)
        }// 秒// 分// 时// 日// 月// 年

    /**
     * 获取当前时分秒
     *
     * @return
     */
    val datetimes: Map<String, String>
        get() {
            val f_yyyy = SimpleDateFormat("yyyy")
            val f_MM = SimpleDateFormat("MM")
            val f_dd = SimpleDateFormat("dd")
            val f_HH = SimpleDateFormat("HH")
            val f_mm = SimpleDateFormat("mm")
            val f_ss = SimpleDateFormat("ss")
            val c = Calendar.getInstance()
            val today = c.time
            val d_yyyy = f_yyyy.format(today) // 年
            val d_MM = f_MM.format(today) // 月
            val d_dd = f_dd.format(today) // 日
            val t_HH = f_HH.format(today) // 时
            val t_mm = f_mm.format(today) // 分
            val t_ss = f_ss.format(today) // 秒
            val datetimes: MutableMap<String, String> = HashMap()
            datetimes["yyyy"] = d_yyyy
            datetimes["MM"] = d_MM
            datetimes["dd"] = d_dd
            datetimes["HH"] = t_HH
            datetimes["mm"] = t_mm
            datetimes["ss"] = t_ss
            return datetimes
        }

    fun getDateMapByStringValue(strData: String?): Map<String, String> {
        val f_yyyy = SimpleDateFormat("yyyy")
        val f_MM = SimpleDateFormat("MM")
        val f_dd = SimpleDateFormat("dd")
        val f_HH = SimpleDateFormat("HH")
        val f_mm = SimpleDateFormat("mm")
        val f_ss = SimpleDateFormat("ss")
        val today = strToDate(strData)
        val d_yyyy = f_yyyy.format(today) // 年
        val d_MM = f_MM.format(today) // 月
        val d_dd = f_dd.format(today) // 日
        val t_HH = f_HH.format(today) // 时
        val t_mm = f_mm.format(today) // 分
        val t_ss = f_ss.format(today) // 秒
        val datetimes: MutableMap<String, String> = HashMap()
        datetimes["yyyy"] = d_yyyy
        datetimes["MM"] = d_MM
        datetimes["dd"] = d_dd
        datetimes["HH"] = t_HH
        datetimes["mm"] = t_mm
        datetimes["ss"] = t_ss
        return datetimes
    }

    /****
     * 获取系统当前的日期
     *
     * @author chenjianfan
     * @return
     */
    val currentDate: String
        get() {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("MM月dd日")
            return dateFormat.format(calendar.time)
        }

    /**
     * 获取星期几
     *
     * @return
     */
    fun getCurrentWeek(vararg date: Date?): String {
        val cal = Calendar.getInstance()
        if (date.size > 0) {
            cal.time = date[0]
        } else {
            cal.time = today
        }
        val week = cal[Calendar.DAY_OF_WEEK]
        var strWeek = "星期天"
        strWeek = when (week) {
            1 -> "星期天"
            2 -> "星期一"
            3 -> "星期二"
            4 -> "星期三"
            5 -> "星期四"
            6 -> "星期五"
            7 -> "星期六"
            else -> "星期天"
        }
        return strWeek
    }

    /****
     * 获取系统当前的时间
     *
     * @author chenjianfan
     * @return
     */
    val currentTime: String
        get() {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("HH:mm")
            return dateFormat.format(calendar.time)
        }

    /**
     * 格式转换
     *
     * @param str
     * @return
     */
    fun strToDate(str: String?): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = format.parse(str)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    fun strToDate(str: String?, format: SimpleDateFormat): Date? {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        var date: Date? = null
        try {
            date = format.parse(str)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    fun str2Format(str: String?, format: SimpleDateFormat): String {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        var date: Date? = null
        try {
            date = format.parse(str)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return format.format(date)
    }

    /**
     * 获得当天时间
     *
     * @param date
     * @param iDaydiff
     * @return
     */
    fun getDay(date: Date?, iDaydiff: Int): String {
        var date = date
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        calendar.add(Calendar.DATE, iDaydiff) //把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.time //这个时间就是日期往后推一天的结果
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(date)
    }
    /** 定义日期对象  */
    /**
     * 获取当天日期
     *
     * @return
     */
    val today: Date
        get() {
            /** 定义日期对象  */
            val c = Calendar.getInstance()
            return c.time
        }

    /**
     * 将年月日的int转成date
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日
     * 注：月表示Calendar的月，比实际小1
     */
    fun getDate(year: Int, month: Int, day: Int): Date {
        val mCalendar = Calendar.getInstance()
        mCalendar[year, month - 1] = day
        return mCalendar.time
    }

    /**
     * 求两个日期相差天数
     *
     * @param strat 起始日期，格式yyyy-MM-dd
     * @param end   终止日期，格式yyyy-MM-dd
     * @return 两个日期相差天数
     */
    fun getIntervalDays(strat: String?, end: String?): Long {
        return try {
            (BASE_DATE_FORMAT.parse(end).time - BASE_DATE_FORMAT.parse(strat).time) / (3600 * 24 * 1000)
        } catch (e: ParseException) {
            e.printStackTrace()
            -1
        }
    }

    /**
     * 获得当前年份
     *
     * @return year(int)
     */
    val currentYear: Int
        get() {
            val mCalendar = Calendar.getInstance()
            return mCalendar[Calendar.YEAR]
        }

    /**
     * 获得当前月份
     *
     * @return month(int) 1-12
     */
    val currentMonth: Int
        get() {
            val mCalendar = Calendar.getInstance()
            return mCalendar[Calendar.MONTH] + 1
        }

    /**
     * 获得当月几号
     *
     * @return day(int)
     */
    val dayOfMonth: Int
        get() {
            val mCalendar = Calendar.getInstance()
            return mCalendar[Calendar.DAY_OF_MONTH]
        }

    /**
     * 获得昨天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    val yesterday: String
        get() {
            val mCalendar = Calendar.getInstance()
            mCalendar.add(Calendar.DATE, -1)
            return getDateFormat(mCalendar.time)
        }

    /**
     * 获得前天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    val beforeYesterday: String
        get() {
            val mCalendar = Calendar.getInstance()
            mCalendar.add(Calendar.DATE, -2)
            return getDateFormat(mCalendar.time)
        }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：正的往后推，负的往前推
     * @return
     */
    @JvmStatic
    fun getOtherDay(diff: Int): String {
        val mCalendar = Calendar.getInstance()
        mCalendar.add(Calendar.DATE, diff)
        return getDateFormat(mCalendar.time)
    }

    @JvmStatic
    fun getOtherFormat(diff: Int): Date {
        val mCalendar = Calendar.getInstance()
        mCalendar.add(Calendar.DATE, diff)
        return mCalendar.time
    }

    /**
     * 获得几小时之前或者几小时之后的日期
     *
     * @param diff 差值：正的往后推，负的往前推
     * @return
     */
    fun getOtherHour(diff: Int): String {
        val mCalendar = Calendar.getInstance()
        mCalendar.add(Calendar.HOUR_OF_DAY, diff)
        return dateSimpleFormat(mCalendar.time, BASE_DATE_FORMAT)
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     *
     * @param sDate  给定的日期对象
     * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    fun getCalcDateFormat(sDate: String?, amount: Int): String {
        val date = getCalcDate(getDateByDateFormat(sDate), amount)
        return getDateFormat(date)
    }

    /**
     * 将"yyyy-MM-dd" 格式的字符串转成Date
     *
     * @param strDate
     * @return Date
     */
    fun getDateByDateFormat(strDate: String?): Date {
        return getDateByFormat(strDate, NORMAL_DATE_FORMAT)
    }

    /**
     * 将指定格式的时间字符串转成Date对象
     *
     * @param strDate 时间字符串
     * @param format  格式化字符串
     * @return Date
     */
    fun getDateByFormat(strDate: String?, format: SimpleDateFormat?): Date {
        return getDateByFormat(strDate, format)
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     *
     * @param date   给定的日期对象
     * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    fun getCalcDate(date: Date?, amount: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DATE, amount)
        return cal.time
    }

    /**
     * 获得一个计算十分秒之后的日期对象
     *
     * @param date
     * @param hOffset 时偏移量，可为负
     * @param mOffset 分偏移量，可为负
     * @param sOffset 秒偏移量，可为负
     * @return
     */
    fun getCalcTime(date: Date?, hOffset: Int, mOffset: Int, sOffset: Int): Date {
        val cal = Calendar.getInstance()
        if (date != null) cal.time = date
        cal.add(Calendar.HOUR_OF_DAY, hOffset)
        cal.add(Calendar.MINUTE, mOffset)
        cal.add(Calendar.SECOND, sOffset)
        return cal.time
    }

    /**
     * 根据指定的年月日小时分秒，返回一个java.Util.Date对象。
     *
     * @param year      年
     * @param month     月 0-11
     * @param date      日
     * @param hourOfDay 小时 0-23
     * @param minute    分 0-59
     * @param second    秒 0-59
     * @return 一个Date对象
     */
    fun getDate(
        year: Int, month: Int, date: Int, hourOfDay: Int,
        minute: Int, second: Int
    ): Date {
        val cal = Calendar.getInstance()
        cal[year, month, date, hourOfDay, minute] = second
        return cal.time
    }

    /**
     * 获得年月日数据
     *
     * @param sDate yyyy-MM-dd格式
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    fun getYearMonthAndDayFrom(sDate: String?): IntArray {
        return getYearMonthAndDayFromDate(getDateByDateFormat(sDate))
    }

    /**
     * 获得年月日数据
     *
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    fun getYearMonthAndDayFromDate(date: Date?): IntArray {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val arr = IntArray(3)
        arr[0] = calendar[Calendar.YEAR]
        arr[1] = calendar[Calendar.MONTH]
        arr[2] = calendar[Calendar.DAY_OF_MONTH]
        return arr
    }

    /**
     * 将年月日的int转成yyyy-MM-dd的字符串
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日
     * 注：月表示Calendar的月，比实际小1
     * 对输入项未做判断
     */
    fun getDateFormat(year: Int, month: Int, day: Int): String {
        return getDateFormat(getDate(year, month, day))
    }

    /**
     * 将date转成yyyy-MM-dd字符串<br></br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd
     */
    fun getDateFormat(date: Date?): String {
        return dateSimpleFormat(date, NORMAL_DATE_FORMAT)
    }

    /**
     * 将date转成字符串
     *
     * @param date   Date
     * @param format SimpleDateFormat
     * <br></br>
     * 注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    @JvmStatic
    fun dateSimpleFormat(date: Date?, format: SimpleDateFormat?): String {
        var format = format
        if (format == null) format = BASE_DATE_FORMAT
        return if (date == null) "" else format.format(date)
    }

    /**
     * 字符串格式的时间转换成整形 如 "09:21" ---> 921
     *
     * @param date
     * @return
     */
    fun dateFormat(date: String?): Int {
        return if (date == null && ("" == date || "null" == date)) 0 else Integer.valueOf(date!!.replace(":", ""))
    }
}