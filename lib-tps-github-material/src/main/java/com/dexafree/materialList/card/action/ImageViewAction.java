package com.dexafree.materialList.card.action;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dexafree.materialList.card.Action;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.OnActionClickListener;

public class ImageViewAction extends Action {
    @Nullable
    private OnActionClickListener mListener;

    public ImageViewAction(@NonNull Context context) {
        super(context);
    }

    @Nullable
    public OnActionClickListener getListener() {
        return mListener;
    }

    public ImageViewAction setListener(@Nullable final OnActionClickListener listener) {
        this.mListener = listener;
        notifyActionChanged();
        return this;
    }

    @Override
    protected void onRender(@NonNull final View view, @NonNull final Card card) {
        ImageView imageView = (ImageView) view;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.onActionClicked(view, card);
                }
            }
        });
    }
}
