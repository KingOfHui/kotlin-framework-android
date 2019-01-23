package com.hualala.base.utils

import android.os.Build

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 获取系统信息工具类
 */

object SystemUtils {

    /**
     * ART
     *
     * @return
     */
    val isART: Boolean
        get() {
            val vmVersion = System.getProperty("java.vm.version")
            return vmVersion != null && vmVersion.startsWith("2")
        }

    /**
     * DALVIK
     *
     * @return
     */
    val isDalvik: Boolean
        get() {
            val vmVersion = System.getProperty("java.vm.version")
            return vmVersion != null && vmVersion.startsWith("1")
        }

    /**
     * The brand (e.g., Xiaomi) the software is customized for, if any.
     *
     * @return
     */
    val brand: String
        get() = Build.BRAND

    /**
     * The name of the underlying board, like "MSM8660_SURF".
     *
     * @return
     */
    val board: String
        get() = Build.BOARD

    /**
     * The end-user-visible name for the end product, like "MI-ONE Plus".
     *
     * @return
     */
    val model: String
        get() = Build.MODEL

    /**
     * Either a changelist number, or a label like "JZO54K".
     *
     * @return
     */
    val id: String
        get() = Build.ID

    /**
     * The user-visible version string, like "4.1.2".
     *
     * @return
     */
    val versionRelease: String
        get() = Build.VERSION.RELEASE

    /**
     * The user-visible SDK version of the framework.
     *
     * @return
     */
    val versionSDK: Int
        get() = Build.VERSION.SDK_INT

    /**
     * A string that uniquely identifies this build. Do not attempt to parse this value.
     *
     * @return
     */
    val fingerPrint: String
        get() = Build.FINGERPRINT

    /**
     * The name of the overall product, like "mione_plus".
     *
     * @return
     */
    val product: String
        get() = Build.PRODUCT

    /**
     * The manufacturer of the product/hardware, like "Xiaomi".
     *
     * @return
     */
    val manufacturer: String
        get() = Build.MANUFACTURER

    /**
     * The name of the industrial design, like "mione_plus".
     *
     * @return
     */
    val device: String
        get() = Build.DEVICE

    /**
     * The name of the instruction set (CPU type + ABI convention) of native code, like "armeabi-v7a".
     *
     * @return
     */
    val cpuAbi: String
        get() = Build.CPU_ABI

    /**
     * The name of the second instruction set (CPU type + ABI convention) of native code, like "armeabi".
     *
     * @return
     */
    val cpuAbi2: String
        get() = Build.CPU_ABI2

    /**
     * A build ID string meant for displaying to the user, like "JZO54K".
     *
     * @return
     */
    val display: String
        get() = Build.DISPLAY
}
