package com.dysen.baselib.data

import android.text.TextUtils
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

object CacheUtil {
    /**
     * 获取保存的信息
     */
    fun getData(clzz: Class<*>): Class<*>? {
        val kv = MMKV.mmkvWithID("app")
        val userStr = kv.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else ({
            Gson().fromJson(userStr, clzz)
        }) as Class<*>?
    }

    /**
     * 设置信息
     */
    fun setData(key: String, clzz: Class<*>) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode(key, Gson().toJson(clzz))
    }
}