package com.hualala.base.rx

import com.alibaba.android.arouter.launcher.ARouter
import com.hualala.base.common.AppManager
import com.hualala.base.common.ResultCode
import com.hualala.base.presenter.view.BaseView
import io.reactivex.observers.DisposableObserver

/*
    Rx订阅者默认实现
*/
open class BaseDisposableObserver<T>(val baseView: BaseView) : DisposableObserver<T>() {

    override fun onNext(t: T) {
    }

    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onError(t: Throwable) {
        baseView.hideLoading()
        if (t is BaseException) {
            baseView.onError(t.msg)
            if (ResultCode.ACCESSTOKEN_EXPIRE.equals(t.code)){
                AppManager.instance.finishAllActivity()
                ARouter.getInstance().build("/hualalapay_user/login").navigation()
            }
        } else {
            t.message?.let {
                baseView.onError(it)
            }
        }
    }
}