package com.dysen.baselib.ui.country_code

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cn.zhikaizhang.indexview.Util
import com.dysen.baselib.R
import com.dysen.baselib.base.AppContext
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.common.base_recycler_adapter.SuperRecyclerAdapter
import com.dysen.baselib.common.base_recycler_adapter.SuperRecyclerHolder
import com.dysen.baselib.data.CacheUtil
import com.dysen.baselib.data.Keys
import com.dysen.baselib.data.entity.CountryData
import com.dysen.baselib.model.LiveDataManager
import com.dysen.baselib.utils.AssetsUtils
import com.dysen.baselib.utils.PinYinUtils.getPinyin
import com.dysen.baselib.utils.SharedPreUtils
import com.dysen.baselib.widgets.WaveSideBarView
import kotlinx.android.synthetic.main.activity_country.*
import java.util.*

class CountryActivity : BaseActivity() {
    private var list_data: MutableList<CountryData> = ArrayList()
    private val show_data: MutableList<CountryData> = ArrayList()
    private var mAdapter: SuperRecyclerAdapter<CountryData>? = null
    private var language: String? = null
    private var onSelectIndexItemListener: WaveSideBarView.OnSelectIndexItemListener? = null

    override fun layoutId(): Int {
        return R.layout.activity_country
    }

    override fun initView(savedInstanceState: Bundle?) {
        initView()
        initData()
        initClick()
        initSearchView(sv_search)
        sv_search?.clearFocus()
    }

    fun initClick() {
        sv_search?.setOnClickListener { view: View ->
            (view as SearchView).onActionViewExpanded()
            line?.visibility = View.INVISIBLE
            tv_tip?.visibility = View.INVISIBLE
        }
        // 搜索按钮相关
        sv_search?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            // 当搜索内容改变时触发该方法
            override fun onQueryTextChange(newText: String): Boolean {
                var newText = newText
                if (!TextUtils.isEmpty(newText)) {
                    newText = newText.toUpperCase()
                    show_data.clear()
                    for (model in list_data) {
                        if (if (SharedPreUtils.en == language) model.shortNameEn!!.toUpperCase()
                                .startsWith(
                                    newText
                                ) else model.shortName!!.toUpperCase().startsWith(newText)
                        ) {
                            show_data.add(model)
                        }
                    }
                    sortList(show_data)
                    mAdapter?.setDatas(show_data)
                } else {
                    mAdapter?.setDatas(list_data)
                }
                return false
            }
        })
    }

    fun initView() {
        language = AppContext.mSpUtils[SharedPreUtils.KEY_APP_LANGUAGE, SharedPreUtils.cn]
        val isEnglish = SharedPreUtils.en == language

        mAdapter = object : SuperRecyclerAdapter<CountryData>(this) {
            private var countryName: String? = null
            private var countryName2: String? = null
            override fun convert(holder: SuperRecyclerHolder?, country: CountryData?, layoutType: Int, position: Int) {


                holder?.apply {
                    country?.let {

                        countryName = if (isEnglish) country.shortNameEn else country.shortName
                        countryName2 = if (isEnglish) country.shortName else country.shortNameEn
                        setText(R.id.tv_name, country.code + "\t" + countryName)
                        setText(R.id.tv_name2, "($countryName2)")
                        val index = if (isEnglish) Util.getIndex(country.shortNameEn) else Util.getIndex(country.shortName)
                        if (position == 0 || (if (isEnglish) Util.getIndex(
                                mAdapter!!.valueList[position - 1].shortNameEn
                            ) else Util.getIndex(mAdapter!!.valueList[position - 1].shortName)) != index
                        ) {
                            setVisibility(R.id.tv_index, View.VISIBLE)
                            setText(R.id.tv_index, index.toString())
                        } else {
                            setVisibility(R.id.tv_index, View.GONE)
                        }
                        setOnItemClickListenner(View.OnClickListener {

                            //把选择的内容通知观察者
                            LiveDataManager.instance?.with<CountryData>(Keys.COUNTRY_CODE)
                                ?.postValue(country)
                            CacheUtil.sObj(Keys.COUNTRY_CODE, country)

                            finish()
                        })

                    }
                }
            }

            override fun getLayoutAsViewType(t: CountryData?, position: Int): Int {
                return R.layout.layout_item_country
            }
        }
        rcl_contacts?.layoutManager = LinearLayoutManager(this)
        rcl_contacts?.adapter = mAdapter
        side_bar.setTextSize(13)
        onSelectIndexItemListener = object : WaveSideBarView.OnSelectIndexItemListener {
            override fun onSelectIndexItem(letter: String?) {
                for (i in list_data.indices) {
                    val index =
                        if (isEnglish) Util.getIndex(list_data[i].shortNameEn) else Util.getIndex(list_data[i].shortName)
                    if (index.toString() == letter) {
                        (rcl_contacts?.layoutManager as LinearLayoutManager?)?.scrollToPositionWithOffset(i, 0)
                        return
                    }
                }
            }
        }
        side_bar.setOnSelectIndexItemListener(onSelectIndexItemListener)

    }

    private fun initData() {
        val countrys =
            AssetsUtils.getListData(this, "country.json", "countrys") as MutableList<CountryData>
        list_data.addAll(countrys)
        list_data = sortList(list_data)
        mAdapter?.setDatas(this.list_data)
    }

    private fun initSearchView(mSearch: SearchView?) {
        mSearch?.let {
            val textView =
                mSearch.findViewById<View>(R.id.search_src_text) as EditText
            textView.setHintTextColor(ContextCompat.getColor(this, R.color.text_color_gray))
            textView.textSize = 14f
            mSearch.queryHint = this.getString(R.string.country_tip)
        }
    }

    fun sortList(contactsBeans: MutableList<CountryData>): MutableList<CountryData> { //对集合排序
        contactsBeans.sortWith(Comparator sort@{ lhs: CountryData, rhs: CountryData ->
            if (SharedPreUtils.en == language) {
                return@sort lhs.shortNameEn!!.toUpperCase()
                    .compareTo(rhs.shortNameEn!!.toUpperCase())
            } else { //根据拼音进行排序
                return@sort getPinyin(lhs.shortName!!).toUpperCase()
                    .compareTo(getPinyin(rhs.shortName!!).toUpperCase())
            }
        })
        return contactsBeans
    }
}