package com.hualala.base.utils

import java.util.regex.Pattern

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 验证数据合法性
 */

object VerifyUtils {
    /**
     * verify whether email is valid
     *
     * @param email
     * @return
     */
    fun isEmail(email: String): Boolean {
        val pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)" + "+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    /**
     * verify whether email is valid
     *
     * @param email
     * @return
     */
    fun isEmail2(email: String): Boolean {
        val expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
        return email.matches(expr.toRegex())
    }

    /**
     * verify whether mobile number is valid
     *
     * @param number
     * @return
     */
    fun isMobileNumber(number: String): Boolean {
        val expr = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"
        return number.matches(expr.toRegex())
    }

    /**
     * verify whether url is valid
     *
     * @param url
     * @return
     */
    fun isUrl(url: String): Boolean {
        val regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
        val patt = Pattern.compile(regex)
        val matcher = patt.matcher(url)
        return matcher.matches()
    }

    /**
     * verify whether number and letter are only contained
     *
     * @param data
     * @return
     */
    fun isNumberAndLetter(data: String): Boolean {
        val expr = "^[A-Za-z0-9]+$"
        return data.matches(expr.toRegex())
    }

    /**
     * verify whether number is only contained
     *
     * @param data
     * @return
     */
    fun isNumber(data: String): Boolean {
        val expr = "^[0-9]+$"
        return data.matches(expr.toRegex())
    }

    /**
     * verify whether letter is only contained
     *
     * @param data
     * @return
     */
    fun isLetter(data: String): Boolean {
        val expr = "^[A-Za-z]+$"
        return data.matches(expr.toRegex())
    }

    /**
     * verify whether Chinese is only contained
     *
     * @param data
     * @return
     */
    fun isChinese(data: String): Boolean {
        val expr = "^[\u0391-\uFFE5]+$"
        return data.matches(expr.toRegex())
    }

    /**
     * verify whether Chinese is contained
     *
     * @param data
     * @return
     */
    fun isContainsChinese(data: String): Boolean {
        val chinese = "[\u0391-\uFFE5]"
        if (!StringUtils.isEmpty(data)) {
            for (i in 0 until data.length) {
                val temp = data.substring(i, i + 1)
                val flag = temp.matches(chinese.toRegex())
                if (flag) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * verify whether chinese identity card is valid
     *
     * @param data
     * @return
     */
    fun isChineseCard(data: String): Boolean {
        val expr = "^[0-9]{17}[0-9xX]$"
        return data.matches(expr.toRegex())
    }

    /**
     * verify whether post code is valid
     *
     * @param data
     * @return
     */
    fun isPostCode(data: String): Boolean {
        val expr = "^[0-9]{6,10}"
        return data.matches(expr.toRegex())
    }
}
