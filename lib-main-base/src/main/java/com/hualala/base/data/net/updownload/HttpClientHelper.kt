package com.hualala.base.data.net.updownload

import com.hualala.base.data.net.request.ProgressRequestBody
import com.hualala.base.data.net.request.ProgressRequestListener
import com.hualala.base.data.net.response.ProgressResponseBody
import com.hualala.base.data.net.response.ProgressResponseListener
import okhttp3.OkHttpClient

object HttpClientHelper {

    /**
     * 包装OkHttpClient，用于下载文件的回调
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    fun addProgressResponseListener(progressListener: ProgressResponseListener): OkHttpClient {
        val client = OkHttpClient.Builder()
        //增加拦截器
        client.addInterceptor { chain ->
            //拦截
            val originalResponse = chain.proceed(chain.request())

            //包装响应体并返回
            originalResponse.newBuilder()
                    .body(ProgressResponseBody(originalResponse.body()!!, progressListener))
                    .build()
        }
        return client.build()
    }

    /**
     * 包装OkHttpClient，用于上传文件的回调
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    fun addProgressRequestListener(progressListener: ProgressRequestListener): OkHttpClient {
        val client = OkHttpClient.Builder()
        //增加拦截器
        client.addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                    .method(original.method(), ProgressRequestBody(original.body()!!, progressListener))
                    .build()
            chain.proceed(request)
        }
        return client.build()
    }
}
