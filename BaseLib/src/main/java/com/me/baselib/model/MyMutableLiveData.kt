package com.me.baselib.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * dysen.
 * dy.sen@qq.com    9/30/20  2:03 PM
 *
 *
 * Info：hook的执行时间？注册观察者的时候
 */
class MyMutableLiveData<T> : MutableLiveData<T>() {
    //目的：使得在observe被调用的时候，能够保证 if (observer.mLastVersion >= mVersion) （livedata源码里面的）成立
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        try {
            hook(observer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, observer)
    }

    /**要修改observer.mLastVersion的值那么思考：（逆向思维）
     * mLastVersion-》observer-》iterator.next().getValue()-》mObservers
     * 反射使用的时候，正好相反
     *
     * mObservers-》函数（iterator.next().getValue()）-》observer-》mLastVersion
     * 通过hook，将observer.mLastVersion = mVersion
     * @param observer
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        val fieldmObservers = liveDataClass.getDeclaredField("mObservers")
        fieldmObservers.isAccessible = true
        val mObservers = fieldmObservers[this]
        val mObserversClass: Class<*> = mObservers.javaClass
        val methodget = mObserversClass.getDeclaredMethod("get", Any::class.java)
        methodget.isAccessible = true
        val entry = methodget.invoke(mObservers, observer)
        val observerWrapper = (entry as Map.Entry<*, *>).value!!
        val mObserver: Class<*>? = observerWrapper.javaClass.superclass //observer
        val mLastVersion = mObserver!!.getDeclaredField("mLastVersion")
        mLastVersion.isAccessible = true
        val mVersion = liveDataClass.getDeclaredField("mVersion")
        mVersion.isAccessible = true
        val mVersionObject = mVersion[this]
        mLastVersion[observerWrapper] = mVersionObject
    }
}