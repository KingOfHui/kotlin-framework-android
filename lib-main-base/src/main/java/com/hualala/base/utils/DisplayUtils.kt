package com.hualala.base.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.view.View
import android.view.Window

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 屏幕相关
 */

object DisplayUtils {
    /**
     * 是否横屏
     *
     * @param context
     * @return
     */
    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * 是否竖屏
     *
     * @param context
     * @return
     */
    fun isPortrait(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * Get screen width, in pixels
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.widthPixels
    }

    /**
     * Get screen height, in pixels
     *
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.heightPixels
    }

    /**
     * Get screen density, the logical density of the display
     *
     * @param context
     * @return
     */
    fun getScreenDensity(context: Context): Float {
        val dm = context.resources.displayMetrics
        return dm.density
    }

    /**
     * Get screen density dpi, the screen density expressed as dots-per-inch
     *
     * @param context
     * @return
     */
    fun getScreenDensityDPI(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.densityDpi
    }

    /**
     * Get titlebar height, this method cannot be used in onCreate(),onStart(),onResume(), unless it is called in the
     * post(Runnable).
     *
     * @param activity
     * @return
     */
    fun getTitleBarHeight(activity: Activity): Int {
        val statusBarHeight = getStatusBarHeight(activity)
        val contentViewTop = activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).getTop()
        val titleBarHeight = contentViewTop - statusBarHeight
        return if (titleBarHeight < 0) 0 else titleBarHeight
    }

    /**
     * Get statusbar height, this method cannot be used in onCreate(),onStart(),onResume(), unless it is called in the
     * post(Runnable).
     *
     * @param activity
     * @return
     */
    fun getStatusBarHeight(activity: Activity): Int {
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }

    /**
     * Get statusbar height
     *
     * @param activity
     * @return
     */
    fun getStatusBarHeight2(activity: Activity): Int {
        var statusBarHeight = getStatusBarHeight(activity)
        if (0 == statusBarHeight) {
            val localClass: Class<*>
            try {
                localClass = Class.forName("com.android.internal.R\$dimen")
                val localObject = localClass.newInstance()
                val id = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString())
                statusBarHeight = activity.resources.getDimensionPixelSize(id)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: SecurityException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }

        }
        return statusBarHeight
    }

    /**
     * Convert dp to px by the density of phone
     *
     * @param context
     * @param dp
     * @return
     */
    fun dip2px(context: Context?, dp: Float): Int {
        return if (context == null) {
            -1
        } else (dipToPx(context, dp) + 0.5f).toInt()
    }

    /**
     * Convert dp to px
     *
     * @param context
     * @param dp
     * @return
     */
    private fun dipToPx(context: Context?, dp: Float): Float {
        if (context == null) {
            return -1f
        }
        val scale = context.resources.displayMetrics.density
        return dp * scale
    }

    /**
     * Convert px to dp by the density of phone
     *
     * @param context
     * @param px
     * @return
     */
    fun px2dip(context: Context?, px: Float): Int {
        return if (context == null) {
            -1
        } else (pxToDip(context, px) + 0.5f).toInt()
    }

    /**
     * Convert px to dp
     *
     * @param context
     * @param px
     * @return
     */
    private fun pxToDip(context: Context?, px: Float): Float {
        if (context == null) {
            return -1f
        }
        val scale = context.resources.displayMetrics.density
        return px / scale
    }

    /**
     * Convert px to sp
     *
     * @param context
     * @param px
     * @return
     */
    fun px2sp(context: Context, px: Float): Int {
        return (pxToSp(context, px) + 0.5f).toInt()
    }

    /**
     * Convert px to sp
     *
     * @param context
     * @param px
     * @return
     */
    private fun pxToSp(context: Context, px: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return px / fontScale
    }

    /**
     * Convert sp to px
     *
     * @param context
     * @param sp
     * @return
     */
    fun sp2px(context: Context, sp: Float): Int {
        return (spToPx(context, sp) + 0.5f).toInt()
    }

    /**
     * Convert sp to px
     *
     * @param context
     * @param sp
     * @return
     */
    private fun spToPx(context: Context, sp: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return sp * fontScale
    }
}
