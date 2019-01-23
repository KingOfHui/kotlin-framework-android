package com.hualala.base.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 数字格式化操作类
 */

object NumberUtils {
    /**
     * 保留一位小数
     *
     * @param number
     * @return
     */
    fun formatOneDecimal(number: Float): String {
        val oneDec = DecimalFormat("##0.0")
        return oneDec.format(number.toDouble())
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    fun formatTwoDecimal(number: Float): String {
        val twoDec = DecimalFormat("##0.00")
        return twoDec.format(number.toDouble())
    }

    /**
     * 保留两位小数百分比
     *
     * @param number
     * @return
     */
    fun formatTwoDecimalPercent(number: Float): String {
        return formatTwoDecimal(number) + "%"
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param scale        scale of the result returned.
     * @param roundingMode rounding mode to be used to round the result.
     * @return
     */
    @JvmOverloads
    fun roundingNumber(number: Float, scale: Int, roundingMode: RoundingMode = RoundingMode.HALF_UP): Double {
        val b = BigDecimal(number.toDouble())
        return b.setScale(scale, roundingMode).toDouble()
    }

}
