package com.me.baselib.utils

import android.util.Log

/**
 * @package com.dysen.tool
 * @email dy.sen@qq.com
 * created by dysen on 2019/11/5 - 下午18:04
 * @info Log的辅助类
 */
class LogUtils {
    companion object {
        const val TAG = "dysen"
        // 是否需要打印bug，可以在application的onCreate函数里面初始化
        var isDebug = true

        // 下面五个是默认tag的函数
        fun i(msg: String) {
            if (isDebug) {
                Log.i(TAG, msg)
            }
        }

        fun d(msg: String) {
            if (isDebug) {
                Log.d(TAG, msg)
            }
        }

        fun e(msg: String) {
            if (isDebug) {
                e(TAG, msg)
            }
        }

        fun v(msg: String) {
            if (isDebug) {
                Log.v(TAG, msg)
            }
        }

        fun w(msg: String) {
            if (isDebug) {
                Log.w(TAG, msg)
            }
        }

        fun a(msg: String) {
            if (isDebug) {
                Log.w(TAG, msg)
            }
        }

        // 下面是传入自定义tag的函数
        fun i(tag: String?, msg: String) {
            if (isDebug) {
                Log.i(tag, msg)
            }
        }

        fun d(tag: String?, msg: String) {
            if (isDebug) {
                Log.d(tag, msg)
            }
        }

        fun e(tag: String?, msg: String) {
            if (isDebug) {
                Log.e(tag, msg)
            }
        }

        fun v(tag: String?, msg: String) {
            if (isDebug) {
                Log.v(tag, msg)
            }
        }

        fun w(tag: String?, msg: String) {
            if (isDebug) {
                Log.w(tag, msg)
            }
        }

        fun v(tag: String?, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.v(tag, msg, tr)
            }
        }

        fun d(tag: String?, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.d(tag, msg, tr)
            }
        }

        fun i(tag: String?, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.i(tag, msg, tr)
            }
        }

        fun w(tag: String?, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.w(tag, msg, tr)
            }
        }

        fun e(tag: String?, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.e(tag, msg, tr)
            }
        }
    }

    //    public static boolean isDebug = BaseAppContext.getInstance().getDeBugMode();
    init { /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }
}