package com.hualala.sample.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadApi {

    @Streaming
    @GET
    fun getNewApk(@Url url: String): Observable<ResponseBody>

}