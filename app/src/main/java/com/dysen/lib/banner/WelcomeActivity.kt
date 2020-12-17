package com.dysen.lib.banner

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.common.transform.PageTransformerFactory
import com.dysen.baselib.common.transform.TransformerStyle
import com.dysen.lib.MainActivity
import com.dysen.lib.R
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.utils.BannerUtils
import com.zhpan.indicator.enums.IndicatorSlideMode
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.*

/**
 * @author dysen
 * dy.sen@qq.com     12/10/20 5:33 PM
 *
 * Info：
 */
class WelcomeActivity :BaseActivity(){
    val ANIMATION_DURATION = 1300
    var mDrawableList: MutableList<Int> = ArrayList()
    private lateinit var mViewPager: BannerViewPager<CustBean, CustomPageViewHolder>

    private val des = arrayOf("在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人")

    private val transforms = intArrayOf(TransformerStyle.NONE, TransformerStyle.ACCORDION, TransformerStyle.DEPTH,TransformerStyle.ROTATE,TransformerStyle.SCALE_IN)

    private val data: List<CustBean>
        get() {
            val list = ArrayList<CustBean>()
            for (i in mDrawableList.indices) {
                val customBean = CustBean()
                customBean.imageRes = mDrawableList[i]
                customBean.imageDescription = des[i]
                list.add(customBean)
            }
            return list
        }

    override fun layoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        initData()
        setupViewPager()
        updateUI(0)
        initClick()
    }

    private fun initClick() {
//        btn_start.setOnClickListener { newInstance(this, MainActivity::class.java) }
        btn_start.setOnClickListener { newInstance(this, MainActivity::class.java) }
    }

    private fun initData() {
        for (i in 0..2) {
            val drawable = resources.getIdentifier("guide$i", "drawable", packageName)
            mDrawableList.add(drawable)
        }
    }

    private fun setupViewPager() {
        mViewPager = findViewById(R.id.viewpager)
        mViewPager.apply {
            setCanLoop(false)
            setPageTransformer(PageTransformerFactory.createPageTransformer(transforms[Random().nextInt(5)]))
            setIndicatorMargin(0, 0, 0, resources.getDimension(R.dimen.dp_96).toInt())
            setIndicatorSliderGap(resources.getDimension(R.dimen.w_10).toInt())
            setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            setIndicatorSliderRadius(resources.getDimension(R.dimen.dp_4).toInt(), resources.getDimension(R.dimen.dp_4).toInt())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    BannerUtils.log("position:$position")
                    updateUI(position)
                }
            })

            adapter = WelcomeAdapter().apply {
                mOnSubViewClickListener = CustomPageViewHolder.OnSubViewClickListener { _, position -> showTip("Logo Clicked,position:$position") }
            }
            setIndicatorSliderColor(
                ContextCompat.getColor(this@WelcomeActivity, R.color.white),
                ContextCompat.getColor(this@WelcomeActivity, R.color.white_alpha_75))
        }.create(data)
    }

    private fun updateUI(position: Int) {
        tv_describe?.text = des[position]
        val translationAnim = ObjectAnimator.ofFloat(tv_describe, "translationX", -120f, 0f)
        translationAnim.apply {
            duration = ANIMATION_DURATION.toLong()
            interpolator = DecelerateInterpolator()
        }
        val alphaAnimator = ObjectAnimator.ofFloat(tv_describe, "alpha", 0f, 1f)
        alphaAnimator.apply {
            duration = ANIMATION_DURATION.toLong()
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationAnim, alphaAnimator)
        animatorSet.start()

        if (position == mViewPager.data.size - 1 && btn_start?.visibility == View.GONE) {
            btn_start?.visibility = View.VISIBLE
            ObjectAnimator
                .ofFloat(btn_start, "alpha", 0f, 1f)
                .setDuration(ANIMATION_DURATION.toLong()).start()
        } else {
            btn_start?.visibility = View.GONE
        }
    }
}