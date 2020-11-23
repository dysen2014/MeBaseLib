package com.dysen.baselib.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import com.dysen.baselib.R
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.layout_top.view.*


/**
 * dysen.
 * dy.sen@qq.com    2020/6/14  11:14

 * Info：自定义标题布局
 */
class TitleLayout(context: Context, attrs:AttributeSet) :LinearLayout(context, attrs){
    companion object {
        @JvmStatic
//        lateinit var mView: View
         var mView: View? = null
        @JvmStatic
        var title:TextView?=null
        @JvmStatic
        var leftText:TextView?=null
        @JvmStatic
        var rightText:TextView?=null
        @JvmStatic
        var line:View?=null
    }
    init {
        mView = LayoutInflater.from(context).inflate(R.layout.layout_top, this)
        leftText = tv_back
        title = tv_title
        rightText = tv_menu
        line = view_line
        tv_back.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }
    }
}


