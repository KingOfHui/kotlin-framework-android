package com.hualala.base.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: SHA1 操作类
 */

object SHA1Utils {
    /**
     * Encode SHA1 for a string
     *
     * @param s
     * @return
     */
    fun SHA1(s: String): String? {
        try {
            val digest = MessageDigest.getInstance("SHA-1")
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()
            return toHexString(messageDigest)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun toHexString(keyData: ByteArray?): String? {
        if (keyData == null) {
            return null
        }
        val expectedStringLen = keyData.size * 2
        val sb = StringBuilder(expectedStringLen)
        for (i in keyData.indices) {
            var hexStr = Integer.toString(keyData[i].toInt() and 0x00FF, 16)
            if (hexStr.length == 1) {
                hexStr = "0$hexStr"
            }
            sb.append(hexStr)
        }
        return sb.toString()
    }
}
