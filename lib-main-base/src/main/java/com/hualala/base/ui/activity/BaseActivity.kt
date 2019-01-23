package com.hualala.base.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.hualala.base.common.AppManager
import com.hualala.base.utils.StatusBarUtil
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.umeng.analytics.MobclickAgent
import org.jetbrains.anko.find

/*
    Activity基类，业务无关
*/
open class BaseActivity : RxAppCompatActivity() {

    protected open var TAG:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        ARouter.getInstance().inject(this)
        when {
            StatusBarUtil.MIUISetStatusBarLightMode(window, true) -> //小米MIUI系统
                StatusBarUtil.compat(this)
            StatusBarUtil.FlymeSetStatusBarLightMode(window, true) -> //魅族flyme系统
                StatusBarUtil.compat(this)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                //Android6.0以上系统
                StatusBarUtil.android6_SetStatusBarLightMode(window)
                StatusBarUtil.compat(this)
            }
        }
        TAG = this::class.simpleName
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    //获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }
}