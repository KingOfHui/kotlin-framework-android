package com.hualala.provider.common.router.service

import com.alibaba.android.arouter.facade.template.IProvider

interface SampleProvider: IProvider {

    /*
        获取版本号
     */
    fun getAppVersionName(): String

}