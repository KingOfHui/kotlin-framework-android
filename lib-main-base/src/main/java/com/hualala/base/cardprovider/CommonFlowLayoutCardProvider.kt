package com.hualala.base.cardprovider

import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.dexafree.materialList.card.Card
import com.dexafree.materialList.card.CardProvider
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.card_common_flowlayout.view.*


/**
 * segmentinglineHeight: 间距
 * selectMaxCount: -1为不限制选择数量，>=1的数字为控制选择tag的数量
 * tagAdapter: 标签
 */
class CommonFlowLayoutCardProvider<T> (
        val segmentinglineHeight: Float = 0F,
        val selectMaxCount: Int = 1,
        val tagAdapter: TagAdapter<T>
) : CardProvider<CommonFlowLayoutCardProvider<T>>() {

    //点击标签事件
    var onTagClickListener: TagFlowLayout.OnTagClickListener? = null
    //点击标签事件回调
    var onSelectListener: TagFlowLayout.OnSelectListener? = null

    override fun render(view: View, card: Card) {
        super.render(view, card)

        view.mSegmentingLineTv?.let {
            if (segmentinglineHeight <= 0){
                view.mSegmentingLineTv.visibility = View.GONE
            } else {
                view.mSegmentingLineTv.visibility = View.VISIBLE
                val params = view.mSegmentingLineTv.layoutParams as LinearLayout.LayoutParams
                params.height =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, segmentinglineHeight, context.resources.displayMetrics).toInt()
                params.width = LinearLayout.LayoutParams.MATCH_PARENT
                view.mSegmentingLineTv.layoutParams = params
            }
        }

        view.mTagFlowLayout?.let {

            it.setMaxSelectCount(selectMaxCount)

            it.adapter = tagAdapter

            if (onTagClickListener != null){
                it.setOnTagClickListener(onTagClickListener)
            }

            if (onSelectListener != null){
                it.setOnSelectListener(onSelectListener)
            }

        }

    }

    fun setOnTagClickListener(listener: TagFlowLayout.OnTagClickListener): CommonFlowLayoutCardProvider<T>{
        onTagClickListener = listener
        return this
    }

    fun setOnSelectListener(listener: TagFlowLayout.OnSelectListener): CommonFlowLayoutCardProvider<T>{
        onSelectListener = listener
        return this
    }

}