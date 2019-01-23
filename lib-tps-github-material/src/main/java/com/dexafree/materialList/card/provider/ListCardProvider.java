package com.dexafree.materialList.card.provider;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dexafree.materialList.QualityPolicy;
import com.dexafree.materialList.R;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.view.ExpandableHeightListView;

public class ListCardProvider extends CardProvider<ListCardProvider> {
    private AdapterView.OnItemClickListener mOnItemSelectedListener;
    private ListAdapter mAdapter;
    private int emptyViewResId;

    @Override
    protected void onCreated() {
        super.onCreated();
        setLayout(R.layout.material_list_card_layout);
    }

    /**
     * Set the adapter of the list.
     *
     * @param adapter of the list.
     * @return the renderer.
     */
    public ListCardProvider setAdapter(ListAdapter adapter) {
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
    public ListCardProvider setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mOnItemSelectedListener = listener;
        return this;
    }

    public ListCardProvider setListViewEmptyViewResId(final int emptyViewResId) {
        this.emptyViewResId = emptyViewResId;
        return this;
    }

    @Override
    public void render(@NonNull final View view, @NonNull final Card card) {
        super.render(view, card);
        if (getAdapter() != null) {
            final ListView listView = (ListView) view.findViewById(R.id.listView);
            if (listView instanceof ExpandableHeightListView) {
                ((ExpandableHeightListView) listView).setExpanded(true);
            } else {
                QualityPolicy.checkListViewLayout(listView);
            }
            listView.setScrollbarFadingEnabled(true);
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false; // That the gesture detection is correct
                }
            });
            listView.setAdapter(getAdapter());
            listView.getAdapter().registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    if( !(listView instanceof  ExpandableHeightListView)){
                        calculateListHeight(listView);
                    }
                }
            });
            listView.setOnItemClickListener(getOnItemClickListener());
            setEmptyView(view, listView);
            if( !(listView instanceof  ExpandableHeightListView)){
                calculateListHeight(listView);
            }
        }
    }

    private void calculateListHeight(ListView listView) {
        final ListAdapter adapter = listView.getAdapter();
        if (adapter.getCount() > 0) {
            QualityPolicy.checkLayoutRootItem(adapter.getView(0, null, listView));
        }

        // Calculate the height of the ListView to display all items
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, listView);
            item.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setEmptyView(View rootView, ListView listView) {
        if (emptyViewResId > 0) {
            final View emptyView = findViewById(rootView, emptyViewResId, View.class);
            if (emptyView != null && listView != null) {
                listView.setEmptyView(emptyView);
            }
        }
    }

}