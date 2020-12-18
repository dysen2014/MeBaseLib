package com.dysen.baselib.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import com.dysen.baselib.utils.AppUtils.getPackageName
import android.provider.Settings


/**
 * @author dysen
 * dy.sen@qq.com     12/18/20 3:49 PM
 *
 * Info：App 保活
 */
object AppKeepAlive {

    /**
     * 判断我们的应用是否在白名单中
     */
    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        var isIgnoring = false
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager?
        if (powerManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName(context))
            }
        }
        return isIgnoring
    }

    /**
     * 申请App 加入白名单
     */
    fun requestIgnoreBatteryOptimizations(context: Context) {

        if (!isIgnoringBatteryOptimizations(context)) {
            try {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:" + getPackageName(context))
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}