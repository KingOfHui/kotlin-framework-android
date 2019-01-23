package com.hualala.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dexafree.materialList.card.Card
import com.hualala.base.R
import kotlinx.android.synthetic.main.fragment_base_material.*

open class BaseMaterialListViewFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_base_material, container, false)
    }

    protected fun addCard(card: Card){
        mMaterialListView.adapter.add(card)
    }

    protected fun addCards(cards: MutableList<Card>){
        mMaterialListView.adapter.addAll(cards)
    }


}