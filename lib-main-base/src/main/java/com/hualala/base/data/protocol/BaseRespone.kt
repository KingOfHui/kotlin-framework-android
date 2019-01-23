package com.hualala.base.data.protocol

import java.io.Serializable

/*
    能用响应对象
    @code:响应状态码
    @message:响应文字消息
    @data:具体响应业务对象
 */
data class BaseRespone<out T>(val code: String, val msg: String, val data: T, val hasMore: Boolean): Serializable