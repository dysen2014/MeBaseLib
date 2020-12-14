package com.me.baselib.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.net.ConnectivityManager
import android.os.Build
import android.view.Display
import android.view.WindowManager
import com.dysen.baselib.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.io.UnsupportedEncodingException

/**
 * dysen.
 * dy.sen@qq.com    11/17/20  11:06 AM
 * <p>
 * Info：
 */
object Utils {
    private val TAG = "Utils"

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    fun isNetWorkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    @Throws(WriterException::class, UnsupportedEncodingException::class)
    fun Create2DCode2(str: String): Bitmap? {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        val matrix = MultiFormatWriter().encode(
            String(str.toByteArray(charset("GBK")), charset("ISO-8859-1")),
            BarcodeFormat.QR_CODE,
            Tools.dp2px(240f),
            Tools.dp2px(240f)
        )
        val width = matrix.width
        val height = matrix.height
        //二维矩阵转为一维像素数组,也就是一直横着排了
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (matrix[x, y]) {
                    pixels[y * width + x] = -0x1000000
                }
            }
        }
        val colors = intArrayOf(R.color.text_color_white)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    @Throws(WriterException::class, UnsupportedEncodingException::class)
    fun Create2DCode(str: String): Bitmap? {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        val matrix = MultiFormatWriter().encode(
            String(str.toByteArray(charset("GBK")), charset("ISO-8859-1")),
            BarcodeFormat.QR_CODE,
            600,
            600
        )
        val width = matrix.width
        val height = matrix.height
        //二维矩阵转为一维像素数组,也就是一直横着排了
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (matrix[x, y]) {
                    pixels[y * width + x] = -0x1000000
                }
            }
        }
        val colors = intArrayOf(R.color.text_color_white)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    fun createBitmap(src: Bitmap?): Bitmap? {
        if (src == null) {
            return null
        }
        val paint = Paint()
        paint.color = Color.WHITE
        paint.isAntiAlias = true
        val w = 600
        val h = 600
        val newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val cv = Canvas(newb)
        cv.drawColor(Color.WHITE)
        cv.drawBitmap(src, 0f, 0f, null)
        //cv.save(Canvas.ALL_SAVE_FLAG);
        cv.save()
        cv.restore() //存储
        return newb
    }

    /**
     * 获取屏幕分辨率
     */
    fun getRealScreenSize(context: Activity): Point? {
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
     * 获得 状态栏和导航栏的高度。
     * 0 ----- statusBarHeights
     * 1 ----- navBarHeights
     */
    fun getStaNavHeight(activity: Activity): List<Int> {
        // 获取屏幕
        val cv = activity.window.decorView
        cv.isDrawingCacheEnabled = true
        cv.buildDrawingCache()
        val bmp = cv.drawingCache

        // 获取状态栏高度
        val rect = Rect()
        cv.getWindowVisibleDisplayFrame(rect)
        val statusBarHeights = rect.top
        val widths = bmp.width
        val heights = bmp.height
        val navBarHeights = heights - rect.bottom
        return listOf(*arrayOf(statusBarHeights, navBarHeights))
    }
}