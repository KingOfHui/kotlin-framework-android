package com.hualala.base.common

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.stetho.Stetho
import com.hualala.base.BuildConfig
import com.hualala.base.injection.component.AppComponent
import com.hualala.base.injection.component.DaggerAppComponent
import com.hualala.base.injection.module.AppModule
import com.hualala.base.utils.TimberUtil
import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import kotlin.properties.Delegates

open class BaseApplication : TinkerApplication(
        ShareConstants.TINKER_ENABLE_ALL,
        "com.hualala.base.common.BaseApplicationLike",
        "com.tencent.tinker.loader.TinkerLoader",
        false) {

    lateinit var appComponent: AppComponent

    /*
        全局伴生对象
    */
    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        initStethoForDebug()
        initAppInjection()

        context = this

        //ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()               // 开启日志
            ARouter.openDebug()             // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace()       // 打印日志的时候打印线程堆栈
        }
        ARouter.init(this)

        //Timber
        TimberUtil.initTimer()

    }

    private fun initStethoForDebug() {
        if (BuildConfig.DEBUG)
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build())
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}