package com.hualala.base.utils

import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: MD5相关操作
 */

object MD5Utils {
    /**
     * Encode MD5 for a string
     *
     * @param s
     * @return
     */
    fun encodeMD5(s: String): String? {
        try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()
            return toHexString(messageDigest)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun toHexString(keyData: ByteArray?): String? {
        if (keyData == null) {
            return null
        }
        val expectedStringLen = keyData.size * 2
        val sb = StringBuilder(expectedStringLen)
        for (i in keyData.indices) {
            var hexStr = Integer.toString(keyData[i].toInt() and 0x00FF.toInt(), 16)
            if (hexStr.length == 1) {
                hexStr = "0$hexStr"
            }
            sb.append(hexStr)
        }
        return sb.toString()
    }

    /**
     * Encode MD5 for a string
     *
     * @param s
     * @return
     */
    fun encodeMD52(s: String): String? {
        val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
        try {
            val btInput = s.toByteArray()
            val digest = MessageDigest.getInstance("MD5")
            digest.update(btInput)
            val md = digest.digest()
            val j = md.size
            val str = CharArray(j * 2)
            var k = 0
            for (i in 0 until j) {
                val byte0 = md[i]
                str[k++] = hexDigits[byte0.toInt().ushr(4) and 0xf]
                str[k++] = hexDigits[byte0.toInt() and 0xf]
            }
            return String(str)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * Encode md5 for a single file
     *
     * @param f
     * @return
     */
    fun encodeMD5(f: File): String? {
        if (!f.isFile) {
            return null
        }
        var digest: MessageDigest? = null
        val buffer = ByteArray(1024)
        var len: Int = 0
        try {
            digest = MessageDigest.getInstance("MD5")
            val `in` = FileInputStream(f)
            while (`in`.read(buffer, 0, 1024).also { len = it } != -1) {
                digest!!.update(buffer, 0, len)
            }
            `in`.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        val bigInt = BigInteger(1, digest!!.digest())
        return bigInt.toString(16)
    }
}
