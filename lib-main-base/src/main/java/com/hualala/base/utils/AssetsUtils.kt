package com.hualala.base.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: Assets获取的相关操作类
 */

object AssetsUtils {
    private val ENCODING = "UTF-8"
    /**
     * 从assets获取文件
     *
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getFileFromAssets(context: Context, fileName: String): InputStream {
        val am = context.assets
        return am.open(fileName)
    }

    /**
     * 从assets获取文本文件
     *
     * @param context
     * @param fileName
     * @return
     */
    fun getTextFromAssets(context: Context, fileName: String): String? {
        var result: String? = null
        try {
            val `in` = getFileFromAssets(context, fileName)
            val length = `in`.available()
            val buffer = ByteArray(length)
            `in`.read(buffer)
            result = String(buffer, Charset.forName(ENCODING))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * Parse a json file in the assets to ArrayList
     * 解析json文件成ArrayList，在assets中
     * @param context
     * @param jsonName
     * @return
     */
    fun <T> parseJsonToArrayList(context: Context, jsonName: String): ArrayList<T>? {
        val json = getTextFromAssets(context, jsonName)
        return JsonUtils.jsonToArrayList(json!!)
    }

    /**
     * Parse a json file in the assets to List
     * 解析json文件成List，在assets中
     * @param context
     * @param jsonName
     * @return
     */
    fun <T> parseJsonToList(context: Context, jsonName: String): List<T>? {
        val json = getTextFromAssets(context, jsonName)
        return JsonUtils.jsonToList(json!!)
    }

    /**
     * Parse a json file in the assets to Bean
     * 解析assets中的json文件
     * @param context
     * @param jsonName
     * @param clazz
     * @return
     */
    fun <T> parseJsonToObject(context: Context, jsonName: String, clazz: Class<T>): T {
        val json = getTextFromAssets(context, jsonName)
        return JsonUtils.jsonToObject(json!!, clazz)
    }
}
