package com.hualala.base.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.util.Linkify
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*

/**
 *
 * 以前的做法是这样子的
 * viewHolder = new ViewHolder();
 * convertView = layoutInflater.inflate(R.layout.people_list_item, null, false);
 * viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
 * viewHolder.name = (TextView)convertView.findViewById(R.id.name);
 * 缺点，如果我们换了一个Item，我们就需要在ViewHolder中从新添加对应空间的变量
 * 也就需要重写viewHolder,
 * 因此这里用了一个通用的方法，就是在viewHolder中用一个类似于map的键值来保存findViewById对象，
 * 这种比先前还有一个好处就是，不管多少条Item，Item中的每个控件只会被findViewById一次
 * 以前的方法，当前页面缓存的个数4个，然后内存中对每个控件都是对应4个，也就是findViewById4次，如果屏幕较大就会越来越多
 * 以后一个项目几十个Adapter一个ViewHolder直接hold住全场
 *
 * @ Author: qiyue (ustory)
 * @ Email: qiyuekoon@foxmail.com
 * @ Data:2016/3/6
 */
class ViewHolder(private val mContext: Context, parent: ViewGroup, val layoutId: Int,
                 position: Int) {
    private val mViews: SparseArray<View>
    var position: Int = 0
        private set
    val convertView: View

    init {
        this.position = position
        this.mViews = SparseArray()
        convertView = LayoutInflater.from(mContext).inflate(layoutId, parent,
                false)
        convertView.tag = this
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**
     * 设置TextView的�??
     *
     * @param viewId
     * @param text
     * @return
     */
    fun setText(viewId: Int, text: String): ViewHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): ViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageBitmap(bitmap)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): ViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    fun setBackgroundColor(viewId: Int, color: Int): ViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(viewId: Int, backgroundRes: Int): ViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundResource(backgroundRes)
        return this
    }

    fun setTextColor(viewId: Int, textColor: Int): ViewHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(textColor)
        return this
    }

    fun setTextColorRes(viewId: Int, textColorRes: Int): ViewHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(mContext.resources.getColor(textColorRes))
        return this
    }

    @SuppressLint("NewApi")
    fun setAlpha(viewId: Int, value: Float): ViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId).alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId).startAnimation(alpha)
        }
        return this
    }

    fun setVisible(viewId: Int, visible: Boolean): ViewHolder {
        val view = getView<View>(viewId)
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun linkify(viewId: Int): ViewHolder {
        val view = getView<TextView>(viewId)
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    fun setTypeface(typeface: Typeface, vararg viewIds: Int): ViewHolder {
        for (viewId in viewIds) {
            val view = getView<TextView>(viewId)
            view.typeface = typeface
            view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    fun setProgress(viewId: Int, progress: Int): ViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.progress = progress
        return this
    }

    fun setProgress(viewId: Int, progress: Int, max: Int): ViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        view.progress = progress
        return this
    }

    fun setMax(viewId: Int, max: Int): ViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        return this
    }

    fun setRating(viewId: Int, rating: Float): ViewHolder {
        val view = getView<RatingBar>(viewId)
        view.rating = rating
        return this
    }

    fun setRating(viewId: Int, rating: Float, max: Int): ViewHolder {
        val view = getView<RatingBar>(viewId)
        view.max = max
        view.rating = rating
        return this
    }

    fun setTag(viewId: Int, tag: Any): ViewHolder {
        val view = getView<View>(viewId)
        view.tag = tag
        return this
    }

    fun setTag(viewId: Int, key: Int, tag: Any): ViewHolder {
        val view = getView<View>(viewId)
        view.setTag(key, tag)
        return this
    }

    fun setChecked(viewId: Int, checked: Boolean): ViewHolder {
        val view = getView<View>(viewId) as Checkable
        view.isChecked = checked
        return this
    }

    /**
     * 关于事件
     */
    fun setOnClickListener(viewId: Int,
                           listener: View.OnClickListener): ViewHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    fun setOnTouchListener(viewId: Int,
                           listener: View.OnTouchListener): ViewHolder {
        val view = getView<View>(viewId)
        view.setOnTouchListener(listener)
        return this
    }

    fun setOnLongClickListener(viewId: Int,
                               listener: View.OnLongClickListener): ViewHolder {
        val view = getView<View>(viewId)
        view.setOnLongClickListener(listener)
        return this
    }

    companion object {

        operator fun get(context: Context, convertView: View?,
                         parent: ViewGroup, layoutId: Int, position: Int): ViewHolder {
            if (convertView == null) {
                return ViewHolder(context, parent, layoutId, position)
            } else {
                val holder = convertView.tag as ViewHolder
                holder.position = position
                return holder
            }
        }
    }

}
