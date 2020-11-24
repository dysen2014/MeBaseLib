package com.dysen.baselib.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.dysen.baselib.R
import com.dysen.baselib.data.CacheUtil
import com.dysen.baselib.data.Keys
import com.dysen.baselib.model.LiveDataManager
import com.dysen.baselib.ui.scan.CustomScanActivity
import com.dysen.baselib.utils.StatusBarUtil
import com.dysen.baselib.utils.Tools
import com.google.zxing.integration.android.IntentIntegrator
import com.kongzue.dialog.v3.TipDialog

/**
 * dysen.
 * dy.sen@qq.com    9/16/20  11:11 AM
 *
 * Info：
 */
abstract class BaseActivity : AppActivity() {
    protected var mScanContent: String=""

    lateinit var tipDialog: TipDialog

    companion object {
        fun newInstance(
            aty: AppCompatActivity,
            cls: Class<*>,
            isFinish: Boolean = false
        ) {
            val intent = Intent(aty, cls)
            aty.startActivity(intent)
            if (isFinish)
                aty.finish()
//            aty.startActivityForResult(intent, Constant.ADD_PEOPLE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId())

        //透明状态栏
        StatusBarUtil.setTransparent(this)
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)

        initView(savedInstanceState)
    }

    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 打开等待框
     */
    fun showLoading(message: String, duration: Int = 4000) {
//        tipDialog = showTip(message, type = TipDialog.TYPE.OTHER).setTip(R.mipmap.icon_submit_success)
        tipDialog = TipDialog.showWait(this, message)
        TipDialog.dismiss(duration)
    }

    fun showTipImg(
        message: String,
        type: TipDialog.TYPE = TipDialog.TYPE.WARNING,
        duration: Int = 4000
    ): TipDialog {
        return TipDialog.show(this, message, type).setTipTime(duration)
    }

    /**
     * 关闭等待框
     */
    fun dismissLoading() {
        tipDialog?.setTipTime(200)
    }

    // 通过 onActivityResult的方法获取 扫描回来的 值
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            mScanContent = if (intentResult.contents.isNullOrEmpty()) {
                ""
            } else {
                intentResult.contents
            }
            println("把扫描到的内容通知观察者$mScanContent")
            //把扫描到的内容通知观察者
            LiveDataManager.instance?.with<String>(Keys.SCAN_CONTENT)?.postValue(mScanContent)
            CacheUtil.sString(Keys.SCAN_CONTENT, mScanContent)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /**
     * scanQR
     * @param activity
     */
    protected open fun customScan(activity: Activity?) {
        IntentIntegrator(activity)
            .setOrientationLocked(true)
            .setPrompt(Tools.getString(R.string.scan_tip))
            .setBeepEnabled(true)
            .setTimeout(1000 * 30.toLong()) //默认扫描30S
            .setCaptureActivity(CustomScanActivity::class.java) // 设置自定义的activity是CustomActivity
            .initiateScan() // 初始化扫描
    }
}