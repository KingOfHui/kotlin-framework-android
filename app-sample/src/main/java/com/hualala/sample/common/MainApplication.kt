package com.hualala.sample.common

import android.util.Log
import com.hualala.base.common.BaseApplication
import com.hualala.base.common.BaseConstant
import com.hualala.base.utils.AppUtils
import com.hualala.base.utils.PrefsUtils
import com.hualala.sample.BuildConfig
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/*
    主工程 Application
 */
class MainApplication : BaseApplication() {

    companion object {
        fun initXG() {
            XGPushManager.registerPush(context, object : XGIOperateCallback {
                override fun onSuccess(data: Any?, flag: Int) {
                    PrefsUtils.putString(BaseConstant.XG_TOKEN, data.toString())

                }

                override fun onFail(data: Any?, errCode: Int, msg: String?) {
                    Log.d("TPush", "哗啦啦闪付－注册失败，错误码：" + errCode + ",错误信息：" + msg);
                }
            })

            XGPushManager.bindAccount(context, "XINGE")
            XGPushManager.setTag(context, "XINGE")
        }
    }

    override fun onCreate() {
        super.onCreate()

        //xg
        if (BuildConfig.DEBUG) {
            XGPushConfig.enableDebug(this, true)
        }

        initXG()

        //xf
        initXf()

        //umeng
        initUmeng()

        PrefsUtils.putString(BaseConstant.VERSION, "${AppUtils.getVersionName(this)}")
        PrefsUtils.putBoolean(BaseConstant.CASH_RECEIPTS, true)
    }

    private fun initXf() {
        val param = StringBuffer()
        param.append("appid=")
        param.append(XF_APP_ID)
        param.append(",")
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC)
        SpeechUtility.createUtility(this, param.toString())
    }

    private fun initUmeng() {
        //MobclickAgent.startWithConfigure(MobclickAgent.UMAnalyticsConfig(this, UMENG_APP_ID, "hualala"))
        UMConfigure.init(this,
                UMENG_APP_ID,
                "hualala",
                0,
                ""
                )
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
        //MobclickAgent.setSecret(this, )

    }

}


