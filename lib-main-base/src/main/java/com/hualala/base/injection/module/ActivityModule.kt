package com.hualala.base.injection.module

import android.app.Activity
import com.hualala.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun providesActivity(): Activity {
        return activity
    }
}