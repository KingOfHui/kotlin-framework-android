package com.hualala.base.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView

class DrawableCenterTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {

    override fun onDraw(canvas: Canvas) {
        // 获取TextView的Drawable对象，返回的数组长度应该是4，对应左上右下
        val drawables = compoundDrawables
        if (drawables != null) {
            var drawable: Drawable? = drawables[0]
            if (drawable != null) {
                // 当左边Drawable的不为空时，测量要绘制文本的宽度
                val textWidth = paint.measureText(text.toString())
                val drawablePadding = compoundDrawablePadding
                val drawableWidth = drawable.intrinsicWidth
                // 计算总宽度（文本宽度 + drawablePadding + drawableWidth）
                val bodyWidth = textWidth + drawablePadding.toFloat() + drawableWidth.toFloat()
                // 移动画布开始绘制的X轴
                canvas.translate((width - bodyWidth) / 2, 0f)
            } else if (drawables[1] != null) {
                // 否则如果上边的Drawable不为空时，获取文本的高度
                val rect = Rect()
                paint.getTextBounds(text.toString(), 0, text.toString().length, rect)
                val textHeight = rect.height().toFloat()
                val drawablePadding = compoundDrawablePadding
                val drawableHeight = drawable!!.intrinsicHeight
                // 计算总高度（文本高度 + drawablePadding + drawableHeight）
                val bodyHeight = textHeight + drawablePadding.toFloat() + drawableHeight.toFloat()
                // 移动画布开始绘制的Y轴
                canvas.translate(0f, (height - bodyHeight) / 2)
            }
        }
        super.onDraw(canvas)
    }
}


