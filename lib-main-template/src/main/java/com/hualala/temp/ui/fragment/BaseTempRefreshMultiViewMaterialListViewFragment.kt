package com.hualala.temp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.dexafree.materialList.card.Card
import com.hualala.base.R
import com.hualala.base.presenter.BasePresenter
import com.hualala.base.presenter.view.BaseView
import com.hualala.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_base_refresh_multi_view_material.*

abstract class BaseTempRefreshMultiViewMaterialListViewFragment<T : BasePresenter<*>>
    : BaseMvpFragment<T>(), BaseView, BGARefreshLayout.BGARefreshLayoutDelegate {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefreshLayout()
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_base_refresh_multi_view_material, container, false)

    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(context, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    protected fun addCard(card: Card){
        mMaterialListView?.let{
            it.adapter.add(card)
        }
    }

    protected fun addCards(cards: MutableList<Card>){
        mMaterialListView?.let{
            it.adapter.addAll(cards)
        }
    }


}