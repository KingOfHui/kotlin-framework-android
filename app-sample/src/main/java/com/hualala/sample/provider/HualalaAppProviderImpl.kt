package com.hualala.sample.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.hualala.base.common.BaseConstant
import com.hualala.base.utils.PrefsUtils
import com.hualala.provider.common.router.RouterPath
import com.hualala.provider.common.router.service.SampleProvider

@Route(path = RouterPath.SampleApp.PROVIDER_SAMPLE_APP)
class SampleAppProviderImpl: SampleProvider {

    private var mContext:Context? = null

    override fun init(context: Context?) {
        mContext = context
    }

    override fun getAppVersionName(): String = PrefsUtils.getString(BaseConstant.VERSION)

}