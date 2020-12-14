package com.me.baselib.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType

/**
 * 描述: 得到指定汉字的拼音
 */
object PinYinUtils {
    /**
     * 将hanzi转成拼音
     *
     * @param hanzi 汉字或字母
     * @return 拼音
     */
    @JvmStatic
    fun getPinyin(hanzi: String): String {
        val sb = StringBuilder()
        val format = HanyuPinyinOutputFormat()
        format.caseType = HanyuPinyinCaseType.UPPERCASE
        format.toneType = HanyuPinyinToneType.WITHOUT_TONE
        //由于不能直接对多个汉子转换，只能对单个汉子转换
        val arr = hanzi.toCharArray()
        for (i in arr.indices) {
            if (Character.isWhitespace(arr[i])) {
                continue
            }
            try {
                val pinyinArr =
                    PinyinHelper.toHanyuPinyinStringArray(
                        arr[i],
                        format
                    )
                if (pinyinArr != null) {
                    sb.append(pinyinArr[0])
                } else {
                    sb.append(arr[i])
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //不是正确的汉字
                sb.append(arr[i])
            }
        }
        return sb.toString()
    }
}