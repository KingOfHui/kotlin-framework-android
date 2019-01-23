package com.hualala.base.data.net

import android.os.Build
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hualala.base.BuildConfig
import com.hualala.base.common.BaseConstant
import com.hualala.base.utils.PrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor() {

    /*
        单例实现
    */
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit
    private val interceptor: Interceptor

    //初始化
    init {

        //通用拦截
        interceptor = Interceptor { chain ->
            val request = chain.request()

            val url = request.url()

            var builder = url.newBuilder().apply {
                addQueryParameter(BaseConstant.TRACE_ID, System.currentTimeMillis().toString())
                val loginUrl = BaseConstant.SERVER_ADDRESS + "/login"
                if (!loginUrl.equals(url.toString())){
                    if (PrefsUtils.getString(BaseConstant.ACCESS_TOKEN).isNotEmpty()){
                        addQueryParameter(BaseConstant.ACCESS_TOKEN, PrefsUtils.getString(BaseConstant.ACCESS_TOKEN))
                    }
                }
            }

            val requestBuilder = request.newBuilder()
            requestBuilder.url(builder.build())
                    .method(request.method(), request.body())
                    .addHeader("Content_Type", "application/json")
                    .addHeader("charset", "UTF-8")
                    .addHeader(BaseConstant.VERSION, PrefsUtils.getString(BaseConstant.VERSION))
                    .addHeader(BaseConstant.OS, BaseConstant.OS_NAME)
                    .addHeader(BaseConstant.OS_VERSION, Build.VERSION.SDK_INT.toString())
                    .addHeader(BaseConstant.MODEL, Build.MODEL)
                    if (PrefsUtils.getInt(BaseConstant.GROUP_ID) != null){
                        requestBuilder.addHeader(BaseConstant.GROUP_ID, PrefsUtils.getInt(BaseConstant.GROUP_ID).toString())
                    }
            chain.proceed(requestBuilder.build())

        }

        //Retrofit实例化
        retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }

    /*
        OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
                .addInterceptor(initLogInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
        /*
        if (!BuildConfig.DEBUG) {
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            }).sslSocketFactory(SSLSocketFactoryMaker.sslSocketFactory)
        }
        */
        return builder.build()
    }

    /*
       日志拦截器
     */
    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    /*
        具体服务实例化
    */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

}