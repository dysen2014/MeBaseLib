package com.dysen.baselib.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException


/**
 * dysen.
 * dy.sen@qq.com    8/12/20  2:34 PM

 * Info：
 */
object AppUtils {
    @JvmStatic
    fun getPackageInfo(context: Context): PackageInfo {

        val pm: PackageManager = context.packageManager
        return pm.getPackageInfo(context.packageName, 0)
    }

    fun getPackageName(context: Context): String {
        return getPackageInfo(context).packageName
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    fun getAppVersionName(context: Context): String {

        var versionName: String = ""

        val p1: PackageInfo = getPackageInfo(context)
        versionName = p1.versionName
        if (versionName == "") {
            return ""
        }
        return versionName;
    }

    fun getSDKVersionName(): String? {
        return Build.VERSION.RELEASE
    }

    fun getHandSetInfo(): String {
        return """
            手机厂商:   ${Build.MANUFACTURER}
            手机品牌:   ${Build.BRAND}
            手机型号:   ${Build.MODEL}
            SDK版本:   ${Build.VERSION.SDK}
            系统版本:   ${Build.VERSION.RELEASE}
        """
    }

    fun jumpInstall(context: Activity, file1: File, authority: String?, code: Int) {
        try {
            val command = arrayOf("chmod", "777", file1.toString())
            val builder = ProcessBuilder(*command)
            builder.start()
        } catch (ignored: IOException) {
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            //适配Android Q,注意mFilePath是通过ContentResolver得到的，上述有相关代码
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file1), "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val contentUri = FileProvider.getUriForFile(
                context,
                authority!!, file1
            )
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            //兼容8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val hasInstallPermission = context.packageManager.canRequestPackageInstalls()
                if (!hasInstallPermission) {
                    Tools.showTip("没有权限")
                    startInstallPermissionSettingActivity(context)
                    /*ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, code);*/return
                } else {
                    context.startActivity(intent)
                }
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file1), "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startInstallPermissionSettingActivity(context: Activity) {
        //注意这个是8.0新API
        val intent = Intent(
            Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
            Uri.parse("package:" + getPackageName(context))
        )
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivityForResult(intent, 0xA1)
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     */
    fun openApplicationMarket(context: Context, packageName: String) {
        try {
            val str = "market://details?id=$packageName"
            val localIntent = Intent(Intent.ACTION_VIEW)
            localIntent.data = Uri.parse(str)
            context.startActivity(localIntent)
        } catch (e: Exception) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace()
            Toast.makeText(
                context,
                "打开应用商店失败",
                Toast.LENGTH_SHORT
            ).show()
            // 调用系统浏览器进入商城
            val url = "http://app.mi.com/detail/163525?ref=search"
            openLinkBySystem(context, url)
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    fun openLinkBySystem(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

    /**
     * 跳转到指定应用的首页
     */
    private fun showActivity(context: Context, @NonNull packageName: String) {
        val intent: Intent = context.packageManager.getLaunchIntentForPackage(packageName) ?: Intent()
        context.startActivity(intent)
    }

    /**
     * 跳转到指定应用的指定页面
     */
    private fun showActivity(context: Context, @NonNull packageName: String, @NonNull activityDir: String) {
        val intent = Intent()
        intent.component = ComponentName(packageName, activityDir)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}