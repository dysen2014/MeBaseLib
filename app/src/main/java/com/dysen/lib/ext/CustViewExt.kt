package com.dysen.lib.ext

import android.view.View
import com.dysen.baselib.common.load_callback.LoadingCallback
import com.dysen.baselib.utils.SettingUtil
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

fun LoadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadsir = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadsir.showCallback(LoadingCallback::class.java)
    SettingUtil.setLoadingColor(SettingUtil.getColor(view.context.applicationContext), loadsir)
    return loadsir
}