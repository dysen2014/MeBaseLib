package com.me.baselib.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.text.ClipboardManager
import com.dysen.baselib.R
import com.dysen.baselib.ui.scan.CustomScanActivity
import com.dysen.baselib.utils.SharedPreUtils
import com.google.zxing.integration.android.IntentIntegrator
import com.dysen.baselib.utils.Tools
import com.kongzue.dialog.util.DialogSettings
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.mmkv.MMKV

/**
 * dysen.
 * dy.sen@qq.com    11/4/20  10:32 AM

 * Info：
 */
open class AppContext : Application() {

    companion object {

        @JvmStatic
        lateinit var mSpUtils: SharedPreUtils

        @JvmStatic
        var app: AppContext? = null
            private set
        private var mContext: Context? = null

        @JvmStatic
        val appContext: Context?
            get() = app

        val appResources: Resources
            get() = app!!.resources

        /**
         * 实现文本复制功能
         * add by wangqianzhou
         *
         * @param content
         */
        fun copy(content: String, context: Context) {
            try {
                // 得到剪贴板管理器
                val cmb = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                cmb.text = content.trim { it <= ' ' }
                Tools.showTip(Tools.getString(R.string.user_fzcg))
            } catch (e: Exception) {
            }
        }

        /**
         * 实现粘贴功能
         * add by wangqianzhou
         *
         * @param context
         * @return
         */
        fun paste(context: Context) {
            // 得到剪贴板管理器
            val cmb = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            var str = ""
            cmb.text?.apply {
                str = this.toString().trim { it <= ' ' }

                if (str.isNullOrEmpty()) {
                    Tools.showTip(Tools.getString(R.string.user_ztsb))
                }
            }
            str
        }

        /**
         * scanQR
         * @param activity
         */
         fun customScan(activity: Activity?) {
            IntentIntegrator(activity)
                .setOrientationLocked(true)
                .setPrompt(Tools.getString(R.string.scan_tip))
                .setBeepEnabled(true)
                .setTimeout(1000 * 30.toLong()) //默认扫描30S
                .setCaptureActivity(CustomScanActivity::class.java) // 设置自定义的activity是CustomActivity
                .initiateScan() // 初始化扫描
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        mSpUtils = SharedPreUtils.getInstance(this)!!
        initMethods()
    }

    private fun initMethods() {
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS

        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")


    }

    /**
     * 分包
     *
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }

    //static 代码段可以防止内存泄露
    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, android.R.color.darker_gray);//全局设置主题颜色
            ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

}