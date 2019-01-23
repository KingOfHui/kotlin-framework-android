package com.hualala.base.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutViewFactory
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.hualala.base.R
import com.hualala.base.presenter.BasePresenter
import com.hualala.base.presenter.view.BaseView
import kotlinx.android.synthetic.main.fragment_base_recycler.*
import java.util.*

abstract class BaseRecyclerViewFragment<T : BasePresenter<*>> : BaseMvpFragment<T>(), BaseView {

    private val mAdapterList = LinkedList<DelegateAdapter.Adapter<*>>()
    private var mDelegateAdapter: DelegateAdapter? = null

    protected var mVirtualLayoutManager: VirtualLayoutManager? = null

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_base_recycler, container, false)

    protected fun initView() {
        mVirtualLayoutManager = VirtualLayoutManager(context!!)
        mVirtualLayoutManager!!.setLayoutViewFactory(LayoutViewFactory { ImageView(context) })
        mRecyclerView.setLayoutManager(mVirtualLayoutManager)
        if (getItemDecoration() != null)
            mRecyclerView.addItemDecoration(getItemDecoration())
        mDelegateAdapter = DelegateAdapter(mVirtualLayoutManager, true)
        mRecyclerView.setAdapter(mDelegateAdapter)
    }

    protected fun getItemDecoration(): RecyclerView.ItemDecoration {
        return RecyclerViewDivider(context!!, LinearLayoutManager.HORIZONTAL)
    }

    fun addAdapterList(adapters: List<DelegateAdapter.Adapter<*>>) {
        mAdapterList.addAll(adapters)
        mDelegateAdapter?.setAdapters(mAdapterList)
    }

    fun addAdapterList(adapter: DelegateAdapter.Adapter<*>) {
        mAdapterList.add(adapter)
        mDelegateAdapter?.setAdapters(mAdapterList)
    }

}