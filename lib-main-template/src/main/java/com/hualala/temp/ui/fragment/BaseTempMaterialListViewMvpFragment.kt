package com.hualala.temp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dexafree.materialList.card.Card
import com.hualala.base.R
import com.hualala.base.presenter.BasePresenter
import com.hualala.base.presenter.view.BaseView
import com.hualala.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_base_material.*

abstract class BaseTempMaterialListViewMvpFragment<T : BasePresenter<*>> : BaseMvpFragment<T>(), BaseView {

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_base_material, container, false)

    protected fun addCard(card: Card){
        mMaterialListView.adapter.add(card)
    }

    protected fun addCards(cards: MutableList<Card>){
        mMaterialListView.adapter.addAll(cards)
    }

}