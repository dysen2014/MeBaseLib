package com.dysen.baselib.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.dysen.baselib.R
import com.dysen.baselib.base.AppContext
import com.dysen.baselib.common.base_recycler_adapter.CommonAdapte
import com.dysen.baselib.widgets.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.kongzue.dialog.util.DialogSettings
import com.kongzue.dialog.v3.CustomDialog
import com.kongzue.dialog.v3.TipDialog
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Tools {
    var app: Context = AppContext.appContext!!
    lateinit var toast: Toast

    fun printStackTrace(tag: String?, e: Throwable) {
        Log.e(tag, e.message, e)
    }

    fun getTime(date: Date): String { //可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.time)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }

    fun getTime(date: Date, pattern: String = "yyyy-MM-dd HH:mm:ss"): String { //可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.time)
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    fun getDate(date: String): Date { //可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: $date")
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.parse(date)
    }

    fun getDate(date: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): Date { //可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: $date")
        val format = SimpleDateFormat(pattern)
        return format.parse(date)
    }

    /**
     * 防止控件被重复点击
     *
     * @param distance 间隔 默认300毫秒 test
     * @return
     */
    private var lastClickTime: Long = 0
    fun isFastDoubleClick(distance: Long): Boolean {
        val time = System.currentTimeMillis()
        val timeD: Long = time - lastClickTime
        if (timeD in 1 until distance) {
            return true
        }
        lastClickTime = time
        return false
    }

    /**
     * 获取非空的text，null或者empty时候可以设置默认值
     * 对content, defaultContent都进行判空操作
     */
    fun obtainNoNullText(content: String?, @NonNull defaultContent: String?): String? {
        return if (!TextUtils.isEmpty(content)) content else if (content == null) "--" else if (!TextUtils.isEmpty(
                defaultContent
            )
        ) defaultContent else ""
    }

    fun toast(): Toast {
        return Toast(app)
    }

    fun toast(content: String): Toast {
        var toast = toast()
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.setText(content)
        toast.show()
        return toast
    }

    /**
     * 1、LinearLayoutManager:线性布局管理器，支持水平和垂直效果。
     */
    @JvmStatic
    fun setManager1(
        context: Context?,
        orientation: Int
    ): RecyclerView.LayoutManager {
        val manager = LinearLayoutManager(context)
        manager.orientation = orientation
        return manager
    }

    /**
     * 　2、GridLayoutManager:网格布局管理器，支持水平和垂直效果。
     */
    fun setManager2(context: Context?, spanCount: Int): RecyclerView.LayoutManager {
        return GridLayoutManager(context, spanCount)
    }

    /**
     * 　3、StaggeredGridLayoutManager:分布型管理器，瀑布流效果
     */
    fun setManager3(spanCount: Int, orientation: Int): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(spanCount, orientation)
    }

    fun setIsEnable(view: View?) {
        setIsEnable(view, false)
    }

    fun setIsEnable(view: View?, falg: Boolean) {
        view?.isEnabled = falg
    }

    fun setGone(view: View?) {
        view?.visibility = View.GONE
    }

    fun setVisible(view: View?) {
        view?.visibility = View.VISIBLE
    }

    fun setIsVisible(view: View?, flag: Boolean) {
        view?.visibility = if (flag) View.VISIBLE else View.GONE
    }

    fun setInvisible(view: View?) {
        view?.visibility = View.INVISIBLE
    }


    fun showConfirmDialog2(
        context: AppCompatActivity,
        mOnYesClickListener: View.OnClickListener?,
        mOnNoClickListener: View.OnClickListener?
    ): ConfirmDialog2 {
        val mCommonDialog = ConfirmDialog2(context)
        if (mOnYesClickListener != null) {
            mCommonDialog.setYesOnClickListener(mOnYesClickListener)
        }
        if (mOnNoClickListener != null) {
            mCommonDialog.setNoOnClickListener(mOnNoClickListener)
        } else mCommonDialog.setBtnNoGone()
        return mCommonDialog.show()
    }

    fun showConfirmDialog2(
        context: AppCompatActivity,
        mOnYesClickListener: View.OnClickListener
    ): ConfirmDialog2? {
        return showConfirmDialog2(context, mOnYesClickListener, null)
    }


    fun showConfirmDialog2(context: AppCompatActivity): ConfirmDialog2? {
        val mCommonDialog: ConfirmDialog2 = showConfirmDialog2(context, null, null)
        mCommonDialog.setBtnNoGone()
        return mCommonDialog
    }

    /**
     * 信息确认框
     *
     * @param context
     * @param msg
     * @param mOnYesClickListener
     * @param mOnNoClickListener
     */
    fun showConfirmDialog(
        context: AppCompatActivity,
        msg: String?,
        mOnYesClickListener: View.OnClickListener?,
        mOnNoClickListener: View.OnClickListener?
    ): ConfirmDialog {
        val mConfirmDialog = ConfirmDialog(context)
        mConfirmDialog.contentView?.text = msg
        if (mOnYesClickListener != null) {
            mConfirmDialog.setYesOnClickListener(mOnYesClickListener)
        }
        if (mOnNoClickListener != null) {
            mConfirmDialog.setNoOnClickListener(mOnNoClickListener)
        } else mConfirmDialog.setBtnNoGone()
        return mConfirmDialog.show()
    }

    fun showConfirmDialogs(
        context: AppCompatActivity,
        msg: String?,
        m1OnClickListener: View.OnClickListener?,
        m2OnClickListener: View.OnClickListener?
    ): ConfirmDialog {
        val mConfirmDialog = ConfirmDialog(context)
        mConfirmDialog.contentView?.text = msg
        if (m1OnClickListener != null) {
            mConfirmDialog.set1OnClickListener(m1OnClickListener)
        }
        if (m2OnClickListener != null) {
            mConfirmDialog.set2OnClickListener(m2OnClickListener)
        } else mConfirmDialog.setBtnNoGone()
        return mConfirmDialog.show()
    }

    fun showConfirmDialogs(
        context: AppCompatActivity,
        msg: String?,
        m1OnClickListener: View.OnClickListener?
    ): ConfirmDialog {
        return showConfirmDialogs(context, msg, m1OnClickListener, null)
    }

    fun showConfirmDialogs(context: AppCompatActivity, msg: String?): ConfirmDialog {
        return showConfirmDialogs(context, msg, null, null)
    }

    /**
     * 信息确认框
     *
     * @param context
     * @param msg
     */
    fun showConfirmDialog(
        context: AppCompatActivity,
        msg: String?,
        mOnYesClickListener: View.OnClickListener?
    ): ConfirmDialog? {
        return showConfirmDialog(context, msg, mOnYesClickListener, null)
    }

    /**
     * 信息确认框
     *
     * @param context
     * @param msg
     */
    fun showConfirmDialog(context:AppCompatActivity, msg: String?): ConfirmDialog? {
        val mConfirmDialog: ConfirmDialog = showConfirmDialog(context, msg, null, null)
        mConfirmDialog.setBtnNoGone()
        return mConfirmDialog
    }

    /**
     * 成生二维码
     *
     * @param str
     * @param width
     * @param height
     * @return
     */
    fun createQRImage(str: String?, width: Int, height: Int): Bitmap? {
        try {
            if (TextUtils.isEmpty(str)) {
                return null
            }
            val hints: Hashtable<EncodeHintType, String> = Hashtable<EncodeHintType, String>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            val bitMatrix: BitMatrix =
                QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height, hints)
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = -0x1000000
                    } else {
                        pixels[y * width + x] = -0x1
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        } catch (e: Throwable) {
            printStackTrace("", e)
        }
        return null
    }

    fun gText(textView: TextView): String {
        return textView.text.toString()
    }

    fun gText(editText: EditText): String {
        return editText.text.toString()
    }

    fun isNull(obj: Any?): Boolean {
        return obj == null
    }

    fun isNotNull(obj: Any?): Boolean {
        return !isNull(obj)
    }

    fun isEmpty(str: String?): Boolean {
        return TextUtils.isEmpty(str)
    }

    fun showTip(str: String) {
        ToastUtils.showLong(str)
//        if (toast == null)
//            toast = Toast.makeText(app, str, Toast.LENGTH_LONG)
//
//        toast.setGravity(Gravity.CENTER,0, 0)
//        toast.show()
    }

    fun showTipInput(str: String) {
        showTip("请输入${str}内容")
    }

    fun showTipSelect(str: String) {
        showTip("请选择${str}")
    }

    fun gColor(colorId: Int): Int {
        return try {
            app.resources.getColor(colorId)
        } catch (e: Throwable) {
            printStackTrace("dysen", e)
            Color.BLACK
        }
    }

    fun gString(id: Int): String? {
        return try {
            app.resources.getString(id)
        } catch (e: java.lang.Exception) {
            printStackTrace("dysen", e)
            ""
        }
    }

    fun gString(id: Int, formatArgs: Any): String? {
        return try {
            app.resources.getString(id, formatArgs)
        } catch (e: java.lang.Exception) {
            printStackTrace("dysen", e)
            ""
        }
    }

    /**
     * 把布局转换成View
     * @param activity
     * @param layoutId
     * @return
     */
    fun getView(activity: Activity, layoutId: Int): View? {
        return activity.layoutInflater.inflate(layoutId, null)
    }


    /**
     * 得到去除重复后的集合
     * @param list
     * @return
     */
    private fun <T> getRemoveList(list: List<T>): List<T>? {
        val set: MutableSet<*> = HashSet<Any?>()
        val newList: MutableList<T> = ArrayList()
        val iter: Iterator<*> = list.iterator()
        while (iter.hasNext()) {
            val obj = iter.next() as T
            if (set.add(obj as Nothing)) newList.add(obj)
        }
        return newList
    }


    fun SpUtils(): SharedPreUtils {
        return SharedPreUtils.getInstance(getAppContext())!!
    }

    //保留两位小数
    fun keep2DecimalPlaces(data: Any): String {
        val df = DecimalFormat("######0.00")
        return df.format(data)
    }

    /**
     * 获得当天时间
     *
     * @param date
     * @param iDaydiff
     * @return
     */
    fun getDay(date: Date, iDaydiff: Int): Date {
        var date = date
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        calendar.add(Calendar.DATE, iDaydiff) //把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.time //这个时间就是日期往后推一天的结果
        return date
    }

    //获取当月
    fun getMonth(date: Date, iDaydiff: Int): Date {
        var date = date
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        calendar.add(Calendar.MONTH, iDaydiff) //把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.time //这个时间就是日期往后推一天的结果
        return date
    }

    //月初
    fun getMonthStart(date: Date): Date {
        return getDay(date, 1 - getDayOfMonth())
    }

    //月末provide-app
    fun getMonthEnd(date: Date): Date {
        return getDay(getMonth(date, 1), -1)
    }


    /**
     * 获取当月几号（第几天）
     */
    fun getDayOfMonth(): Int {
        val mCalendar = Calendar.getInstance()
        return mCalendar[Calendar.DAY_OF_MONTH]
    }


    fun getAppContext(): Application? {
        return AppContext.app
    }

    fun getColor(colorId: Int): Int {
        return try {
            app.resources.getColor(colorId)
        } catch (e: Exception) {
            printStackTrace("Tools", e)
            Color.BLACK
        }
    }

    fun getString(id: Int): String {
        return try {
            app.resources.getString(id)
        } catch (e: Exception) {
            printStackTrace("Tools", e)
            ""
        }
    }

    fun getStringArray(id: Int): Array<String> {
        return try {
            app.resources.getStringArray(id)
        } catch (e: Exception) {
            printStackTrace("Tools", e)
            arrayOf()
        }
    }

    fun getString(id: Int, vararg formatArgs: Any?): String {
        return try {
            app.resources.getString(id, *formatArgs)
        } catch (e: Exception) {
            printStackTrace("Tools", e)
            ""
        }
    }

    val screenWidth: Int
        get() = app.resources.displayMetrics.widthPixels

    val screenHeight: Int
        get() = app.resources.displayMetrics.heightPixels

    fun dp2px(dpValue: Float): Int {
        val scale = app.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(pxValue: Float): Int {
        val scale = app.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val scale = app.resources.displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }


    /**
     * 字符串分割 默认以空格分割
     */
    fun splitStr(s: String?, split: String = " "): MutableList<String> {
        var splits = s?.split(split) ?: mutableListOf()
        return splits as MutableList<String>
    }

    /**
     * 获取日期
     */
    fun subDate(s: String?, split: String = " "): String {
        var splits = splitStr(s, split)
        return if (splits.size > 0) splits[0] else ""
    }

    /**
     * 获取时间
     */
    fun subTime(s: String?, split: String = " ", isSub: Boolean = false): String {
        var splits = splitStr(s, split)
        return if (splits.size > 1) {
            if (splits[1].length > 4 && isSub) splits[1].substring(0, 5) else splits[1]
        } else ""
    }

    /**
     * 加载Tab badge统计
     */
//    fun loadBadgeCount(mCounts: MutableList<Int>, mTabBadges: MutableList<KDTabEndRelativeBadge>) {
//        if (mTabBadges.size == mCounts.size)
//            mCounts?.forEachIndexed { index, it ->
//                mTabBadges[index]?.count = if (it > 99) "···" else "$it"
//                mTabBadges[index]?.apply {
//                    if (it > 0) show() else dismiss()
//                    showCount = it > 0
//                }
//            }
//    }

    fun confirmDialog(
        aty: AppCompatActivity,
        title: String,
        btnName: String = "好的",
        isFinish: Boolean = true
    ) {
        CustomDialog.show(aty, R.layout.layout_custom_dialog) { dialog, v ->
            val tvSubTitle = v?.findViewById<TextView>(R.id.tv_subTitle)
            val btnOk = v?.findViewById<Button>(R.id.btn_ok)
            tvSubTitle?.text = title
            btnOk?.text = btnName
            btnOk?.setOnClickListener {
                if (isFinish)
                    aty.finish()
                dialog?.doDismiss()
            }

        }
    }

    fun showTipDialog(
        aty: AppCompatActivity,
        s: String,
        icoResId: Int = R.mipmap.icon_submit_success,
        duration: Int = Toast.LENGTH_LONG
    ) {
        val SHORT_DURATION_TIMEOUT: Int = 4000
        val LONG_DURATION_TIMEOUT: Int = 7000
        val mDuration =
            if (duration == Toast.LENGTH_LONG) LONG_DURATION_TIMEOUT else SHORT_DURATION_TIMEOUT
        TipDialog.show(aty, s, icoResId).setTipTime(mDuration).theme = DialogSettings.THEME.LIGHT
    }

    fun showTipDialog(
        aty: AppCompatActivity,
        s: String,
        type: TipDialog.TYPE = TipDialog.TYPE.WARNING,
        duration: Int = Toast.LENGTH_LONG
    ) {
        val SHORT_DURATION_TIMEOUT: Int = 4000
        val LONG_DURATION_TIMEOUT: Int = 7000
        val mDuration =
            if (duration == Toast.LENGTH_LONG) LONG_DURATION_TIMEOUT else SHORT_DURATION_TIMEOUT
        TipDialog.show(aty, s, type).setTipTime(mDuration).theme = DialogSettings.THEME.LIGHT
    }

    /**
     * 设置RadioGroup 内容是否可点击；
     * @param isEnabled
     * @param radioGroup
     */
    fun setRadioGroupEnable(radioGroup: RadioGroup, isEnabled: Boolean = false) {
//		 设置RadioGroup 全部取消选中
        radioGroup.clearCheck()
        for (i in 0 until radioGroup.childCount) {
            radioGroup.getChildAt(i)?.let {
                val btn = it as RadioButton
                btn.isChecked = isEnabled
            }
        }
    }

    open fun<T> layoutResId(mValueList: MutableList<T>, layoutResId: Int):Int{
        return if (mValueList.isEmpty())R.layout.layout_common_empty else layoutResId
    }



    open fun<T> refreshNoList(mValueList: MutableList<T>, adapter: CommonAdapte<T>?){
            setIsVisible(MeRecyclerViewNoRefresh.emptyLayout, mValueList.isEmpty())
            setIsVisible(MeRecyclerViewNoRefresh.swipeRecyclerView, mValueList.isNotEmpty())
        EmptyLayout.refresh?.setOnClickListener {
            adapter?.setDatas(mValueList)
        }
    }

    fun showWebTip(aty: AppCompatActivity, title: String, url: String, btnName: String = "好的") {
        showWebTip(aty, title, url, btnName, null)
    }

    fun showWebTip(aty: AppCompatActivity, title: String, url: String, btnName: String, rbnReadAndAgree: AppCompatRadioButton?) {
        val dialog = CustomDialog.show(aty, R.layout.layout_common_webview) { dialog, v ->
            val web = v?.findViewById(R.id.web) as WebView
            val tvSubTitle = v?.findViewById<TextView>(R.id.tv_subTitle)
            val btnOk = v?.findViewById<Button>(R.id.btn_ok)
            tvSubTitle?.text = title
            WebUtils.loadUrl(web, url)
            btnOk?.text = btnName
            btnOk?.setOnClickListener {
                rbnReadAndAgree?.isChecked = true
                dialog?.doDismiss()
            }
        }
        dialog.isFullScreen = true
        dialog.cancelable = false
    }

}