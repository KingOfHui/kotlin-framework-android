package com.hualala.base.injection.component

import android.app.Activity
import android.content.Context
import com.hualala.base.injection.ActivityScope
import com.hualala.base.injection.module.ActivityModule
import com.hualala.base.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class, LifecycleProviderModule::class))
interface ActivityComponent {

    fun context(): Context
    fun activity(): Activity
    fun lifecycleProvider(): LifecycleProvider<*>

}