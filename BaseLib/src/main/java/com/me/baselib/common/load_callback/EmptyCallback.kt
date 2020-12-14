package com.me.baselib.common.load_callback


import com.dysen.baselib.R
import com.kingja.loadsir.callback.Callback


open class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}