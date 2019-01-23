package com.hualala.base.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hualala.base.R
import org.jetbrains.anko.find

/*
    加载对话框封装
 */
class ProgressLoading private constructor(context: Context, theme: Int) : Dialog(context, theme) {

    companion object {
        private lateinit var mDialog: ProgressLoading
        private var animDrawable: AnimationDrawable? = null
        private var stateTextView: TextView? = null

        /*
            创建加载对话框
         */
        fun create(context: Context): ProgressLoading {
            //样式引入
            mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            //设置布局
            mDialog.setContentView(R.layout.progress_dialog)
            mDialog.setCancelable(true)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.window.attributes.gravity = Gravity.CENTER

            val lp = mDialog.window.attributes
            //设置灰暗程度
            lp.dimAmount = 0.2f
            //设置属性
            mDialog.window.attributes = lp

            //获取动画视图
            val loadingView = mDialog.find<ImageView>(R.id.iv_loading)
            animDrawable = loadingView.background as AnimationDrawable

            stateTextView = mDialog.find<TextView>(R.id.iv_state)

            return mDialog
        }
    }

    /*
        显示加载对话框，动画开始
     */
    fun showLoading(state: String) {
        super.show()
        if (state.isNullOrEmpty()){
            stateTextView?.visibility = View.GONE
        } else {
            stateTextView?.visibility = View.VISIBLE
            stateTextView?.text = state
        }
        animDrawable?.start()
    }

    /*
        隐藏加载对话框，动画停止
     */
    fun hideLoading() {
        super.dismiss()
        animDrawable?.stop()
    }
}
