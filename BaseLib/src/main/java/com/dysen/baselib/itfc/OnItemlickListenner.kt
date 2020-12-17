package com.dysen.baselib.itfc

import android.view.View

/**
 *  @package com.dysen.test.utils
 *  @email  dy.sen@qq.com
 *  created by dysen on 2020-02-10 - 07:32
 *  @info
 */
interface OnItemlickListenner {
    fun onClick(v: View, position:Int,data:String)
}