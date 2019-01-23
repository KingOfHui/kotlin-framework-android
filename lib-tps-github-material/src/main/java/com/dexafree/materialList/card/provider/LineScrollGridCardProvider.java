package com.dexafree.materialList.card.provider;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.dexafree.materialList.QualityPolicy;
import com.dexafree.materialList.R;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.view.LineScrollGridView;

public class LineScrollGridCardProvider extends CardProvider<LineScrollGridCardProvider> {
    private AdapterView.OnItemClickListener mOnItemSelectedListener;
    private ListAdapter mAdapter;
    private int mEmptyViewResId = 0;

    private int mNumColumn = 2;

    private int mHorizontalSpacing = 1;

    private int mVerticalSpacing = 1;

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
    public LineScrollGridCardProvider setAdapter(ListAdapter adapter) {
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
    public LineScrollGridCardProvider setOnItemClickListener(AdapterView.OnItemClickListener listener) {
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
            final LineScrollGridView gridView = (LineScrollGridView) view.findViewById(R.id.gridview);

            final View emptyView = mEmptyViewResId > 0 ? view.findViewById(getEmptyViewResId()) : null;
            if (emptyView != null) {
                gridView.setEmptyView(emptyView);
            }

            gridView.setScrollbarFadingEnabled(true);
            gridView.setOnTouchListener(mTouchListener);
            gridView.setNumColumns(getNumColumn());
            gridView.setHorizontalSpacing(getHorizontalSpacing());
            gridView.setVerticalSpacing(getVerticalSpacing());
            gridView.setAdapter(getAdapter());
            gridView.getAdapter().registerDataSetObserver(new GridViewDataSetObserver(gridView));

            if (getOnItemClickListener() != null) {
                gridView.setOnItemClickListener(getOnItemClickListener());
            }
            calculateListHeight(gridView);
        }
    }

    private class GridViewDataSetObserver extends DataSetObserver {
        private final GridView gridView;

        GridViewDataSetObserver(GridView gridView) {
            this.gridView = gridView;
        }

        @Override
        public void onChanged() {
            super.onChanged();
            calculateListHeight(gridView);
        }
    }

    private void calculateListHeight(GridView listView) {
        final ListAdapter adapter = listView.getAdapter();

        // Calculate the height of the ListView to display all items
        int column = getNumColumn();
        int size = adapter.getCount();

        int rowCount;
        if (size % column == 0) {
            rowCount = size / column;
        } else {
            rowCount = (size + column) / column;
        }

//        int rowCount = (size + size + column) / (column + column);
        int totalHeight = 0;
        if (rowCount > 0) {
            QualityPolicy.checkLayoutRootItem(adapter.getView(0, null, listView));
        }
        for (int i = 0; i < rowCount; i++) {
            View item = adapter.getView(i, null, listView);
            item.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (totalHeight > 0) {
            params.height = totalHeight;
        }

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public LineScrollGridCardProvider setNumColumn(int num) {
        mNumColumn = num;
        return this;
    }

    public int getNumColumn() {
        return mNumColumn;
    }

    public LineScrollGridCardProvider setHorizontalSpacing(int spacing) {
        mHorizontalSpacing = spacing;
        return this;
    }

    public int getHorizontalSpacing() {
        return mHorizontalSpacing;
    }

    public LineScrollGridCardProvider setVerticalSpacing(int spacing) {
        mVerticalSpacing = spacing;
        return this;
    }

    public int getVerticalSpacing() {
        return mVerticalSpacing;
    }

}
