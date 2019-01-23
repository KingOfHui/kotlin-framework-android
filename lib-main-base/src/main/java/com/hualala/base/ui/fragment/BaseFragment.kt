package com.hualala.base.ui.fragment

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle2.components.support.RxFragment
import com.umeng.analytics.MobclickAgent


/*
    Fragment基类，业务无关
*/
open class BaseFragment : RxFragment() {

    protected open var TAG:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        TAG = this::class.simpleName
        if (arguments != null) {
            analysisArguments(arguments)
        }
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(activity)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(activity)
    }

    protected open fun analysisArguments(bundle: Bundle?) {
    }

}