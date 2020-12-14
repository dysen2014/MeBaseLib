package com.me.baselib.common.base_recycler_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.dysen.baselib.common.base_recycler_adapter.SuperRecyclerHolder.Companion.createViewHolder
import java.util.*

/**
 * @email dy.sen@qq.com
 * created by dysen on 2018/9/19 - 上午10:38
 * @info
 */
abstract class SuperRecyclerAdapter<T> : RecyclerView.Adapter<SuperRecyclerHolder> {
    private var mCtx: Context? = null
    private var mValueList: MutableList<T> = ArrayList()

    //empty
    private var mEmptyLayout: FrameLayout? = null

    open fun setDatas(items: MutableList<T>?) {
        mValueList.clear()
        mValueList.addAll(items!!)
        notifyDataSetChanged()
    }

    constructor() {}
    constructor(mValueList: MutableList<T>) {
        this.mValueList = mValueList
    }

    constructor(mCtx: Context) {
        this.mCtx = mCtx
    }

    constructor(mCtx: Context, mValueList: MutableList<T>) {
        this.mCtx = mCtx
        this.mValueList = mValueList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperRecyclerHolder {
        mCtx = parent.context
        val itemView = LayoutInflater.from(mCtx).inflate(viewType, parent, false)
        return createViewHolder(mCtx!!, itemView)
    }

    override fun onBindViewHolder(holder: SuperRecyclerHolder, position: Int) {
        convert(holder, getValue(position), getItemViewType(position), position)
    }

    override fun getItemCount(): Int {
        return mValueList.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutAsViewType(getValue(position), position)
    }

    val valueList: MutableList<T>
        get() = mValueList
    val context: Context?
        get() = mCtx

    fun getValue(position: Int): T {
        return valueList[position]
    }

    abstract fun convert(holder: SuperRecyclerHolder?, t: T?, layoutType: Int, position: Int)

    @LayoutRes
    abstract fun getLayoutAsViewType(t: T?, position: Int): Int


}