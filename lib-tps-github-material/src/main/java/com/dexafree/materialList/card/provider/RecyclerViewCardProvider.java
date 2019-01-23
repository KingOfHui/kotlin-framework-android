package com.dexafree.materialList.card.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dexafree.materialList.R;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;

public class RecyclerViewCardProvider extends CardProvider<RecyclerViewCardProvider> {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreated() {
        super.onCreated();
        setLayout(R.layout.material_list_card_recycler);
    }

    /**
     * Set the adapter of the list.
     *
     * @param adapter
     *         of the list.
     * @return the renderer.
     */
    public RecyclerViewCardProvider setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * Get the adapter of the list.
     *
     * @return the adapter.
     */
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void render(@NonNull final View view, @NonNull final Card card) {
        super.render(view, card);
        if (getAdapter() != null) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setScrollbarFadingEnabled(true);
            mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false; // That the gesture detection is correct
                }
            });
            mRecyclerView.setAdapter(getAdapter());
            calculateListHeight(mRecyclerView);
        }
    }

    private void calculateListHeight(RecyclerView recyclerView) {

        // Calculate the height of the ListView to display all items
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            //View item = layoutManager.findViewByPosition(i);
            //item.measure(
            //        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            //        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            //);
            //totalHeight += item.getMeasuredHeight();
            totalHeight += 200;
        }

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = totalHeight;

        recyclerView.setLayoutParams(params);
        recyclerView.requestLayout();
    }

}
