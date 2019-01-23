package com.hualala.base.data.net


import com.hualala.base.common.BaseConstant
import okhttp3.OkHttpClient
import java.io.ByteArrayInputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.*


class SSLSocketFactoryMaker : OkHttpClient() {
    companion object {

        val keyManagers: Array<KeyManager>?
            get() {
                try {
                    val keyManagerKeyStore = keyManagerKeyStore
                    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
                    keyManagerFactory.init(keyManagerKeyStore, p12Password.toCharArray())
                    return keyManagerFactory.keyManagers
                } catch (e: Exception) {

                }

                return null
            }

        val trustManagers: Array<TrustManager>?
            get() {
                try {
                    val trustManagerKeyStore = trustManagerKeyStore
                    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                    trustManagerFactory.init(trustManagerKeyStore)
                    return trustManagerFactory.trustManagers
                } catch (e: Exception) {

                }

                return null
            }

        val sslSocketFactory: SSLSocketFactory?
            get() {
                try {
                    val keyManagers = null
                    val trustManagers = trustManagers

                    val sslContext = SSLContext.getInstance("TLS")
                    sslContext.init(
                            null,
                            trustManagers,
                            SecureRandom())

                    return sslContext.socketFactory
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return null
            }

        fun hex2byte(str: String?): ByteArray? { // 字符串转二进制
            var str: String? = str ?: return null
            str = str!!.trim { it <= ' ' }
            val len = str.length
            if (len == 0 || len % 2 == 1)
                return null

            val b = ByteArray(len / 2)
            try {
                var i = 0
                while (i < str.length) {
                    b[i / 2] = Integer.decode("0x" + str.substring(i, i + 2))!!.toInt().toByte()
                    i += 2
                }
                return b
            } catch (e: Exception) {
                return null
            }

        }

        private val keyManagerKeyStore: KeyStore?
            get() {
                try {
                    val clientKeyStore = KeyStore.getInstance("PKCS12")
                    val data = hex2byte(BaseConstant.CLIENT_CER)
                    val inputStream = ByteArrayInputStream(data)
                    clientKeyStore.load(inputStream, p12Password.toCharArray())
                    inputStream.close()
                    return clientKeyStore
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return null
            }

        private val trustManagerKeyStore: KeyStore?
            get() {
                try {
                    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
                    keyStore.load(null)
                    val data = BaseConstant.SERER_CER.toByteArray()
                    val inputStream = ByteArrayInputStream(data)
                    val certificateFactory = CertificateFactory.getInstance("X.509")
                    keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(inputStream))
                    inputStream.close()
                    return keyStore
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return null
            }

        private val p12Password = "&ofO9ouh"
    }
}
