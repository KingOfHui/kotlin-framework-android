package com.hualala.base.utils

import android.text.TextUtils
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 字符串操作
 */

object StringUtils {
    /**
     * Judge whether a string is whitespace, empty ("") or null.
     *
     * @param str
     * @return
     */
    fun isEmpty(str: String?): Boolean {
        val strLen: Int = str?.length ?: 0
        if (str.isNullOrEmpty() || str.equals("null", ignoreCase = true)) {
            return true
        }
        for (i in 0 until strLen) {
            if (Character.isWhitespace(str!![i]) == false) {
                return false
            }
        }
        return true
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     *
     * @param a
     * @param b
     * @return
     */
    fun equals(a: CharSequence, b: CharSequence): Boolean {
        return TextUtils.equals(a, b)
    }

    /**
     * Judge whether a string is number.
     *
     * @param str
     * @return
     */
    fun isNumeric(str: String): Boolean {
        var i = str.length
        while (--i >= 0) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }

    /**
     * Encode a string
     *
     * @param str
     * @return
     */
    fun encodeString(str: String): String {
        try {
            return URLEncoder.encode(str, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return str
        }

    }

    /**
     * Decode a string
     *
     * @param str
     * @return
     */
    fun decodeString(str: String): String {
        try {
            return URLDecoder.decode(str, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return str
        }

    }

    /**
     * Converts this string to lower case, using the rules of `locale`.
     *
     * @param s
     * @return
     */
    fun toLowerCase(s: String): String {
        return s.toLowerCase(Locale.getDefault())
    }

    /**
     * Converts this this string to upper case, using the rules of `locale`.
     *
     * @param s
     * @return
     */
    fun toUpperCase(s: String): String {
        return s.toUpperCase(Locale.getDefault())
    }
}
