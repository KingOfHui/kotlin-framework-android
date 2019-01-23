package com.hualala.base.utils

import android.util.Log
import com.hualala.base.BuildConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import timber.log.Timber

class TimberUtil {

    companion object {

        fun initTimer(){
            if (BuildConfig.DEBUG){
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(FileLoggingTree())
            }
        }
    }

    class FileLoggingTree: Timber.Tree() {

        private lateinit var mLogger: Logger

        init {
            mLogger = LoggerFactory.getLogger("releaselog")
        }

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

            when (priority) {
                Log.VERBOSE -> return
                Log.DEBUG -> mLogger.debug(message)
                Log.INFO -> mLogger.info(message)
                Log.WARN -> mLogger.warn(message)
                Log.ERROR -> mLogger.error(message)
            }

        }

    }

}