package com.me.baselib.widgets

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dysen.baselib.R

class ConfirmDialog2(private val mContext: Context) : View.OnClickListener {
    val dialog: Dialog?
    private val mContentView: View
    var titleView: TextView? = null
        private set
    var subTitleView: TextView? = null
        private set
    var tvTipView: TextView? = null
        private set
    var recyclerviewView: RecyclerView? = null
        private set
    var btnNo: Button? = null
        private set
    var btnYes: Button? = null
        private set
    private var btnNoOnClickListener: View.OnClickListener? = null
    private var btnYesOnClickListener: View.OnClickListener? = null
    var isFalgOutside = true
    fun setOutside(falgOutside: Boolean) {
        dialog!!.setCanceledOnTouchOutside(falgOutside)
    }

    private fun initDialog() {
        dialog!!.setContentView(mContentView)
        dialog.setCanceledOnTouchOutside(isFalgOutside)
        dialog.window!!.attributes.gravity = Gravity.CENTER
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.2f
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp
    }

    private fun initView() {
        titleView = findView<TextView>(R.id.txt_title)
        subTitleView = findView<TextView>(R.id.tv_subTitle)
        tvTipView = findView<TextView>(R.id.tv_tip)
        recyclerviewView = findView<RecyclerView>(R.id.rcl_contacts)
        btnNo = findView<Button>(R.id.btn_no)
        btnYes = findView<Button>(R.id.btn_yes)
        btnNo!!.setOnClickListener(this)
        btnYes!!.setOnClickListener(this)
    }

    fun getmContentView(): View {
        return mContentView
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

    fun show(): ConfirmDialog2 {
        dialog?.show()
        return this
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    private fun <T : View?> findView(id: Int): T {
        return mContentView.findViewById(id)
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_no) {
            btnNoOnClickListener?.onClick(v)
            dismiss()
        } else if (id == R.id.btn_yes) {
            btnYesOnClickListener?.onClick(v)
            dismiss()
        }
    }

    init {
        dialog = Dialog(mContext, R.style.Custom_Progress)
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm2, null)
        initDialog()
        initView()
    }
}