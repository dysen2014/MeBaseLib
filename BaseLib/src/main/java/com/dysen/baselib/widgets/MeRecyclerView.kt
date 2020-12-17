package com.dysen.baselib.widgets

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dysen.baselib.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.layout_common_empty.view.*
import kotlinx.android.synthetic.main.layout_common_recyclerview.view.*
import kotlinx.android.synthetic.main.layout_common_recyclerview.view.rcl_contacts
import kotlinx.android.synthetic.main.layout_common_recyclerview.view.rcl_contacts_swipe
import kotlinx.android.synthetic.main.layout_common_recyclerview.view.empty_layout
import kotlinx.android.synthetic.main.layout_common_recyclerview_no_refresh.view.*
import kotlinx.android.synthetic.main.layout_common_recyclerview_no_refresh.view.rcl_no_contacts
import kotlinx.android.synthetic.main.layout_common_recyclerview_no_refresh.view.rcl_no_contacts_swipe
import kotlinx.android.synthetic.main.layout_common_recyclerview_no_refresh.view.empty_no_layout

class MeRecyclerView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    companion object {
        @JvmStatic
        var smartRefreshLayout: SmartRefreshLayout? = null

        @JvmStatic
        var swipeRecyclerView: SwipeRecyclerView? = null

        @JvmStatic
        var llEmpty: LinearLayout? = null

        @JvmStatic
        var emptyLayout: LinearLayout? = null

        @JvmStatic
        var recyclerView: RecyclerView? = null

        @JvmStatic
        var emptyIcon: ImageView? = null

        @JvmStatic
        var emptyTips: TextView? = null
        @JvmStatic
        var emptyRefresh: TextView? = null
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_common_recyclerview, this)
        smartRefreshLayout = srl
        swipeRecyclerView = rcl_contacts_swipe
        recyclerView = rcl_contacts
        emptyLayout = empty_layout
        llEmpty = ll_empty
        emptyIcon = imvEmptyIcon
        emptyTips = imvEmptyTips
        emptyRefresh = tvRefresh
    }
}

class MeRecyclerViewNoRefresh(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    companion object {

        @JvmStatic
        var swipeRecyclerView: SwipeRecyclerView? = null

        @JvmStatic
        var emptyLayout: LinearLayout? = null

        @JvmStatic
        var llEmpty: LinearLayout? = null

        @JvmStatic
        var recyclerView: RecyclerView? = null

        @JvmStatic
        var emptyIcon: ImageView? = null

        @JvmStatic
        var emptyTips: TextView? = null
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_common_recyclerview_no_refresh, this)
        swipeRecyclerView = rcl_no_contacts_swipe
        recyclerView = rcl_no_contacts
        emptyLayout = empty_no_layout

        llEmpty = ll_empty
        emptyIcon = imvEmptyIcon
        emptyTips = imvEmptyTips
    }
}