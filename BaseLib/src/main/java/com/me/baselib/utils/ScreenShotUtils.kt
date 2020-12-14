package com.me.baselib.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.widget.ListView
import android.widget.ScrollView
import java.util.*

/**
 *
 * @email dy.sen@qq.com
 * created by dysen on 20/3/28. - 下午1:35
 * @info 截图工具类
 */
object ScreenShotUtils {
    private val TAG = ScreenShotUtils::class.java.simpleName

    /**
     * 使用View的缓存功能，截取指定区域的View
     */
    fun getViewBitmap(view: View): Bitmap {
        //开启缓存功能
        view.isDrawingCacheEnabled = true
        // 创建缓存
        view.buildDrawingCache()
        // 获取缓存Bitmap
        return Bitmap.createBitmap(view.drawingCache)
    }

    /**
     * 截取LinearLayout （当前屏幕里的view  可示的view）
     */
    fun getLinearLayoutBitmap(linearLayout: ViewGroup): Bitmap {
        var h = 0
        val bitmap: Bitmap
        for (i in 0 until linearLayout.childCount) {
            h += linearLayout.getChildAt(i).height
        } // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(linearLayout.width, h, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)
        linearLayout.draw(canvas)
        return bitmap
    }

    /**
     * 截取scrollView的屏幕
     */
    fun getScrollViewBitmap(scrollView: ScrollView): Bitmap {
        var h = 0
        val bitmap: Bitmap
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        return bitmap
    }

    fun addQr(aty: Activity, view: View, bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) return null
        view.destroyDrawingCache()
        view.measure(
            View.MeasureSpec.makeMeasureSpec(getRealScreenSize(aty)!!.x, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.setBackgroundColor(Color.WHITE)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val shareBitmap = Bitmap.createBitmap(
            bitmap.width, bitmap.height,
            Bitmap.Config.ARGB_4444
        )
        val c = Canvas(shareBitmap)
        view.draw(c)
        return shareBitmap
    }

    /**
     * 获取屏幕分辨率
     */
    fun getRealScreenSize(context: Context): Point? {
        var screenSize: Point? = null
        try {
            screenSize = Point()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val defaultDisplay = windowManager.defaultDisplay
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                defaultDisplay.getRealSize(screenSize)
            } else {
                try {
                    val mGetRawW = Display::class.java.getMethod("getRawWidth")
                    val mGetRawH = Display::class.java.getMethod("getRawHeight")
                    screenSize[(mGetRawW.invoke(defaultDisplay) as Int)] =
                        (mGetRawH.invoke(defaultDisplay) as Int)
                } catch (e: Exception) {
                    screenSize[defaultDisplay.width] = defaultDisplay.height
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return screenSize
    }

    /**
     * 截取webView的屏幕
     */
    fun getWebViewBitmap(activity: Activity, webView: WebView): Bitmap? {

        //重新调用WebView的measure方法测量实际View的大小（将测量模式设置为UNSPECIFIED模式也就是需要多大就可以获得多大的空间）
//        webView.measure(View.MeasureSpec.makeMeasureSpec(webView.getWidth(), View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Utils.getRealScreenSize(activity)?.x?.let {
            View.MeasureSpec.makeMeasureSpec(
                it,
                View.MeasureSpec.EXACTLY
            )
        }?.let {
            webView.measure(
                it,
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
        }
        //调用layout方法设置布局（使用新测量的大小）
        webView.layout(0, 0, webView.measuredWidth, webView.measuredHeight)
        //开启WebView的缓存(当开启这个开关后下次调用getDrawingCache()方法的时候会把view绘制到一个bitmap上)
        webView.isDrawingCacheEnabled = true
        //强制绘制缓存（必须在setDrawingCacheEnabled(true)之后才能调用，否者需要手动调用destroyDrawingCache()清楚缓存）
        webView.buildDrawingCache()
        if (webView.measuredWidth <= 0 || webView.measuredHeight <= 0) return null
        //根据测量结果创建一个大小一样的bitmap
        val picture = Bitmap.createBitmap(
            webView.measuredWidth,
            webView.measuredHeight, Bitmap.Config.ARGB_8888
        )
        //已picture为背景创建一个画布
        val canvas = Canvas(picture) // 画布的宽高和 WebView 的网页保持一致
        val paint = Paint()
        //设置画笔的定点位置，也就是左上角
        canvas.drawBitmap(picture, 0f, webView.measuredHeight.toFloat(), paint)
        //将webview绘制在刚才创建的画板上
        webView.draw(canvas)
        return picture
    }

    /**
     * http://stackoverflow.com/questions/12742343/android-get-screenshot-of-all-listview-items
     */
    fun getListViewBitmap(listview: ListView): Bitmap? {
        val adapter = listview.adapter
        val itemscount = adapter.count
        var allitemsheight = 0
        val bmps: MutableList<Bitmap> = ArrayList()
        for (i in 0 until itemscount) {
            val childView = adapter.getView(i, null, listview)
            childView.measure(
                View.MeasureSpec.makeMeasureSpec(listview.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            childView.layout(0, 0, childView.measuredWidth, childView.measuredHeight)
            childView.isDrawingCacheEnabled = true
            childView.buildDrawingCache()
            bmps.add(childView.drawingCache)
            allitemsheight += childView.measuredHeight
        }
        val w = listview.measuredWidth
        val bigbitmap = Bitmap.createBitmap(w, allitemsheight, Bitmap.Config.ARGB_8888)
        val bigcanvas = Canvas(bigbitmap)
        val paint = Paint()
        var iHeight = 0
        for (i in bmps.indices) {
            var bmp: Bitmap? = bmps[i]
            bigcanvas.drawBitmap(bmp!!, 0f, iHeight.toFloat(), paint)
            iHeight += bmp.height
            bmp.recycle()
            bmp = null
        }
        return bigbitmap
    }
}