package com.me.baselib.widgets

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.dysen.baselib.R

class ConfirmDialog(mContext: Context) : View.OnClickListener {
    val dialog: Dialog?
    private val mContentView: View
    var titleView: TextView? = null
        private set
    var contentView: TextView? = null
        private set
    var btnNo: Button? = null
        private set
    var btnYes: Button? = null
        private set
    var btn1: Button? = null
        private set
    var btn2: Button? = null
        private set
    var llTitle: LinearLayout? = null
    var llVertical: LinearLayout? = null
    var llHorizontal: LinearLayout? = null
    private var btnNoOnClickListener: View.OnClickListener? = null
    private var btnYesOnClickListener: View.OnClickListener? = null
    private var btn1OnClickListener: View.OnClickListener? = null
    private var btn2OnClickListener: View.OnClickListener? = null
    fun setOutside(falgOutside: Boolean) {
        dialog!!.setCanceledOnTouchOutside(falgOutside)
    }

    private fun initDialog() {
        dialog!!.setContentView(mContentView)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.attributes.gravity = Gravity.CENTER
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.2f
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp
    }

    private fun initView() {
        titleView = findView<TextView>(R.id.txt_title)
        contentView = findView<TextView>(R.id.txt_content)
        btnNo = findView<Button>(R.id.btn_no)
        btnYes = findView<Button>(R.id.btn_yes)
        btn1 = findView<Button>(R.id.btn_1)
        btn2 = findView<Button>(R.id.btn_2)
        llTitle = findView<LinearLayout>(R.id.ll_title)
        llVertical = findView<LinearLayout>(R.id.ll_vertical)
        llHorizontal = findView<LinearLayout>(R.id.ll_horizontal)
        btnNo!!.setOnClickListener(this)
        btnYes!!.setOnClickListener(this)
        btn1!!.setOnClickListener(this)
        btn2!!.setOnClickListener(this)
    }

    fun getllTitleView(): LinearLayout? {
        return llTitle
    }

    fun getllVerticalView(): LinearLayout? {
        return llVertical
    }

    fun getllHorizontalView(): LinearLayout? {
        return llHorizontal
    }

    fun getmContentView(): View {
        return mContentView
    }

    fun setTextIsSelectable(selectable: Boolean) {
        contentView!!.setTextIsSelectable(selectable)
    }

    fun setBtnNoGone() {
        btnNo!!.visibility = View.GONE
    }

    fun setBtnNoVisible() {
        btnNo!!.visibility = View.VISIBLE
    }

    fun setYesOnClickListener(mListener: View.OnClickListener?) {
        btnYesOnClickListener = mListener
    }

    fun setNoOnClickListener(mListener: View.OnClickListener?) {
        btnNoOnClickListener = mListener
    }

    fun set1OnClickListener(mListener: View.OnClickListener?) {
        btn1OnClickListener = mListener
    }

    fun set2OnClickListener(mListener: View.OnClickListener?) {
        btn2OnClickListener = mListener
    }

    fun show(): ConfirmDialog {
        dialog?.show()
        return this
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    fun <T : View?> findView(id: Int): T {
        return mContentView.findViewById(id)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_no -> {
                btnNoOnClickListener?.onClick(v)
                dismiss()
            }
            R.id.btn_yes -> {
                btnYesOnClickListener?.onClick(v)
                dismiss()
            }
            R.id.btn_1 -> {
                btn1OnClickListener?.onClick(v)
                dismiss()
            }
            R.id.btn_2 -> {
                btn2OnClickListener?.onClick(v)
                dismiss()
            }
        }
    }

    init {
        dialog = Dialog(mContext, R.style.Custom_Progress)
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm, null)
        initDialog()
        initView()
    }
}