package com.dysen.baselib.common.base_recycler_adapter

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dysen.baselib.R
import java.util.*

/**
 * @email dy.sen@qq.com
 * created by dysen on 2020/05/13 - 上午09:59
 * @info
 */
class BasePagerAdapter {
    class ViewAdapter : PagerAdapter {
        private var views: MutableList<View> = ArrayList() //数据源
        fun setDatas(items: List<View>?) {
            views.clear()
            views.addAll(items!!)
            notifyDataSetChanged()
        }

        constructor() {}
        constructor(views: MutableList<View>) {
            this.views = views
        }

        //数据源的数目
        override fun getCount(): Int {
            return views.size
        }

        //view是否由对象产生，官方写arg0==arg1即可
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        //对应页卡添加上数据
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = views[position]
            container.addView(view)
            return view
        }

        //销毁一个页卡(即ViewPager的一个item)
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = views[position]
            container.removeView(view)
        }
    }

    class FragmentAdapter : FragmentPagerAdapter {
        private var fragments: MutableList<Fragment>? = null
        private var mTitles: List<String>?
        fun setDatas(items: List<Fragment>?) {
            fragments!!.clear()
            fragments!!.addAll(items!!)
            notifyDataSetChanged()
        }

        constructor(fm: FragmentManager?, titles: List<String>?) : super(fm!!) {
            mTitles = titles
        }

        constructor(fm: FragmentManager?, fragments: MutableList<Fragment>?, titles: List<String>?) : super(fm!!) {
            this.fragments = fragments
            mTitles = titles
        }

        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (mTitles == null) {
                ""
            } else mTitles!![position]
        }

        override fun getCount(): Int {
            return fragments!!.size
        }
    }

    class ViewPager2Adapter(private val data: MutableList<String> = mutableListOf<String>()) : RecyclerView.Adapter<ViewPager2Adapter.ViewPager2Holder>() {

        fun setNewData(newData: List<String>) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2Holder {
            val tv = TextView(parent.context).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                setTextColor(Color.RED)
                gravity = Gravity.CENTER
            }
            tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return ViewPager2Holder(
                    tv
            )
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ViewPager2Holder, position: Int) {
            (holder.itemView as TextView).text = data[position]
        }

        class ViewPager2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }

    abstract class RclAdapter<T>(@LayoutRes private val layoutResId: Int, mValueList: MutableList<T> = mutableListOf()) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId, mValueList) {
        override fun convert(holder: BaseViewHolder, item: T) {
//            holder.setText(R.id.tv_name, item)
        }
    }

}