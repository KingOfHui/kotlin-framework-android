package com.hualala.base.rx

import com.hualala.base.common.ResultCode
import com.hualala.base.data.protocol.BaseRespone
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

/*
    Boolean类型转换封装
 */

class BaseFunctionBoolean<T> : Function<BaseRespone<T>, ObservableSource<Boolean>> {
    override fun apply(t: BaseRespone<T>): ObservableSource<Boolean> {
        return if (ResultCode.SUCCESS.equals(t.code)) Observable.just(true) else Observable.error(BaseException(t.code, t.msg))
    }

}