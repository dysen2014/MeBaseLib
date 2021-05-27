package com.dysen.baselib.model

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer

/**
 * dysen.
 * dy.sen@qq.com    9/30/20  2:10 PM
 *
 *
 * Info：组件通信 ，数据共享
 */
open class GLiveData<T> {
    //数据持有类， 持有的数据
    private var mPendingData: T? = null

    //观察者的集合
    private var mObservers: MutableList<ObserverWrapper> = mutableListOf()
    private var mVersion = -1

    /**
     * 注册观察者的方法
     * @param lifecycleOwner
     * @param observer
     */
    fun observer(lifecycleOwner: LifecycleOwner, observer: Observer<T>?) {
        //如果当前传进来的组件的生命周期已经结束，就直接返回
        if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }
        val mObserverWrapper: ObserverWrapper = ObserverWrapper()
        mObserverWrapper.observer = observer
        //为了解决还没 注册的观察者 ，也能监听到的问题
        mObserverWrapper.mLastVersion = -1
        mObserverWrapper.lifecycle = lifecycleOwner.lifecycle
        mObserverWrapper.myLifecycleBound = MyLifecycleBound()
        mObservers.add(mObserverWrapper)
        mObserverWrapper.myLifecycleBound?.let {
            lifecycleOwner.lifecycle.addObserver(it)
        }
        //有个问题
        //disPatingValue();
    }

    /**
     * 绑定数据 发送通知
     * @param vaule
     */
    fun postValue(vaule: T?) {
        mPendingData = vaule
        mVersion++
        disPatingValue()
    }

    /**
     * 遍历所有的观察者
     */
    fun disPatingValue() {
        for (mObserverWrapper in mObservers) {
            toChanged(mObserverWrapper)
        }
    }

    /**
     * 回调所有的观察者
     */
    fun toChanged(mObserverWrapper: ObserverWrapper) {
        //判断生命周期
        if (mObserverWrapper.lifecycle?.currentState != Lifecycle.State.RESUMED) {
            return
        }
        //判断匹配版本号，为了防止生命周期改变的时候，观察者会被回调
        if (mObserverWrapper.mLastVersion >= mVersion) {
            return
        }
        mObserverWrapper.mLastVersion = mVersion
        mObserverWrapper.observer?.onChanged(mPendingData)
    }

    /**
     * 组件的生命周期的回调类
     */
    inner class MyLifecycleBound : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            source?.run {
                if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    remove(source.lifecycle)
                }
                Log.e("TAG-----", "${source.lifecycle.currentState} <<<--->>>${source.javaClass.simpleName}")
                if (mPendingData != null) {
                    disPatingValue()
                }
            }
        }
    }

    /**
     * 观察者的封装类
     *
     */
    inner class ObserverWrapper {
        //观察者
        var observer: Observer<T>? = null
        var lifecycle: Lifecycle? = null
        var mLastVersion = -1

        //绑定生命周期的回调接口
        var myLifecycleBound: MyLifecycleBound? = null
    }

    fun remove(lifecycle: Lifecycle) {
        mObservers.forEach {
            println(" lifecycle= ${it.lifecycle} = ${it.lifecycle == lifecycle}")
            if (it.lifecycle == lifecycle) {
                it.myLifecycleBound?.let { t -> it.lifecycle?.removeObserver(t) }
                mObservers.remove(it)
            }
        }
    }
}