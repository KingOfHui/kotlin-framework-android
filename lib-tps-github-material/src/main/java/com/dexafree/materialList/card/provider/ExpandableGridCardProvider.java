package com.dexafree.materialList.card.provider;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.dexafree.materialList.R;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.view.ExpandableHeightGridView;

public class ExpandableGridCardProvider extends CardProvider<ExpandableGridCardProvider> {
    private AdapterView.OnItemClickListener mOnItemSelectedListener;
    private ListAdapter mAdapter;
    private int mEmptyViewResId = 0;

    private int mNumColumn = 2;

    private int mHorizontalSpacing = 1;

    private int mVerticalSpacing = 1;


    private ExpandableHeightGridView mExpandableHeightGridView;
    private final View.OnTouchListener mTouchListener = new MyTouchListener();

    private static class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false; // That the gesture detection is correct
        }
    }

    @Override
    protected void onCreated() {
        super.onCreated();
        setLayout(getLayout());
    }

    /**
     * Set the adapter of the list.
     *
     * @param adapter of the list.
     * @return the renderer.
     */
    public ExpandableGridCardProvider setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        notifyDataSetChanged();
        return this;
    }

    /**
     * Get the adapter of the list.
     *
     * @return the adapter.
     */
    public ListAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * Get the listener for on item click events.
     *
     * @return the listener.
     */
    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return mOnItemSelectedListener;
    }

    /**
     * Set the listener for on item click events.
     *
     * @param listener to set.
     * @return the renderer.
     */
    public ExpandableGridCardProvider setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mOnItemSelectedListener = listener;
        return this;
    }

    public void setEmptyViewResId(int resId) {
        mEmptyViewResId = resId;
    }

    public int getEmptyViewResId() {
        return mEmptyViewResId;
    }

    @Override
    public void render(@NonNull final View view, @NonNull final Card card) {
        super.render(view, card);
        if (getAdapter() != null) {
            mExpandableHeightGridView = (ExpandableHeightGridView) view.findViewById(R.id.expandgridview);

            final View emptyView = mEmptyViewResId > 0 ? view.findViewById(getEmptyViewResId()) : null;
            if (emptyView != null) {
                mExpandableHeightGridView.setEmptyView(emptyView);
            }
            mExpandableHeightGridView.setExpanded(true);
            mExpandableHeightGridView.setScrollbarFadingEnabled(true);
            mExpandableHeightGridView.setOnTouchListener(mTouchListener);
            mExpandableHeightGridView.setNumColumns(getNumColumn());
            mExpandableHeightGridView.setHorizontalSpacing(getHorizontalSpacing());
            mExpandableHeightGridView.setVerticalSpacing(getVerticalSpacing());
            mExpandableHeightGridView.setAdapter(getAdapter());
            if (getOnItemClickListener() != null) {
                mExpandableHeightGridView.setOnItemClickListener(getOnItemClickListener());
            }
        }
    }


    public ExpandableGridCardProvider setNumColumn(int num) {
        mNumColumn = num;
        return this;
    }

    public int getNumColumn() {
        return mNumColumn;
    }

    public ExpandableGridCardProvider setHorizontalSpacing(int spacing) {
        mHorizontalSpacing = spacing;
        return this;
    }

    public int getHorizontalSpacing() {
        return mHorizontalSpacing;
    }

    public ExpandableGridCardProvider setVerticalSpacing(int spacing) {
        mVerticalSpacing = spacing;
        return this;
    }

    public int getVerticalSpacing() {
        return mVerticalSpacing;
    }

}
