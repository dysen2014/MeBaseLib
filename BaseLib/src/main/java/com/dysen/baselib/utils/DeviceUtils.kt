package com.dysen.baselib.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import androidx.core.app.ActivityCompat

/**
 * User: dysen
 * dy.sen@qq.com    @date : 11/24/20 10:26 AM

 * Info：
 */
object DeviceUtils {
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp,字体的转换
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 获取DisplayMetrics，包括屏幕高宽，密度等
     *
     * @param context
     * @return
     */
    fun getDisplayMetrics(context: Activity): DisplayMetrics? {
        val dm = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(dm)
        return dm
    }

    /**
     * 获得屏幕宽度 px
     *
     * @param context
     * @return
     */
    fun getWidth(context: Activity): Int {
        val dm = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * 获得屏幕高度 px
     *
     * @param context
     * @return
     */
    fun getHeight(context: Activity): Int {
        val dm = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    fun getDeviceId(context: Activity): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED) {
            ""
        } else tm.deviceId
    }
}