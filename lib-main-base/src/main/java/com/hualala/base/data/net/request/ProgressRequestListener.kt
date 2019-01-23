package com.hualala.base.data.net.request

/**
 * 请求体进度回调接口，用于文件上传进度回调
 */
interface ProgressRequestListener {
    fun onRequestProgress(bytesWritten: Long, contentLength: Long, done: Boolean)
}
