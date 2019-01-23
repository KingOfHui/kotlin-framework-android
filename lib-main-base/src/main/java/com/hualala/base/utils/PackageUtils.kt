package com.hualala.base.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import java.io.File
import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 包的相关操作
 */

object PackageUtils {
    /**
     * Check whether a particular package has been granted a particular permission.
     * 检查包是否授予权限
     *
     * @param context
     * @param permName
     * @param pkgName
     * @return
     */
    fun checkPermission(context: Context, permName: String, pkgName: String): Boolean {
        val pm = context.packageManager
        return PackageManager.PERMISSION_GRANTED == pm.checkPermission(permName, pkgName)
    }

    /**
     * Check whether a particular package has been granted a particular permission.
     * 检查包是否授予权限
     *
     * @param context
     * @param permName
     * @return
     */
    fun checkPermission(context: Context, permName: String): Boolean {
        val perm = context.checkCallingOrSelfPermission(permName)
        return perm == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Install package
     * 安装apk
     *
     * @param context
     * @param filePath
     * @return
     */
    fun install(context: Context, filePath: String): Boolean {
        val i = Intent(Intent.ACTION_VIEW)
        val file = File(filePath)
        if (file == null || !file.exists() || !file.isFile || file.length() <= 0) {
            return false
        }

        i.setDataAndType(Uri.parse("file://$filePath"), "application/vnd.android.package-archive")
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
        return true
    }

    /**
     * Uninstall package
     * 卸载apk
     *
     * @param context
     * @param packageName
     * @return
     */
    fun uninstall(context: Context, packageName: String?): Boolean {
        if (packageName == null || packageName.length == 0) {
            return false
        }

        val i = Intent(Intent.ACTION_DELETE, Uri.parse(StringBuilder(32).append("package:")
                .append(packageName).toString()))
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
        return true
    }

    /**
     * Judge whether packageName is a system application
     * 判断是否是一个系统的应用软件
     *
     * @param context
     * @return
     */
    fun isSystemApplication(context: Context?): Boolean {
        return if (context == null) {
            false
        } else isSystemApplication(context, context.packageName)
    }

    /**
     * Judge whether packageName is a system application
     * 判断是否是一个系统的应用软件
     *
     * @param context
     * @param packageName
     * @return
     */
    fun isSystemApplication(context: Context?, packageName: String): Boolean {
        return if (context == null) {
            false
        } else isSystemApplication(context.packageManager, packageName)
    }

    /**
     * Judge whether packageName is a system application
     * 判断是否是一个系统的应用软件
     *
     * @param packageManager
     * @param packageName
     * @return
     */
    fun isSystemApplication(packageManager: PackageManager?, packageName: String?): Boolean {
        if (packageManager == null || packageName == null || packageName.length == 0) {
            return false
        }
        try {
            val app = packageManager.getApplicationInfo(packageName, 0)
            return app != null && app.flags and ApplicationInfo.FLAG_SYSTEM > 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * Get installed package infos
     * 获取安装包信息
     *
     * @param context
     * @return
     */
    fun getInsatalledPackageInfos(context: Context): List<PackageInfo> {
        return context.packageManager.getInstalledPackages(0)
    }

    /**
     * Judge whether the packageName is installed
     * 判断是否安装了软件
     *
     * @param context
     * @param packageName
     * @return
     */
    fun isInsatalled(context: Context, packageName: String): Boolean {
        if (!StringUtils.isEmpty(packageName)) {
            val list = getInsatalledPackageInfos(context)
            if (!CollectionUtils.isEmpty(list)) {
                for (pi in list) {
                    if (packageName.equals(pi.packageName, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * Get all apps
     * 获取所有的APP
     *
     * @param context
     * @return
     */
    fun getAllApps(context: Context): List<PackageInfo> {
        val apps = ArrayList<PackageInfo>()
        val packageManager = context.packageManager
        val packageInfos = packageManager.getInstalledPackages(0)
        for (i in packageInfos.indices) {
            val pak = packageInfos[i]
            if (pak.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0) {
                apps.add(pak)
            }
        }
        return apps
    }

    /**
     * Start app by packageName
     * 通过包名启动应用程序
     *
     * @param context
     * @param packageName
     */
    fun startApp(context: Context, packageName: String) {
        var packageinfo: PackageInfo? = null
        try {
            packageinfo = context.packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        if (packageinfo == null) {
            return
        }

        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        resolveIntent.setPackage(packageinfo.packageName)

        val resolveinfoList = context.packageManager
                .queryIntentActivities(resolveIntent, 0)

        val resolveinfo = resolveinfoList.iterator().next()
        if (resolveinfo != null) {
            val pkgName = resolveinfo.activityInfo.packageName
            val className = resolveinfo.activityInfo.name

            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            val cn = ComponentName(pkgName, className)
            intent.component = cn
            context.startActivity(intent)
        }
    }
}
