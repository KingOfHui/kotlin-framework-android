package com.hualala.base.rx

import com.hualala.base.common.ResultCode
import com.hualala.base.data.protocol.BaseRespone
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

/*
    通用数据类型转换封装
 */
class BasePageFunction<T> : Function<BaseRespone<T>, ObservableSource<BaseRespone<T>>> {
    override fun apply(t: BaseRespone<T>): ObservableSource<BaseRespone<T>> {
        return if (ResultCode.SUCCESS.equals(t.code)) Observable.just(t) else Observable.error(BaseException(t.code, t.msg))
    }
}
