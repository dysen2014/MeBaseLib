package com.dysen.baselib.common.base_recycler_adapter

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.dysen.baselib.R
import com.dysen.baselib.utils.Tools
import com.dysen.baselib.widgets.MeRecyclerView
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenu
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem

abstract class CommonAdapte<T> : SuperRecyclerAdapter<T> {

    private var layoutResId: Int = 0
    private var mValueList: MutableList<T> = mutableListOf()

    constructor(layoutResId: Int) {
        this.layoutResId = layoutResId
        setDatas(mValueList)
    }

    constructor(layoutResId: Int, valueList: MutableList<T>) {
        this.mValueList = valueList
        this.layoutResId = layoutResId
        setDatas(mValueList)
    }

    override fun setDatas(items: MutableList<T>?) {
        refreshList(items)
        super.setDatas(items)
    }

    override fun onBindViewHolder(holder: SuperRecyclerHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.context, R.anim.trans_scale_alpha))

        super.onBindViewHolder(holder, position)
    }
    open fun layoutResId(layoutResId: Int):Int {
        return if (mValueList.isEmpty()) R.layout.layout_common_empty else layoutResId
    }

    open fun <T> refreshList(mValueList: MutableList<T>?){
        Tools.setIsVisible(MeRecyclerView.emptyLayout, mValueList?.isEmpty() ?: true)
        Tools.setIsVisible(MeRecyclerView.swipeRecyclerView, mValueList?.isNotEmpty() ?: false)
//        MeRecyclerView.emptyRefresh?.setOnClickListener {
//            MeRecyclerView.emptyRefresh?.text = "Touch me"
//            notifyDataSetChanged()
//
//        }
//        if (mValueList?.isNotEmpty()!!)
//            notifyDataSetChanged()
    }



    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */

    open val mSwipeMenuCreator: SwipeMenuCreator = object : SwipeMenuCreator {
        override fun onCreateMenu(swipeLeftMenu: SwipeMenu, swipeRightMenu: SwipeMenu, position: Int) {
            val width = Tools.app.resources.getDimensionPixelSize(R.dimen.dp_48)
//            val height = Tools.app.resources.getDimensionPixelSize(R.dimen.dp_32)

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
//            val width = ViewGroup.LayoutParams.WRAP_CONTENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT - Tools.app.resources.getDimensionPixelSize(R.dimen.dp_16)

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            run {
//                val addItem = SwipeMenuItem(this@AddPeopleActivity).setBackground(R.drawable.selector_green)
//                        .setImage(R.drawable.ic_action_add)
//                        .setWidth(width)
//                        .setHeight(height)
//                swipeLeftMenu.addMenuItem(addItem) // 添加菜单到左侧。
                val closeItem = SwipeMenuItem(context).setBackground(R.drawable.btn_bg_rect_app_red)
                    .setImage(R.mipmap.ic_action_delete)
//                    .setText("删除")
//                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height)
//                swipeLeftMenu.addMenuItem(closeItem) // 添加菜单到左侧。
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            run {
                val deleteItem = SwipeMenuItem(context).setBackground(R.drawable.btn_bg_rect_app_red)
                    .setImage(R.mipmap.ic_action_delete)
//                    .setText("删除")
//                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height)
                swipeRightMenu.addMenuItem(deleteItem) // 添加菜单到右侧。
//                val addItem = SwipeMenuItem(this@AddPeopleActivity).setBackground(R.drawable.selector_green)
//                        .setText("添加")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height)
//                swipeRightMenu.addMenuItem(addItem) // 添加菜单到右侧。
            }
        }
    }

    /**
     * RecyclerView的Item的Menu点击监听。
     */

    open val mItemMenuClickListener = OnItemMenuClickListener { menuBridge, position ->
        menuBridge.closeMenu()
        val direction = menuBridge.direction // 左侧还是右侧菜单。
        val menuPosition = menuBridge.position // 菜单在RecyclerView的Item中的Position。
        if (mValueList.size > 0) {
            mValueList.removeAt(position)
            println("${mValueList.size}======${mValueList}")
            setDatas(mValueList)
        } else {
            Tools.showTip("请至少保留一项")
        }
    }

    override fun convert(holder: SuperRecyclerHolder?, t: T?, layoutType: Int, position: Int) {
    }

    override fun getLayoutAsViewType(t: T?, position: Int): Int = layoutResId
}