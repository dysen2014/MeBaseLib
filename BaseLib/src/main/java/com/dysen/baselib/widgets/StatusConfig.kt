package com.dysen.baselib.widgets

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 * dysen.
 * dy.sen@qq.com    2021/1/22  11:14

 * Info：状态属性
 */
data class StatusConfig(
        var status: String,
        @field:LayoutRes
        var layoutRes: Int = 0,
        var view: View? = null,
        @field:IdRes
        var clickRes: Int = 0,
        var autoClcik: Boolean = true)

enum class StatusType{
        Empty,Loading,Error
}