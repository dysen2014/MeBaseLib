package com.dysen.baselib.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dysen.baselib.R
import kotlinx.android.synthetic.main.layout_common_empty.view.*


/**
 * dysen.
 * dy.sen@qq.com    2020/6/14  11:14

 * Info：自定义空布局
 */
class EmptyLayout(context: Context, attrs:AttributeSet) :LinearLayout(context, attrs){
    companion object {
        @JvmStatic
         var mView: View? = null
        @JvmStatic
        var img:ImageView?=null
        @JvmStatic
        var text:TextView?=null
        @JvmStatic
        var refresh:TextView?=null
        @JvmStatic
        var llEmpty:LinearLayout?=null
    }
    init {
        mView = LayoutInflater.from(context).inflate(R.layout.layout_common_empty, this)
        img = imvEmptyIcon
        text = imvEmptyTips
        refresh = tvRefresh
        llEmpty = ll_empty
    }
}


