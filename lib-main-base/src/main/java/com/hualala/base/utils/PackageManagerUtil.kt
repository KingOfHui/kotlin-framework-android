package com.hualala.base.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.*
import android.content.res.Resources


/**
 * @author wlj
 * @date 2018/09/01
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description PackageManager工具类
 */
object PackageManagerUtil {


    fun queryIntentActivities(context: Context, intent: Intent): List<ResolveInfo> {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.queryIntentActivities(intent, 0)
        }
    }

    fun getPackageManager(context: Context): PackageManager {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager
        }
    }

    @Throws(PackageManager.NameNotFoundException::class)
    fun getPackageInfo(context: Context, packageName: String, flags: Int): PackageInfo {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getPackageInfo(packageName, flags)
        }
    }

    @Throws(PackageManager.NameNotFoundException::class)
    fun getResourcesForApplication(context: Context, packageName: String): Resources {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getResourcesForApplication(packageName)
        }
    }

    fun getInstalledPackages(context: Context, flags: Int): List<PackageInfo> {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getInstalledPackages(flags)
        }
    }

    fun getLaunchIntentForPackage(context: Context, packageName: String): Intent? {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getLaunchIntentForPackage(packageName)
        }
    }

    fun getPackageArchiveInfo(context: Context, archiveFilePath: String, flags: Int): PackageInfo {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getPackageArchiveInfo(archiveFilePath, flags)
        }
    }

    @Throws(PackageManager.NameNotFoundException::class)
    fun getApplicationInfo(context: Context, packageName: String, flags: Int): ApplicationInfo {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getApplicationInfo(packageName, flags)
        }
    }

    fun resolveActivity(context: Context, intent: Intent, flags: Int): ResolveInfo {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.resolveActivity(
                    intent, flags)
        }
    }

    fun hasSystemFeature(context: Context, name: String): Boolean {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.hasSystemFeature(name)
        }
    }

    fun clearPackagePreferredActivities(context: Context, packageName: String) {
        synchronized(PackageManagerUtil::class.java) {
            context.packageManager.clearPackagePreferredActivities(packageName)
        }
    }

    fun setComponentEnabledSetting(context: Context, componentName: ComponentName,
                                   newState: Int, flags: Int) {
        synchronized(PackageManagerUtil::class.java) {
            context.packageManager.setComponentEnabledSetting(componentName,
                    newState,
                    flags
            )
        }
    }

    fun queryIntentServices(context: Context, intent: Intent, flags: Int): List<ResolveInfo> {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.queryIntentServices(intent, flags)
        }
    }

    fun getApplicationLabel(context: Context, info: ApplicationInfo): CharSequence {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getApplicationLabel(info)
        }
    }

    fun queryContentProviders(context: Context, processName: String, uid: Int, flags: Int): List<ProviderInfo> {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.queryContentProviders(processName, uid, flags)
        }
    }

    fun getPackagesForUid(context: Context, uid: Int): Array<String>? {
        synchronized(PackageManagerUtil::class.java) {
            return context.packageManager.getPackagesForUid(uid)
        }
    }
}
