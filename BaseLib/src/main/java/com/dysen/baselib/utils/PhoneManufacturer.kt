package com.dysen.baselib.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.NonNull


/**
 * @author dysen
 * dy.sen@qq.com     12/18/20 4:32 PM
 *
 * Info：获取各手机厂商及跳转应用启动管理设置页面
 */
object PhoneManufacturer {

    enum class ManufacturerType {
        huawei, honor, xiaomi, oppo, vivo, meizu, samsung, letv, smartisan, google
    }

    fun getManufacturer(context: Context) {
        when (Build.BRAND?.toLowerCase() ?: "") {
            ManufacturerType.huawei.name,
            ManufacturerType.honor.name -> {
                goHuaweiSetting(context)
            }
            ManufacturerType.xiaomi.name -> {
                goXiaomiSetting(context)
            }
            ManufacturerType.oppo.name -> {
                goOPPOSetting(context)
            }
            ManufacturerType.vivo.name -> {
                goVIVOSetting(context)
            }
            ManufacturerType.meizu.name -> {
                goMeizuSetting(context)
            }
            ManufacturerType.samsung.name -> {
                goSamsungSetting(context)
            }
            ManufacturerType.letv.name -> {
                goLetvSetting(context)
            }
            ManufacturerType.smartisan.name -> {
                goSmartisanSetting(context)
            }
            ManufacturerType.google.name -> {
                goGoogleSetting(context)
            }
        }
    }

    /**
     * 跳转到指定应用的首页
     */
    fun showActivity(context: Context, @NonNull packageName: String) {
        val intent: Intent = context.packageManager.getLaunchIntentForPackage(packageName) ?: Intent()
        context.startActivity(intent)
    }

    /**
     * 跳转到指定应用的指定页面
     */
    fun showActivity(context: Context, @NonNull packageName: String, @NonNull activityDir: String) {
        val intent = Intent()
        intent.component = ComponentName(packageName, activityDir)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    /*************** 华为 *********************/
    fun isHuawei(): Boolean {
        return if (Build.BRAND == null) {
            false
        } else {
            Build.BRAND.toLowerCase() == "huawei" || Build.BRAND.toLowerCase() == "honor"
        }
    }

    fun goHuaweiSetting(context: Context) {
        try {
            showActivity(
                context,
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
            )
        } catch (e: Exception) {
            showActivity(
                context,
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"
            )
        }
    }

    /*************** 小米 *********************/
    fun isXiaomi(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "xiaomi"
    }

    fun goXiaomiSetting(context: Context) {
        showActivity(
            context,
            "com.miui.securitycenter",
            "com.miui.permcenter.autostart.AutoStartManagementActivity"
        )
    }

    /*************** OPPO *********************/
    fun isOPPO(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "oppo"
    }

    fun goOPPOSetting(context: Context) {
        try {
            showActivity(context, "com.coloros.phonemanager")
        } catch (e1: java.lang.Exception) {
            try {
                showActivity(context, "com.oppo.safe")
            } catch (e2: java.lang.Exception) {
                try {
                    showActivity(context, "com.coloros.oppoguardelf")
                } catch (e3: java.lang.Exception) {
                    showActivity(context, "com.coloros.safecenter")
                }
            }
        }
    }

    /*************** VIVO *********************/
    fun isVIVO(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "vivo"
    }

    fun goVIVOSetting(context: Context) {
        showActivity(context, "com.iqoo.secure")
    }

    /*************** Meizu *********************/
    fun isMeizu(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "meizu"
    }

    fun goMeizuSetting(context: Context) {
        showActivity(context, "com.meizu.safe")
    }

    /*************** Samsung *********************/
    fun isSamsung(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "samsung"
    }

    fun goSamsungSetting(context: Context) {
        try {
            showActivity(context, "com.samsung.android.sm_cn")
        } catch (e: java.lang.Exception) {
            showActivity(context, "com.samsung.android.sm")
        }
    }

    /*************** LeTV *********************/
    fun isLeTV(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "letv"
    }

    fun goLetvSetting(context: Context) {
        showActivity(
            context,
            "com.letv.android.letvsafe",
            "com.letv.android.letvsafe.AutobootManageActivity"
        )
    }

    /*************** Smartisan *********************/
    fun isSmartisan(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "smartisan"
    }

    fun goSmartisanSetting(context: Context) {
        showActivity(context, "com.smartisanos.security")
    }

    /*************** Google *********************/
    fun isGoogle(): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == "google"
    }
    fun goGoogleSetting(context: Context) {
        showActivity(context, "com.android.settings")
    }

}