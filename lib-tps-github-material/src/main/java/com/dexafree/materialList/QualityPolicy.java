package com.dexafree.materialList;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dexafree.materialList.view.ExpandableHeightListView;

public class QualityPolicy {
    public static void checkLayoutRootItem(View view) {
        View rootView = view.getRootView();
        if (rootView instanceof RelativeLayout) {
            throw new RuntimeException("Root view of recycler item layout can not be Relative Layout, it will crash in android 4.0-4.4.");
        }
    }

    public static void checkListViewLayout(ListView listView) {
        if (listView instanceof ExpandableHeightListView) {
            ((ExpandableHeightListView) listView).setExpanded(true);
        } else {
            throw new RuntimeException("ListView should instance of ExpandableHeightListView in xml file if using Recycler view, if not it will display wrongly when pull up to refresh .");
        }
    }
}
