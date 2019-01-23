package com.hualala.base.common

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.multidex.MultiDex
import android.widget.Toast
import com.hualala.base.BuildConfig
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.interfaces.BetaPatchListener
import com.tencent.tinker.entry.DefaultApplicationLike
import java.util.*

/**
 * 自定义ApplicationLike类.
 *
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里<br></br>
 *
 * @author wenjiewu
 * @since 2016/11/7
 */
class BaseApplicationLike(
        application: Application,
        tinkerFlags: Int,
        tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long,
        applicationStartMillisTime: Long,
        tinkerResultIntent: Intent) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {

    companion object {
        val TAG = "Tinker.BaseApplicationLike"
    }

    override fun onCreate() {
        super.onCreate()
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true
        // 补丁回调接口
        Beta.betaPatchListener = object : BetaPatchListener {
            override fun onPatchReceived(patchFile: String) {
                Toast.makeText(application, "补丁下载地址$patchFile", Toast.LENGTH_SHORT).show()
            }

            override fun onDownloadReceived(savedLength: Long, totalLength: Long) {
                Toast.makeText(application,
                        String.format(Locale.getDefault(), "%s %d%%",
                                Beta.strNotificationDownloading,
                                (if (totalLength == 0L) 0 else savedLength * 100 / totalLength).toInt()),
                        Toast.LENGTH_SHORT).show()
            }

            override fun onDownloadSuccess(msg: String) {
                Toast.makeText(application, "补丁下载成功", Toast.LENGTH_SHORT).show()
            }

            override fun onDownloadFailure(msg: String) {
                Toast.makeText(application, "补丁下载失败", Toast.LENGTH_SHORT).show()

            }

            override fun onApplySuccess(msg: String) {
                Toast.makeText(application, "补丁应用成功", Toast.LENGTH_SHORT).show()
            }

            override fun onApplyFailure(msg: String) {
                Toast.makeText(application, "补丁应用失败", Toast.LENGTH_SHORT).show()
            }

            override fun onPatchRollback() {

            }
        }

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(application, true)
        // 多渠道需求塞入
        // String channel = WalleChannelReader.getChannel(getApplication());
        // Bugly.setAppChannel(getApplication(), channel);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        Bugly.init(application, BaseConstant.BUGLY_APP_ID, BuildConfig.DEBUG)
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        // TODO: 安装tinker
        Beta.installTinker(this)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(
            callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }

    override fun onTerminate() {
        super.onTerminate()
        Beta.unInit()
    }


}
