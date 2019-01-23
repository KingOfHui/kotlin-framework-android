package com.hualala.base.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.hualala.base.R
import com.hualala.base.ext.onClick
import kotlinx.android.synthetic.main.layout_header_bar_right_icon.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/*
    Header Bar封装
 */
class HeaderBarRightIcon @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    //是否显示"返回"图标
    private var isShowBack = false

    //Title文字
    private var titleText: String? = null
    //右侧文字
    private var rightText: String? = null

    init {
        //获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)

        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, false)

        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)

        initView()
        typedArray.recycle()
    }

    /*
        初始化视图
     */
    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar_right_icon, this)

        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE

        //标题不为空，设置值
        titleText?.let {
            mTitleTv.text = it
        }


        //返回图标默认实现（关闭Activity）
        mLeftIv.onClick {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }

    }

    fun setTitleText(title: String){
        mTitleTv.text = title
    }


    fun setTitleText(resId: Int){
        mTitleTv.text = context.getText(resId)
    }

    fun setNewCount(count: Int){
        if (count == 0){
            mNewCounterTv.visibility = View.INVISIBLE
        } else {
            mNewCounterTv.visibility = View.VISIBLE
            mNewCounterTv.text = count.toString()
        }
    }

    fun getNewsLayout(): RelativeLayout{
        return mNewsFl
    }

    fun setShowBack(show: Boolean){
        doAsync {
            uiThread {
                mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE
            }
        }
    }

    /*
        获取左侧视图
     */
    fun getLeftView(): ImageView {
        return mLeftIv
    }


}
