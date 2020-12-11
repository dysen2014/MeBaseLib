package com.dysen.welcome

import android.animation.ObjectAnimator
import android.net.Uri
import android.view.View
import android.widget.ImageView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.dysen.baselib.utils.ColorUtil
import com.dysen.baselib.utils.gone
import com.zhpan.bannerview.BaseViewHolder

/**
 * @author dysen
 * dy.sen@qq.com     12/11/20 9:33 AM
 *
 * Info：
 */
class CustomPageViewHolder(itemView: View) : BaseViewHolder<WelcomeModel>(itemView) {
    private var mOnSubViewClickListener: OnSubViewClickListener? = null
    override fun bindData(data: WelcomeModel, position: Int, pageSize: Int) {
        val imageStart = findView<ImageView>(R.id.iv_logo)
        var imgBg = findView<ImageView>(R.id.banner_image)
        println("${data.imgUrl} ==== ${data.imageRes} === ${data.imageDescription}")
        if (data.imgUrl.isEmpty()) {
            imgBg.load(data.imageRes) {
                crossfade(false)
                placeholder(R.mipmap.ic_launcher)
                scale(Scale.FIT)
            }
        } else {
            imgBg.load(data.imgUrl) {
                crossfade(false)
                placeholder(R.mipmap.ic_launcher)
                scale(Scale.FIT)
            }
        }

        imageStart.visibility = gone
//        setImageResource(R.id.banner_image, R.drawable.bg_red_rc_button)
        setOnClickListener(R.id.iv_logo) { view: View? -> if (null != mOnSubViewClickListener) mOnSubViewClickListener!!.onViewClick(view, adapterPosition) }
        val alphaAnimator = ObjectAnimator.ofFloat(imageStart, "alpha", 0f, 1f)
        alphaAnimator.duration = 1500
        alphaAnimator.start()
    }

    fun setOnSubViewClickListener(subViewClickListener: OnSubViewClickListener?) {
        mOnSubViewClickListener = subViewClickListener
    }

    interface OnSubViewClickListener {
        fun onViewClick(view: View?, position: Int)
    }

}