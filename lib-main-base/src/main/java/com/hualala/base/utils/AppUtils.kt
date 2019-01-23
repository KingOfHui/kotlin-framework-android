package com.hualala.base.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import javax.security.auth.x500.X500Principal

/**
 * @author： wlj
 * @Date: 2017-03-28
 * @email: wanglijundev@gmail.com
 * @desc: App 相关信息，包括版本名称、版本号、包名等等
 */

object AppUtils {
    private val DEBUG_DN = X500Principal(
            "CN=Android Debug,O=Android,C=US")

    /**
     * Get version name
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String {
        val info: PackageInfo
        try {
            info = context.packageManager.getPackageInfo(context.packageName, 0)
            return info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * Get version code
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): Int {
        val info: PackageInfo
        try {
            info = context.packageManager.getPackageInfo(context.packageName, 0)
            return info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * Get package name
     *
     * @param context
     * @return
     */
    fun getPackageName(context: Context): String {
        return context.packageName
    }

    /**
     * Get icon
     *
     * @param context
     * @return
     */
    fun getIcon(context: Context): Drawable? {
        return getAppIcon(context, getPackageName(context))
    }

    /**
     * Get app icon
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getAppIcon(context: Context, packageName: String): Drawable? {
        try {
            val pm = context.packageManager
            val info = pm.getApplicationInfo(packageName, 0)
            return info.loadIcon(pm)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Get app version name
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getAppVersionName(context: Context, packageName: String): String? {
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Get app version code
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getAppVersionCode(context: Context, packageName: String): Int {
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(packageName, 0)
            return packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return -1
    }

    /**
     * Get app name
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getAppName(context: Context, packageName: String): String? {
        try {
            val pm = context.packageManager
            val info = pm.getApplicationInfo(packageName, 0)
            return info.loadLabel(pm).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Get app permission
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getAppPermission(context: Context, packageName: String): Array<String>? {
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            return packageInfo.requestedPermissions
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Get app signature
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getAppSignature(context: Context, packageName: String): String? {
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            return packageInfo.signatures[0].toCharsString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Judge whether an app is dubuggable
     *
     * @param context
     * @return
     */
    fun isApkDebuggable(context: Context): Boolean {
        try {
            val info = context.applicationInfo
            return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {

        }

        return false
    }

    /**
     * Judge whether an app is dubuggable by package name
     *
     * @param context
     * @param packageName
     * @return
     */
    fun isApkDebugable(context: Context, packageName: String): Boolean {
        try {
            val pkginfo = context.packageManager.getPackageInfo(
                    packageName, PackageManager.GET_ACTIVITIES)
            if (pkginfo != null) {
                val info = pkginfo.applicationInfo
                return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
            }
        } catch (e: Exception) {
        }

        return false
    }

    /**
     * Judge whether an app is in background
     *
     * @param context
     * @return
     */
    fun isAppInBackground(context: Context): Boolean {
        val am = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val taskList = am.getRunningTasks(1)
        if (taskList != null && !taskList.isEmpty()) {
            val topActivity = taskList[0].topActivity
            if (topActivity != null && topActivity.packageName != context.packageName) {
                return true
            }
        }
        return false
    }
}
