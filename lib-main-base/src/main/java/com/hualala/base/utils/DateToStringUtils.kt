package com.hualala.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description 时间转换工具类
 */
object DateToStringUtils {
    /**
     * 字符串转换到时间格式
     * @param dateStr 需要转换的字符串
     * @return dateFormatStr 需要转换的字符串的时间格式
     * @param formatStr 需要格式的目标字符串  举例 yyyyMMdd
     * @return String 返回转换后的时间字符串
     * @throws ParseException 转换异常
     */
    fun StringToDate(dateStr: String, dateFormatStr: String, formatStr: String): String {
        val sdf = SimpleDateFormat(dateFormatStr)
        var date: Date? = null
        try {
            date = sdf.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val s = SimpleDateFormat(formatStr)

        return s.format(date)
    }

    fun StringToDate2(dateStr: Long, dateFormatStr: String, formatStr: String): String {
        val sdf = SimpleDateFormat(dateFormatStr)
        var date: Date? = null
        try {
            date = sdf.parse(dateStr.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val s = SimpleDateFormat(formatStr)

        return s.format(date)
    }
}
