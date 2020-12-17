package com.dysen.lib.banner

import android.view.View
import android.view.ViewGroup
import com.dysen.lib.R

import com.zhpan.bannerview.BaseBannerAdapter

/**
* @author dysen
* dy.sen@qq.com     12/11/20 9:03 AM
*
* Info：
*/
class WelcomeAdapter : BaseBannerAdapter<CustBean, _root_ide_package_.com.dysen.lib.banner.CustomPageViewHolder>() {

    var mOnSubViewClickListener: _root_ide_package_.com.dysen.lib.banner.CustomPageViewHolder.OnSubViewClickListener? = null

    override fun onBind(holder: _root_ide_package_.com.dysen.lib.banner.CustomPageViewHolder, data: CustBean, position: Int, pageSize: Int) {
        holder.bindData(data, position, pageSize)
    }

    override fun createViewHolder(parent: ViewGroup, itemView: View, viewType: Int): _root_ide_package_.com.dysen.lib.banner.CustomPageViewHolder? {
        val customPageViewHolder = _root_ide_package_.com.dysen.lib.banner.CustomPageViewHolder(itemView)
        customPageViewHolder.setOnSubViewClickListener(mOnSubViewClickListener)
        return customPageViewHolder
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_custom_view
    }
}
