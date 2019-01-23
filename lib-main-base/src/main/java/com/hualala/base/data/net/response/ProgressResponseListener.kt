package com.hualala.base.data.net.response

/**
 * 响应体进度回调接口，用于文件下载进度回调
 */
interface ProgressResponseListener {
    fun onResponseProgress(bytesRead: Long, contentLength: Long, done: Boolean)
}
