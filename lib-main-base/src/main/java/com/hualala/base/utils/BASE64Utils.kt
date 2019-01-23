package com.hualala.base.utils

import android.util.Base64

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: bese64的转换
 */

object BASE64Utils {
    /**
     * base64编码
     *
     * @param input
     * @return
     */
    fun encodeBase64(input: ByteArray): ByteArray {
        return Base64.encode(input, Base64.DEFAULT)
    }

    /**
     * base64编码
     *
     * @param s
     * @return
     */
    fun encodeBase64(s: String): String {
        return Base64.encodeToString(s.toByteArray(), Base64.DEFAULT)
    }

    /**
     * base64解码
     *
     * @param input
     * @return
     */
    fun decodeBase64(input: ByteArray): ByteArray {
        return Base64.decode(input, Base64.DEFAULT)
    }

    /**
     * base64解码
     *
     * @param s
     * @return
     */
    fun decodeBase64(s: String): String {
        return String(Base64.decode(s, Base64.DEFAULT))
    }
}
