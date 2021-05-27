package com.dysen.lib.test

import android.Manifest
import android.os.Bundle
import android.view.ViewGroup
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.utils.rxRequestPermissions
import com.dysen.lib.R
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_video_info.*

class WebviewActy : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_webview

    override fun initView(savedInstanceState: Bundle?) {
        initWeb()
    }

    private fun initWeb() {
        rxRequestPermissions(this, Manifest.permission.INTERNET, describe = "网络") {
//                VideoRecordActivity.newInstance(this)
            var mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(web, ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://www.jd.com")


        }
    }
}