package com.dysen.baselib.utils

import android.content.SharedPreferences

/**
 * @author dysen
 * dy.sen@qq.com     11/27/20 8:43 AM
 *
 * Info：
 */
class SpUtils (private val sp: SharedPreferences) : SharedPreferences by sp {
    operator fun <T> set(key: String, isCommit: Boolean = false , value: T) {
        with(sp.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                is Set<*> -> (value as? Set<String>)?.let { putStringSet(key, it) }
                else -> throw IllegalArgumentException("unsupported type of value")
            }
            if (isCommit) {
                commit()
            } else {
                apply()
            }
        }
    }

    operator fun <T> get(key: String, default: T): T = with(sp) {
        when (default) {
            is Long -> getLong(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            is String -> getString(key, default)
            is Set<*> -> getStringSet(key, mutableSetOf())
            else -> throw IllegalArgumentException("unsupported type of value")
        } as T
    }
}