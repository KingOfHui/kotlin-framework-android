package com.hualala.base.data.net.updownload

import com.hualala.base.data.net.request.ProgressRequestListener
import com.hualala.base.data.net.response.ProgressResponseListener

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceGenerator {

    private val HOST = "http://www.xxx.com/ "

    private val builder = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

    fun <T> createService(tClass: Class<T>): T {
        return builder.build().create(tClass)
    }

    /**
     * 创建带响应进度(下载进度)回调的service
     */
    fun <T> createResponseService(tClass: Class<T>, listener: ProgressResponseListener): T {
        return builder
                .client(HttpClientHelper.addProgressResponseListener(listener))
                .build()
                .create(tClass)
    }

    /**
     * 创建带请求体进度(上传进度)回调的service
     */
    fun <T> createReqeustService(tClass: Class<T>, listener: ProgressRequestListener): T {
        return builder
                .client(HttpClientHelper.addProgressRequestListener(listener))
                .build()
                .create(tClass)
    }

}
