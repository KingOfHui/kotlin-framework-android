package com.hualala.base.common

import com.hualala.base.BuildConfig

class BaseConstant {
    companion object {
        //本地服务器地址
        var SERVER_ADDRESS = BuildConfig.SERVER_ADDRESS
        //App升级地址
        var APP_UPDATE_SERVER_ADDRESS = BuildConfig.SERVER_ADDRESS
        var CLIENT_TYPE = "580"
        var APP_TYPE = "hualalapay"

        //SP表名
        const val TABLE_PREFS = "quickpay"

        //traceID
        const val TRACE_ID = "traceID"
        //accessToken
        const val ACCESS_TOKEN = "accessToken"

        //login type
        const val LOGIN_TYPE = "loginType"
        const val ROLE_ADMIN = 1
        const val ROLE_SHOPOWNER = 2
        const val LOGIN_PHONE_NUMBER = "loginPhoneNumber"

        //settle
        const val SETTLE = "settleUnitInfo"

        //shop
        const val SHOP = "shopInfo"

        //message createtime
        const val MESSAGE_CREATE_TIME = "msgCreateTime"

        //cash_receipts
        const val CASH_RECEIPTS = "cashReceipts"

        //https
        //server cer
        const val SERER_CER = ""

        //client cer
        const val CLIENT_CER = ""

        //version
        const val VERSION = "version"
        //os
        const val OS = "os"
        const val OS_NAME = "1"
        //osversion
        const val OS_VERSION = "osversion"
        //model
        const val MODEL = "model"
        const val GROUP_ID = "groupID"

        //xg token
        const val XG_TOKEN = ""
        const val PUSH_PLATFORM: Int = 1

        /*
            bugly
        */
        const val BUGLY_APP_ID = "b684e204ee"

    }
}