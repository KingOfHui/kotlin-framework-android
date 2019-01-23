package com.hualala.base.cardprovider

import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.dexafree.materialList.card.Card
import com.dexafree.materialList.card.CardProvider
import kotlinx.android.synthetic.main.card_mine_common.view.*


class CommonCardProvider(
        val logoResId: Int,
        val segmentinglineHeight: Float = 0F,
        val showRight: Boolean = true

) : CardProvider<CommonCardProvider>() {

    private val mChooseContent: String? = null

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

        view.mItemLogoIv?.let {
            if (logoResId == 0){
                view.mItemLogoIv.visibility = View.GONE
            } else {
                view.mItemLogoIv.visibility = View.VISIBLE
                view.mItemLogoIv.setImageResource(logoResId)
            }
        }

        if (card.provider.subtitle.isNullOrEmpty()){
            view.subtitle.visibility = View.GONE
        } else {
            view.subtitle.visibility = View.VISIBLE
        }

        view.mRightIv?.let {
            it.visibility = if (showRight) View.VISIBLE else View.GONE
        }

    }



}