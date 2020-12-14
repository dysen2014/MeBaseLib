package com.me.baselib.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @package com.kcloudchina.member.app.utils
 * @email dy.sen@qq.com
 * created by dysen on 20/3/28. - 下午5:35
 * @info 本地数据存储
 */
class SharedPreUtils {
    constructor() { /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    constructor(context: Context?) {
        if (context == null) {
            return
        }
        sharedPreferences = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun put(key: String?, value: String?) {
        val editor = sharedPreferences!!.edit()
        editor.putString(key, value)
        SharedPreferencesCompat.apply(editor)
    }

    fun put(key: String?, value: Set<String?>?) {
        val editor = sharedPreferences!!.edit()
        editor.putStringSet(key, value)
        SharedPreferencesCompat.apply(editor)
    }

    fun put(key: String?, value: Int) {
        val editor = sharedPreferences!!.edit()
        editor.putInt(key, value)
        SharedPreferencesCompat.apply(editor)
    }

    fun put(key: String?, value: Float) {
        val editor = sharedPreferences!!.edit()
        editor.putFloat(key, value)
        SharedPreferencesCompat.apply(editor)
    }

    fun put(key: String?, value: Long) {
        val editor = sharedPreferences!!.edit()
        editor.putLong(key, value)
        SharedPreferencesCompat.apply(editor)
    }

    fun put(key: String?, value: Boolean) {
        val editor = sharedPreferences!!.edit()
        editor.putBoolean(key, value)
        SharedPreferencesCompat.apply(editor)
    }

    operator fun get(key: String?, value: String?): String? {
        return sharedPreferences!!.getString(key, value)
    }

    operator fun get(
        key: String?,
        value: Set<String?>?
    ): Set<String>? {
        return sharedPreferences!!.getStringSet(key, value)
    }

    operator fun get(key: String?, value: Int): Int {
        return sharedPreferences!!.getInt(key, value)
    }

    operator fun get(key: String?, value: Float): Float {
        return sharedPreferences!!.getFloat(key, value)
    }

    operator fun get(key: String?, value: Long): Long {
        return sharedPreferences!!.getLong(key, value)
    }

    operator fun get(key: String?, value: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, value)
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    fun putObject(key: String?, `object`: Any?) {
        val editor = sharedPreferences!!.edit()
        if (`object` is String) {
            editor.putString(key, `object` as String?)
        } else if (`object` is Int) {
            editor.putInt(key, (`object` as Int?)!!)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, (`object` as Boolean?)!!)
        } else if (`object` is Float) {
            editor.putFloat(key, (`object` as Float?)!!)
        } else if (`object` is Long) {
            editor.putLong(key, (`object` as Long?)!!)
        }
        run {}
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    fun getObject(key: String?, defaultObject: Any?): Any? {
        if (defaultObject is String) {
            return sharedPreferences!!.getString(
                key,
                defaultObject as String?
            )
        } else if (defaultObject is Int) {
            return sharedPreferences!!.getInt(key, (defaultObject as Int?)!!)
        } else if (defaultObject is Boolean) {
            return sharedPreferences!!.getBoolean(
                key,
                (defaultObject as Boolean?)!!
            )
        } else if (defaultObject is Float) {
            return sharedPreferences!!.getFloat(
                key,
                (defaultObject as Float?)!!
            )
        } else if (defaultObject is Long) {
            return sharedPreferences!!.getLong(
                key,
                (defaultObject as Long?)!!
            )
        } else if (defaultObject is Boolean) {
            return sharedPreferences!!.getBoolean(
                key,
                (defaultObject as Boolean?)!!
            )
        }
        return null
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    fun remove(key: String?) {
        val editor = sharedPreferences!!.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    operator fun contains(key: String?): Boolean {
        return sharedPreferences!!.contains(key)
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    val all: Map<String, *>
        get() = sharedPreferences!!.all

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private object SharedPreferencesCompat {
        private val sApplyMethod =
            findApplyMethod()

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz: Class<*> = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }
            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(
                        editor
                    )
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }
            editor.commit()
        }
    }

    companion object {
        /**
         * 保存在手机里面的文件名
         */
        const val FILE_NAME = "share_data"
        var sharedPreUtils: SharedPreUtils? = null
        var sharedPreferences: SharedPreferences? = null
        var SELECT_ID = "select_id"
        const val zh_cn = "zh_cn"
        const val cn = "cn"
        const val en = "en"
        const val ko = "ko"
        const val KEY_LOGIN_NAME = "login_name"
        const val KEY_COUNTRY_CODE_NAME = "key_country_code_name"
        const val KEY_LOGIN_CODE = "login_code"
        const val KEY_SAVE_TIME = "save_time"
        const val KEY_APP_LANGUAGE = "app_language"
        const val KEY_IS_ONLINE = "KEY_IS_ONLINE"
        fun getInstance(context: Context?): SharedPreUtils? {
            sharedPreUtils = SharedPreUtils(context)
            return sharedPreUtils
        }
    }
}