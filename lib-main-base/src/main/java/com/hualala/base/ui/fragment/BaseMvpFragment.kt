package com.hualala.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hualala.base.common.BaseApplication
import com.hualala.base.injection.component.ActivityComponent
import com.hualala.base.injection.component.DaggerActivityComponent
import com.hualala.base.injection.module.ActivityModule
import com.hualala.base.injection.module.LifecycleProviderModule
import com.hualala.base.presenter.BasePresenter
import com.hualala.base.presenter.view.BaseView
import com.hualala.base.widgets.ProgressLoading
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/*
    Fragment基类，业务相关
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    /**
     * 是否可见，用于懒加载
     */
    protected var mVisible: Boolean = false

    /**
     * 是否第一次加载，用于懒加载
     */
    protected var mFirst: Boolean = true

    protected var mRootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            analysisArguments(arguments)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mRootView = initView(inflater, container, savedInstanceState)

        initActivityInjection()
        injectComponent()

        //初始加载框
        mLoadingDialog = ProgressLoading.create(context!!)

        //可见，并且是首次加载
        if (mVisible && mFirst) {
            onFragmentVisibleChange(true)
        }



        return mRootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mVisible = isVisibleToUser
        if (mRootView == null) {
            return
        }
        //可见，并且首次加载时才调用
        onFragmentVisibleChange(mVisible and mFirst)
    }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

    protected abstract fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    /**
     * 当前 Fragment 可见状态发生变化时会回调该方法。
     * 如果当前 Fragment 是第一次加载，等待 onCreateView 后才会回调该方法，
     * 其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected open fun onFragmentVisibleChange(isVisible: Boolean) {
    }

    /*
        初始化Activity级别Component
     */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder().appComponent((activity!!.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(activity!!))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }

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

}
