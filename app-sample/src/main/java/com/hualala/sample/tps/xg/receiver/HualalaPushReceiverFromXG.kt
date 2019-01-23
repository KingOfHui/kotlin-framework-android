package com.hualala.sample.tps.xg.receiver

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.hualala.base.common.BaseConstant
import com.hualala.base.utils.PrefsUtils
import com.iflytek.cloud.*
import com.iflytek.sunflower.FlowerCollector
import com.tencent.android.tpush.*

class HualalaPushReceiverFromXG : XGPushBaseReceiver(), InitListener, SynthesizerListener {

    private var TAG: String = "HualalaPushReceiverFromXG"

    lateinit var mSpeechSynthesizer: SpeechSynthesizer

    // 设置tag的回调
    override fun onSetTagResult(context: Context?, errorCode: Int, tagName: String?) {
        Log.d(TAG, "onSetTagResult")
    }

    // 通知展示
    override fun onNotifactionShowedResult(context: Context?, notifiShowedRlt: XGPushShowedResult?) {
        Log.d(TAG, "onNotifactionShowedResult")
        if (PrefsUtils.getBoolean(BaseConstant.CASH_RECEIPTS)){
            initXFSpeek(context!!)
            notifiShowedRlt?.content?.let {
                mSpeechSynthesizer.startSpeaking(it, this)
            }
        }

    }

    // 反注册的回调
    override fun onUnregisterResult(context: Context?, errorCode: Int) {
        Log.d(TAG, "onUnregisterResult")
    }

    // 删除tag的回调
    override fun onDeleteTagResult(context: Context?, errorCode: Int, tagName: String?) {
        Log.d(TAG, "onDeleteTagResult")
    }

    // 注册的回调
    override fun onRegisterResult(context: Context?, p1: Int, p2: XGPushRegisterResult?) {
        Log.d(TAG, "onRegisterResult")
    }

    // 消息透传的回调
    override fun onTextMessage(context: Context?, p1: XGPushTextMessage?) {
        Log.d(TAG, "onTextMessage")
    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击。此处不能做点击消息跳转，详细方法请参照官网的Android常见问题文档
    override fun onNotifactionClickedResult(context: Context?, message: XGPushClickedResult?) {
        Log.d(TAG, "onNotifactionClickedResult")
    }

    override fun onInit(p0: Int) {
    }

    override fun onBufferProgress(p0: Int, p1: Int, p2: Int, p3: String?) {
    }

    override fun onSpeakBegin() {
    }

    override fun onSpeakProgress(p0: Int, p1: Int, p2: Int) {
    }

    override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {
    }

    override fun onSpeakPaused() {
    }

    override fun onSpeakResumed() {
    }

    override fun onCompleted(p0: SpeechError?) {
    }

    private fun initXFSpeek(context: Context){

        //appid
        SpeechUtility. createUtility(context, SpeechConstant. APPID + "=5bc5aa1f" )

        //创建SpeechSynthesizer对象
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(context, this)
        mSpeechSynthesizer.let {
            //设置发音人
            FlowerCollector.setParameter(SpeechConstant.VOICE_NAME, "vixyun")
            //设置语速
            FlowerCollector.setParameter(SpeechConstant.SPEED, "50")
            //设置音量
            FlowerCollector.setParameter(SpeechConstant.VOLUME, "80")
            //设置云端
            FlowerCollector.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
            //设置合成音频保存位置
            FlowerCollector.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm")
        }

    }
}