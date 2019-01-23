package com.hualala.base.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

abstract class BaseListViewAdapter<T>(
        protected var mContext: Context,
        var datas: MutableList<T>,
        private val layoutId: Int
) : BaseAdapter() {
    protected var mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): T {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = ViewHolder[mContext, convertView, parent, layoutId, position]
        convert(holder, getItem(position))
        return holder.convertView
    }

    abstract fun convert(holder: ViewHolder, t: T)

}
