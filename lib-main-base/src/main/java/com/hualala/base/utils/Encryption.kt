package com.hualala.base.utils

import android.util.Base64

import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

object Encryption {


    private val DES = "DES"

    private val key = "3214324242332"

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     * 加密键byte数组
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptjm(data: String): String {
        val bt = encrypt(data.toByteArray(), key.toByteArray())
        return Base64.encodeToString(bt, Base64.DEFAULT)
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     * 加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    @Throws(IOException::class, Exception::class)
    fun decryptjm(data: String?): String? {
        if (data == null)
            return null
        val buf = Base64.decode(data, Base64.DEFAULT)
        val bt = decrypt(buf, key.toByteArray())
        return String(bt)
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     * 加密键byte数组
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun encrypt(data: ByteArray, key: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()

        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(key)

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(DES)
        val securekey = keyFactory.generateSecret(dks)

        // Cipher对象实际完成加密操作
        val cipher = Cipher.getInstance(DES)

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr)

        return cipher.doFinal(data)
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     * 加密键byte数组
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun decrypt(data: ByteArray, key: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()

        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(key)

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(DES)
        val securekey = keyFactory.generateSecret(dks)

        // Cipher对象实际完成解密操作
        val cipher = Cipher.getInstance(DES)

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr)

        return cipher.doFinal(data)
    }

    /**
     * Description 获取字符串MD5值
     *
     * @param sourceStr
     */
    private fun MD5(sourceStr: String): String {
        var result = ""
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(sourceStr.toByteArray())
            val b = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0)
                    i += 256
                if (i < 16)
                    buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            result = buf.toString()
        } catch (e: NoSuchAlgorithmException) {
        }

        return result
    }


}
