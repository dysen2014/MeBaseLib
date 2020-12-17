package com.dysen.baselib.model

import android.util.Log
import java.util.*

/**
 * dysen.
 * dy.sen@qq.com    9/30/20  2:11 PM
 *
 * Info：
 */
open class LiveDataManager private constructor() {
    //应用中所有数据持有类的集合
    private val map: MutableMap<String, GLiveData<Any>?>
    fun <T> with(key: String): GLiveData<T>? {
        if (!map.containsKey(key)) {
            map[key] = GLiveData()
        }
        return map[key] as GLiveData<T>?
    }

    fun <T> with(key: String, clazz: Class<T>?): GLiveData<T>? {
        if (!map.containsKey(key)) {
            map[key] = GLiveData()
        }
        return map[key] as GLiveData<T>?
    }
    //      public void remove(String key){

    //         if(map.containsKey(key)){
    //             map.remove(key);
    //         }
    //}
    companion object {
        private var liveDataManager: LiveDataManager? = null
        val instance: LiveDataManager?
            get() {
                if (liveDataManager != null) {
                    return liveDataManager
                }
                synchronized(LiveDataManager::class.java) {
                    if (liveDataManager == null) {
                        liveDataManager = LiveDataManager()
                    }
                }
                return liveDataManager
            }
    }

    init {
        Log.e("TAG", "LiveDataManager执行构造方法")
        map = HashMap()
    }
}