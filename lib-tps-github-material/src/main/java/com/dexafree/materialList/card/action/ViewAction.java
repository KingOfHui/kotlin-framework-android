package com.dexafree.materialList.card.action;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dexafree.materialList.card.Action;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.OnActionClickListener;

public class ViewAction extends Action {
    @Nullable
    private OnActionClickListener mListener;


    public ViewAction(@NonNull Context context) {
        super(context);
    }

    @Nullable
    public OnActionClickListener getListener() {
        return mListener;
    }

    public ViewAction setListener(@Nullable final OnActionClickListener listener) {
        this.mListener = listener;
        notifyActionChanged();
        return this;
    }

    @Override
    protected void onRender(@NonNull final View view, @NonNull final Card card) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onActionClicked(view, card);
                }
            }
        });
    }
}
