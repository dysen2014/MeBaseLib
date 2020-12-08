package com.dysen.lib

import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.dysen.baselib.base.AppContext
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.common.base_recycler_adapter.MeAdapter
import com.dysen.baselib.common.base_recycler_adapter.SuperRecyclerHolder
import com.dysen.baselib.data.CacheUtil
import com.dysen.baselib.data.Keys
import com.dysen.baselib.data.entity.CountryData
import com.dysen.baselib.model.LiveDataManager
import com.dysen.baselib.ui.country_code.CountryActivity
import com.dysen.baselib.ui.scan.CustomScanActivity
import com.dysen.baselib.utils.*
import com.dysen.baselib.widgets.MeRecyclerView
import com.dysen.baselib.widgets.TitleLayout
import com.dysen.common.base_recycler_adapter.ViewUtils
import com.dysen.lib.coil_test.CoilTestActivity
import com.dysen.lib.room_test.RoomTestActy
import com.google.android.flexbox.FlexboxLayoutManager
import com.kingja.loadsir.core.LoadService
import com.kongzue.dialog.util.BaseDialog
import com.kongzue.dialog.util.DialogSettings
import com.kongzue.dialog.v3.MessageDialog
import com.me.optionbarview.OptionBarView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    //界面状态管理者
    private lateinit var loadsirService: LoadService<Any>
    private lateinit var mAdapter: MeAdapter<String>

    private var menus =
        mutableListOf("Coil", "扫一扫", "CountryCode", "RoomTest")
    private var clzzs = mutableListOf<Class<*>>(CoilTestActivity::class.java, CustomScanActivity::class.java, CountryActivity::class.java, RoomTestActy::class.java)

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

//        val loadsir = LoadSir.Builder().addCallback(LoadingCallback()).addCallback(ErrorCallback()).setDefaultCallback(SuccessCallback::class.java).build()

//        loadsirService = loadsir.register(this){
//            Thread {
//                loadsirService.showCallback(LoadingCallback::class.java)
//                SystemClock.sleep(5000)
//            }.start()
//        }
//
//        loadsirService = loadsir.register(this){
//            SystemClock.sleep(5000)
//                loadsirService.showCallback(LoadingCallback::class.java)
//        }

//        loadsirService = LoadServiceInit(rcl_menu){
//            loadsirService.showCallback(LoadingCallback::class.java)
//            menus.addAll(menus)
//            mAdapter.setDatas(menus)
//        }

        WebUtils.loadUrl(web, "https://wy.kcloudchina.com/app-h5/yzapp/#/userService")
        Tools.showWebTip(this, "用户协议", "https://wy.kcloudchina.com/app-h5/yzapp/#/userService", "我已阅读并同意")

        TitleLayout.title?.text = "测试"
        TitleLayout.rightText?.text = "三"
        LiveDataManager.instance?.with<String>(Keys.SCAN_CONTENT)
            ?.observer(this, Observer {
                it?.let {
                    if (it.isNotEmpty()) showTip("扫描的内容：${it}")
                }
                println("${CacheUtil.gString(Keys.SCAN_CONTENT)} 扫描的内容 $mScanContent")
            })
        LiveDataManager.instance?.with<CountryData>(Keys.COUNTRY_CODE)
            ?.observer(this, Observer {
                var name =
                    if (SharedPreUtils.en == AppContext.mSpUtils[SharedPreUtils.KEY_APP_LANGUAGE, SharedPreUtils.cn])
                        it?.shortNameEn else it?.shortName

                showTip("${CacheUtil.gObj(Keys.COUNTRY_CODE, CountryData::class.java)?.name} 选择的内容：${it.code}----$name")
            })
        initAdapter()

        initClick()
    }

    private fun initClick() {

        TitleLayout.rightText?.setOnClickListener {

            val messageDialog = MessageDialog.build(this).setTitle("提示").setMessage("选择显示模式").setOkButton("夜间模式").setCancelButton("日间模式").setOtherButton("取消")
                .setTheme(if (lightMode) DialogSettings.THEME.DARK else DialogSettings.THEME.LIGHT)
            messageDialog.show()
//                MessageDialog.show(this, "提示", "选择显示模式", "夜间模式", "日间模式", "取消")
            messageDialog.setButtonOrientation(LinearLayout.VERTICAL).setOnOkButtonClickListener { _, v ->
                setNightMode(true)
                false
            }.setOnCancelButtonClickListener { _, v ->
                setNightMode(false)
                false
            }
        }
        //区域分割 响应区域点击事件
        opv.splitMode = true
        opv.setOnOptionItemClickListener(object : OptionBarView.OnOptionItemClickListener {
            override fun leftOnClick() {
                showTip("leftText")
            }

            override fun centerOnClick() {
                showTip("titleText")
            }

            override fun rightOnClick() {
                showTip("rightText")
            }

        })
        val srl = MeRecyclerView.smartRefreshLayout
        srl?.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                mAdapter?.setDatas(menus)
                ViewUtils.closeRefresh(this)
            }
        }
    }

    private fun initAdapter() {

        mAdapter = object : MeAdapter<String>(R.layout.layout_common_item) {
            override fun convert(holder: SuperRecyclerHolder?, t: String?, layoutType: Int, position: Int) {
                super.convert(holder, t, layoutType, position)
                holder?.apply {
                    val opv: OptionBarView = holder?.getViewById(R.id.opv_item) as OptionBarView
                    t?.let {
                        opv.setLeftText("left")
                        opv.setTitleText(it)
                        opv.setRightText("right")
                        opv.setLeftImage(BitmapUtils.getResBitmap(this@MainActivity, R.mipmap.ic_common_empty))
                    }
                    setOnItemClickListenner {
                        if (menus.size == clzzs.size) {
                            if (position == 1 || t == "扫一扫")
                                customScan(this@MainActivity)
                            else
                                newInstance(this@MainActivity, clzzs[position])
                        } else
                            showLoading("请保证菜单和页面个数对应！")
                    }
                }
            }
        }

        MeRecyclerView.swipeRecyclerView?.apply {

            setSwipeMenuCreator(mAdapter.mSwipeMenuCreator)
//            setOnItemMenuClickListener(mAdapter.mItemMenuClickListener)
            setOnItemMenuClickListener { menuBridge, position ->
                menuBridge.closeMenu()
                if (menus.size > 0) {
                    menus.removeAt(position)
                    println("${menus.size}======${menus}")
                    mAdapter.setDatas(menus)
                } else {
                    Tools.showTip("请至少保留一项")
                }
            }
            layoutManager = FlexboxLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }
}