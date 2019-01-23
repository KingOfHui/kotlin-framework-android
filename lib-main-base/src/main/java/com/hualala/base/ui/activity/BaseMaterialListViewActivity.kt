package com.hualala.base.ui.activity

import android.os.Bundle
import com.dexafree.materialList.card.Card
import com.hualala.base.R
import com.hualala.base.presenter.BasePresenter
import com.hualala.base.presenter.view.BaseView
import kotlinx.android.synthetic.main.fragment_base_material.*

abstract class BaseMaterialListViewActivity<T : BasePresenter<*>> : BaseMvpActivity<T>(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_material)
    }

    protected fun addCard(card: Card){
        mMaterialListView.adapter.add(card)
    }

    protected fun addCards(cards: MutableList<Card>){
        mMaterialListView.adapter.addAll(cards)
    }

}