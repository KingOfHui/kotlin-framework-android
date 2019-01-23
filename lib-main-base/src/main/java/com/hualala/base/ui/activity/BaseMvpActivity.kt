package com.hualala.base.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.hualala.base.common.BaseApplication
import com.hualala.base.injection.component.ActivityComponent
import com.hualala.base.injection.component.DaggerActivityComponent
import com.hualala.base.injection.module.ActivityModule
import com.hualala.base.injection.module.LifecycleProviderModule
import com.hualala.base.presenter.BasePresenter
import com.hualala.base.presenter.view.BaseView
import com.hualala.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/*
    Activity基类，业务相关
*/
abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    //Presenter泛型，Dagger注入
    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        //初始加载框
        mLoadingDialog = ProgressLoading.create(this)

        ARouter.getInstance().inject(this)
    }

    /*
        Dagger注册
   */
    protected abstract fun injectComponent()

    /*
        显示加载框，默认实现
    */
    override fun showLoading(state: String) {
        mLoadingDialog.showLoading(state)
    }

    /*
        隐藏加载框，默认实现
    */
    override fun hideLoading() {
         mLoadingDialog.hideLoading()
    }

    /*
        错误信息提示，默认实现
    */
    override fun onError(error: String) {
        toast(error)
    }

    /*
        初始Activity Component
    */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

}