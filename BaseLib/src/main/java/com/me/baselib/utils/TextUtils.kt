package com.me.baselib.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt

/**
 * dysen.
 * dy.sen@qq.com    2020/4/22  15:53

 * Info：
 */
object TextUtils {

    /**
     *
    //方法一：
    textView.setText(Html.fromHtml("<font color=\"#ff0000\">红色</font>其它颜色"));

    //方法二：
    String text = "获得银宝箱!";
    SpannableStringBuilder style=new SpannableStringBuilder(text);
    //设置指定位置textview的背景颜色
    style.setSpan(new BackgroundColorSpan(Color.RED),2,5,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    //设置指定位置文字的颜色
    style.setSpan(new ForegroundColorSpan(Color.RED),0,2,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    textView.setText(style);
     */

    fun setTextColor(text: String, start: Int, end: Int, @ColorInt backgroundColor:Int=Color.RED, @ColorInt foreground:Int=Color.RED): SpannableStringBuilder {
        var style = SpannableStringBuilder(text)
        //设置指定位置textview的背景颜色
        style.setSpan(BackgroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //设置指定位置文字的颜色
        style.setSpan(ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        return style
    }

    fun isNullOrEmpty(str:String):Boolean{
        return str.isNullOrEmpty()
    }
}