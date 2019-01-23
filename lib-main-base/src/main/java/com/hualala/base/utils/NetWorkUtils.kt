package com.hualala.base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import android.text.TextUtils

/**
 * 注意 要添加网络权限 android.permission.ACCESS_NETWORK_STATE
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 获取网络状态
 */

object NetWorkUtils {
    val NETWORK_TYPE_WIFI = "wifi"
    val NETWORK_TYPE_3G = "eg"
    val NETWORK_TYPE_2G = "2g"
    val NETWORK_TYPE_WAP = "wap"
    val NETWORK_TYPE_UNKNOWN = "unknown"
    val NETWORK_TYPE_DISCONNECT = "disconnect"

    /**
     * Get network type
     *
     * @param context
     * @return
     */
    fun getNetworkType(context: Context): Int {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo?.type ?: -1
    }

    /**
     * Check network
     *
     * @param context
     * @return
     */
    fun checkNetwork(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    /**
     * Get network type name
     *
     * @param context
     * @return
     */
    fun getNetworkTypeName(context: Context): String {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo = manager.activeNetworkInfo
        var type = NETWORK_TYPE_DISCONNECT
        if (manager == null || networkInfo == null) {
            return type
        }

        if (networkInfo.isConnected) {
            val typeName = networkInfo.typeName
            if ("WIFI".equals(typeName, ignoreCase = true)) {
                type = NETWORK_TYPE_WIFI
            } else if ("MOBILE".equals(typeName, ignoreCase = true)) {
                val proxyHost = android.net.Proxy.getDefaultHost()
                type = if (TextUtils.isEmpty(proxyHost))
                    if (isFastMobileNetwork(context)) NETWORK_TYPE_3G else NETWORK_TYPE_2G
                else
                    NETWORK_TYPE_WAP
            } else {
                type = NETWORK_TYPE_UNKNOWN
            }
        }
        return type
    }

    /**
     * Whether is fast mobile network
     *
     * @param context
     * @return
     */
    private fun isFastMobileNetwork(context: Context): Boolean {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return false

        when (telephonyManager.networkType) {
            TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_LTE -> return true
            TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_UNKNOWN -> return false
            else -> return false
        }
    }

}
