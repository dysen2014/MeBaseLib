package com.dysen.paging3

import android.os.Bundle
import androidx.lifecycle.rxLifeScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.common.base_recycler_adapter.MeAdapter
import com.dysen.baselib.common.base_recycler_adapter.SuperRecyclerHolder
import com.dysen.baselib.widgets.MeRecyclerView
import com.dysen.common.base_recycler_adapter.ViewUtils
import com.dysen.paging3.http.Api
import com.dysen.paging3.http.res.Res
import com.me.optionbarview.OptionBarView
import kotlinx.android.synthetic.main.activity_main.*
import rxhttp.wrapper.param.RxHttp

class MainActivity : BaseActivity() {

    var mAdapter: MeAdapter<Res.Item>? = null
    var res: Res.ProjListsRes? = null
    var datas = mutableListOf<Res.Item>()


    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        initAdapter()
        initClick()
        initData()
    }


    private fun initAdapter() {
        mAdapter = object : MeAdapter<Res.Item>(R.layout.projects_item) {
            override fun convert(holder: SuperRecyclerHolder?, t: Res.Item?, layoutType: Int, position: Int) {
                super.convert(holder, t, layoutType, position)
                holder?.apply {
                    t?.let {
                        var opv1 = getViewById(R.id.opv1) as OptionBarView
                        var opv2 = getViewById(R.id.opv2) as OptionBarView

                        opv1.setLeftText(t.id)
                        opv1.setRightText(t.name)
                        opv2.setLeftText(t.description)
                        opv2.setRightText(t.stargazersCount)
                    }
                }
            }
        }
        MeRecyclerView.swipeRecyclerView?.apply {
            setSwipeMenuCreator(mAdapter?.mSwipeMenuCreator)

            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
    }}

    private fun initClick() {

//        MeRecyclerView.smartRefreshLayout?.apply {
//            setEnableLoadMore(false)
//            setOnRefreshListener {
//                mAdapter?.setDatas(datas)
//                ViewUtils.closeRefresh(this)
//            }
//        }
    }

    fun initData() {
        tv.text = "Paging3"
        rxLifeScope.launch {
            RxHttp.setDebug(true)
            res = Api.getProjects()
            datas = res?.items as MutableList<Res.Item>
            mAdapter?.setDatas(datas)
        }
//        rcl.layoutParams = LinearLayoutManager(this)

    }
}