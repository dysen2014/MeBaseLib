package com.dysen.baselib.ui.scan

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.dysen.baselib.R
import com.dysen.baselib.base.BaseActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DecoratedBarcodeView.TorchListener

/**
 * 扫描二维码
 * @author dysen
 */
class CustomScanActivity : BaseActivity(), TorchListener {
    var dbvCustom: DecoratedBarcodeView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null
    private var captureManager: CaptureManager? = null
    private var context: Activity? = null
    private var isLightOn = false
    private var ivFlashLight: ImageView? = null

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
        captureManager?.onSaveInstanceState(outState)
    }


    override fun layoutId(): Int {
        return R.layout.activity_custom_scan
    }


    override fun initView(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        context = this
        dbvCustom = findViewById(R.id.dbv_custom)
        ivFlashLight = findViewById(R.id.iv_flash_light)
        ivBack = findViewById(R.id.iv_back)
        tvTitle = findViewById(R.id.tv_title)
        ivBack?.setOnClickListener { finish() }
        if (!hasFlash()) {
            ivFlashLight?.visibility = View.GONE
        }
        dbvCustom?.setTorchListener(this)
        //重要代码，初始化捕获
        captureManager = CaptureManager(this, dbvCustom)
        captureManager?.initializeFromIntent(intent, savedInstanceState)
        captureManager?.decode()

    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun switchFlashlight(view: View?) { //        Tools.toast("闪关灯状态："+!isLightOn);
        if (isLightOn) {
            dbvCustom!!.setTorchOff()
        } else {
            dbvCustom!!.setTorchOn()
        }
    }

    // torch 手电筒
    override fun onTorchOn() {
        ivFlashLight!!.setImageResource(R.drawable.btn_flash_off)
        isLightOn = true
    }

    override fun onTorchOff() {
        ivFlashLight!!.setImageResource(R.drawable.btn_flash_on)
        isLightOn = false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return dbvCustom!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        captureManager!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        captureManager!!.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager!!.onDestroy()
    }
}