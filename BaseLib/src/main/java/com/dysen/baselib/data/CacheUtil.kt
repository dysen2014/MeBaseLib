package com.dysen.baselib.data

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV

object CacheUtil {
    val kv = MMKV.mmkvWithID("app")

    /**
     * 获取保存的信息
     */
//    fun <T> gList(key: String): MutableList<T>? {
//        val jsonData = kv.decodeString(key)
//        return if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
//            Gson().fromJson<MutableList<T>>(jsonData, object : TypeToken<MutableList<DataRes>>() {}.type)
//        } else
//            null
//    }

    /**
     * 获取保存的信息
     */
    fun <T> gObj(key: String, clzz: Class<T>): T? {
        val jsonData = kv.decodeString(key)
        return parseObject(jsonData, clzz)
    }

    /**
     * 设置信息
     */
    fun <T> sObj(key: String, t: T) {
        kv.encode(key, Gson().toJson(t))
    }

    fun <T> parseObject(jsonData: String?, cls: Class<T>): T? {
        return if (jsonData != null) {
            val gson = Gson()
            gson.fromJson(jsonData, cls)
        } else
            null
    }

    fun <T> parseList(jsonData: String?, cls: Class<T>): MutableList<T>? {
        return if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson().fromJson<MutableList<T>>(jsonData, object : TypeToken<MutableList<T>>() {}.type)
        } else
            null
    }

    fun isBool(key: String, bl: Boolean = false): Boolean = kv.decodeBool(key, bl)
    fun sBool(key: String, bl: Boolean) = kv.encode(key, bl)
    fun gInt(key: String): Int = kv.decodeInt(key, 0)
    fun sInt(key: String, data: Int) = kv.encode(key, data)
    fun gLong(key: String): Long = kv.decodeLong(key, 0)
    fun sLong(key: String, data: Long) = kv.encode(key, data)
    fun gFloat(key: String): Float = kv.decodeFloat(key, 0.0f)
    fun sFloat(key: String, data: Float) = kv.encode(key, data)
    fun gDouble(key: String): Double = kv.decodeDouble(key, 0.0)
    fun sDouble(key: String, data: Double) = kv.encode(key, data)
    fun gString(key: String): String = kv.decodeString(key, "")
    fun sString(key: String, data: String) = kv.encode(key, data)

}