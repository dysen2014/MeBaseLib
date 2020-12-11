package com.dysen.welcome

import android.view.View
import android.view.ViewGroup

import com.zhpan.bannerview.BaseBannerAdapter

/**
* @author dysen
* dy.sen@qq.com     12/11/20 9:03 AM
*
* Info：
*/
class WelcomeAdapter : BaseBannerAdapter<WelcomeModel, CustomPageViewHolder>() {

    var mOnSubViewClickListener: CustomPageViewHolder.OnSubViewClickListener? = null

    override fun onBind(holder: CustomPageViewHolder, data: WelcomeModel, position: Int, pageSize: Int) {
        holder.bindData(data, position, pageSize)
    }

    override fun createViewHolder(parent: ViewGroup, itemView: View, viewType: Int): CustomPageViewHolder? {
        val customPageViewHolder = CustomPageViewHolder(itemView)
        customPageViewHolder.setOnSubViewClickListener(mOnSubViewClickListener)
        return customPageViewHolder
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_custom_view
    }
}
