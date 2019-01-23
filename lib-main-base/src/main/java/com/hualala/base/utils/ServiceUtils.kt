package com.hualala.base.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 服务开启与关闭
 */

object ServiceUtils {
    /**
     * Judge whether a service is running
     *
     * @param context
     * @param className
     * @return
     */
    fun isServiceRunning(context: Context, className: String): Boolean {
        var isRunning = false
        val activityManager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val serviceInfos = activityManager
                .getRunningServices(Integer.MAX_VALUE)
        for (si in serviceInfos) {
            if (className == si.service.className) {
                isRunning = true
            }
        }
        return isRunning
    }

    /**
     * Stop running service
     *
     * @param context
     * @param className
     * @return
     */
    fun stopRunningService(context: Context, className: String): Boolean {
        var intent_service: Intent? = null
        var ret = false
        try {
            intent_service = Intent(context, Class.forName(className))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (intent_service != null) {
            ret = context.stopService(intent_service)
        }
        return ret
    }
}
