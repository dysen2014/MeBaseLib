package com.me.baselib.ui.image_paper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.dysen.baselib.R
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.widgets.ViewPagerFixed
import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher
import java.util.*

/**
 * dysen.
 * dy.sen@qq.com    9/15/20  10:18 AM

 * Info：查看大图 glide
 */
class BigImagePagerActivity : BaseActivity() {
    private val guideViewList: MutableList<View> = ArrayList()
    private var guideGroup: LinearLayout? = null

    override fun layoutId(): Int {
        return R.layout.act_image_pager
    }

    override fun initView(savedInstanceState: Bundle?) {
        val viewPager: ViewPager = findViewById<View>(R.id.pager) as ViewPagerFixed
        guideGroup = findViewById<View>(R.id.guideGroup) as LinearLayout

        val mAdapter = ImageAdapter(this)
        mAdapter.setDatas(imgUrls)
        viewPager.adapter = mAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in guideViewList.indices) {
                    guideViewList[i].isSelected = i == position
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        viewPager.currentItem = startPos
        addGuideView(guideGroup, startPos, imgUrls)
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun addGuideView(guideGroup: LinearLayout?, startPos: Int, imgUrls: List<String>) {
        if (imgUrls != null && imgUrls.isNotEmpty()) {
            guideViewList.clear()
            for (i in imgUrls.indices) {
                val view = View(this)
                view.setBackgroundResource(R.drawable.selector_guide_bg)
                view.isSelected = i == startPos
                val layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.gudieview_width),
                    resources.getDimensionPixelSize(R.dimen.gudieview_heigh)
                )
                layoutParams.setMargins(10, 0, 0, 0)
                guideGroup!!.addView(view, layoutParams)
                guideViewList.add(view)
            }
        }
    }

    private inner class ImageAdapter(private val context: Context) : PagerAdapter() {
        private var datas: List<String?>? = ArrayList()
        private val inflater: LayoutInflater = LayoutInflater.from(context)
        fun setDatas(datas: List<String?>?) {
            if (datas != null) this.datas = datas
        }

        override fun getCount(): Int {
            return if (datas == null) 0 else datas!!.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = inflater.inflate(R.layout.item_pager_image, container, false)
            if (view != null) {
                val imageView = view.findViewById<View>(R.id.image) as PhotoView

                //单击图片退出
                imageView.onViewTapListener =
                    PhotoViewAttacher.OnViewTapListener { view, x, y -> finish() }
                //loading
                val loading = ProgressBar(context)
                val loadingLayoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                loadingLayoutParams.gravity = Gravity.CENTER
                loading.layoutParams = loadingLayoutParams
                (view as FrameLayout).addView(loading)
                val imgurl = datas!![position]
                if (TextUtils.isEmpty(imgurl)) {
                    imageView.setImageResource(R.mipmap.icon_head)
                    loading.visibility = View.GONE
                    container.addView(view, 0)
                } else {
                    loading.visibility = View.VISIBLE

                    imageView.load(imgurl) {
                        diskCachePolicy(CachePolicy.DISABLED)
                        placeholder(R.mipmap.icon_pic_load_failed)
                        error(R.mipmap.icon_pic_load_failed)
                        listener(object : ImageRequest.Listener {
                            override fun onError(request: ImageRequest, throwable: Throwable) {
                                super.onError(request, throwable)
                                loading.visibility = View.GONE
                            }

                            override fun onCancel(request: ImageRequest) {
                                super.onCancel(request)
                                loading.visibility = View.GONE
                            }
                        })

                    }
                    container.addView(view, 0)
                }
            }
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
        override fun saveState(): Parcelable? {
            return null
        }

    }

    companion object {
        var startPos = 0
        var imgUrls: List<String> = listOf()

        fun startImagePagerActivity(activity: Activity, imgUrls: List<String>?, position: Int) {
            this.imgUrls = imgUrls ?: listOf<String>()
            startPos = position
            val intent = Intent(activity, BigImagePagerActivity::class.java)
            activity.startActivity(intent)
//            activity.overridePendingTransition(R.anim.fade_in,
//                    R.anim.fade_out)
        }

        fun startImagePagerActivity(activity: Activity, imgUrl: String) {
            this.imgUrls = listOf(imgUrl)
            startPos = 0
            val intent = Intent(activity, BigImagePagerActivity::class.java)
            activity.startActivity(intent)
//            activity.overridePendingTransition(R.anim.fade_in,
//                    R.anim.fade_out)
        }
    }
}