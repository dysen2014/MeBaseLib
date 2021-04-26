package com.dysen.meapp

import android.os.Bundle
import com.dysen.baselib.base.BaseActivity
import com.me.welcome.WelcomeActivity
import com.me.welcome.WelcomeModel

class MainActivity : BaseActivity() {

    var datas: MutableList<WelcomeModel> = mutableListOf()

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
            datas.add(WelcomeModel(R.drawable.bg_orange_rcbtn,imageDescription = "在这里\n你可以听到周围人的心声", imgUrl = "https://cdn.pixabay.com/photo/2021/01/15/17/01/green-5919790_1280.jpg"))
            datas.add(WelcomeModel(imageDescription = "在这里 TA会在下一秒遇见你",imgUrl = "https://cdn.pixabay.com/photo/2020/12/10/09/22/beach-front-5819728_1280.jpg"))
            datas.add(WelcomeModel(imageDescription = "在这里\n不再错过可以改变你一生的人", imgUrl = "https://cdn.pixabay.com/photo/2020/11/07/13/07/waves-5720916_1280.jpg"))

        WelcomeActivity.newInstance(this,datas, true)


    }
}