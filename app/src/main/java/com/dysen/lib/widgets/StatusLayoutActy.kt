package com.dysen.lib.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.widgets.StatusConfig
import com.dysen.baselib.widgets.StatusType
import com.dysen.lib.R
import kotlinx.android.synthetic.main.activity_status_layout.*

/**
 * dysen.
 * dy.sen@qq.com    2021/1/22  11:44

 * Info：
 */
class StatusLayoutActy : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_status_layout

    override fun initView(savedInstanceState: Bundle?) {
        initStatusLayout()
        initClick()
    }

    private fun initClick() {
        btnEmpty.setOnClickListener { statusLayout.switchLayout(StatusType.Empty.name) }
        btnLoading.setOnClickListener { statusLayout.switchLayout(StatusType.Loading.name) }
        btnError.setOnClickListener { statusLayout.switchLayout(StatusType.Error.name) }
    }

    private fun initStatusLayout() {
        val emptyLayout = LayoutInflater.from(this).inflate(R.layout.layout_empty, statusLayout, false)
        val errorLayout = LayoutInflater.from(this).inflate(R.layout.layout_error, statusLayout, false)
        emptyLayout.findViewById<TextView>(R.id.empty_text).setOnClickListener {
            statusLayout.switchLayout(StatusType.Loading.name)
        }
        errorLayout.findViewById<TextView>(R.id.error_text).setOnClickListener {
            statusLayout.switchLayout(StatusType.Loading.name)
        }
        statusLayout.add(StatusConfig(StatusType.Empty.name, view = emptyLayout))
        statusLayout.add(StatusConfig(StatusType.Loading.name, R.layout.layout_loading))
        statusLayout.add(StatusConfig(StatusType.Error.name, view = errorLayout))
    }
}