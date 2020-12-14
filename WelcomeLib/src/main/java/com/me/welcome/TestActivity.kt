package com.me.welcome

import android.os.Bundle
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.data.Keys
import com.dysen.baselib.model.LiveDataManager
import com.dysen.baselib.utils.ColorUtil
import com.dysen.welcome.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {

    var datas = mutableListOf<WelcomeModel>()

    override fun layoutId(): Int = R.layout.activity_test

    override fun initView(savedInstanceState: Bundle?) {

        initData()
        LiveDataManager.instance?.with<Boolean>(Keys.WELCOME_BACK)?.observer(this, {
            textView.text = "erjnvejvnjevnj"

        })
        textView.setOnClickListener {

            textView.text = "1888181181"
            WelcomeActivity.newInstance(
                this,
                imgs = datas
            )
        }
    }

    fun initData() {
        for (i in 0..10) {
            datas.add(WelcomeModel(imgUrl = ColorUtil.randomImage()))
        }
    }
}