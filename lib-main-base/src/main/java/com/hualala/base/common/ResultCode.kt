package com.hualala.base.common

class ResultCode {
    companion object {

        const val SUCCESS = "000"                                                               //操作成功

        /**
         * 错误
         */
        const val ERROR_PREFIX                  = "QPA_"
        const val ACCOUNT_NOT_EXIST             = "${ERROR_PREFIX}000001"                       //账号不存在
        const val SMSCODE_WRONG                 = "${ERROR_PREFIX}000002"                       //验证码错误，请重试
        const val SMSCODE_EXPIRE                = "${ERROR_PREFIX}000003"                       //验证码已过期
        const val QUERYSETTLE_ERROR             = "${ERROR_PREFIX}000004"                       //获取结算主体失败
        const val ACCESSTOKEN_EXPIRE            = "${ERROR_PREFIX}000005"                       //token过期，请重新登录
        const val MOBILE_NOT_VALID              = "${ERROR_PREFIX}000006"                       //手机号无效
        const val LOGIN_CONFIRM_FAIL            = "${ERROR_PREFIX}000007"                       //确认登录失败
        const val QUERYSHOP_ERROR               = "${ERROR_PREFIX}000008"                       //获取店铺失败,请重试
        const val OPEN_QUICKPASS_ERROR          = "${ERROR_PREFIX}000009"                       //哗啦啦支付开通失败，请重试
        const val QUERY_DATA_ERROR              = "${ERROR_PREFIX}000010"                       //查询数据失败,请重试
        const val WITHDRAW_MONEY_FAIL           = "${ERROR_PREFIX}000011"                       //提现失败,请重试
        const val PAYVOLUME_INVALID             = "${ERROR_PREFIX}000012"                       //支付金额非法,请重试
        const val PAY_NO_SESSIONID              = "${ERROR_PREFIX}000013"                       //缺少sessionID
        const val QRCODE_INVALID                = "${ERROR_PREFIX}000014"                       //二维码无效
        const val PAY_ORDER_NO_WRONG            = "${ERROR_PREFIX}000015"                       //缺少支付订单号
        const val ADD_NOTICE_FAIL               = "${ERROR_PREFIX}000016"                       //新增通知失败, 请重试
    }
}