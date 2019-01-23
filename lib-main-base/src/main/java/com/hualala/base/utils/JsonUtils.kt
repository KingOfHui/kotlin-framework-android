package com.hualala.base.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: json的转换
 */

object JsonUtils {
    /**
     * Convert object, list, ... to Json
     * 转为json格式输出
     * @param obj
     * @return
     */
    fun toJson(obj: Any): String {
        val gson = Gson()
        return gson.toJson(obj)
    }

    /**
     * Convert a json string to Object
     * 将json转为object对象
     * @param json
     * @param clazz
     * @return
     */
    fun <T> jsonToObject(json: String, clazz: Class<T>): T {
        val gson = Gson()
        return gson.fromJson(json, clazz)
    }

    /**
     * Convert a json string to List
     * 将json转为list集合
     * @param json
     * @return
     */
    fun <T> jsonToList(json: String): List<T>? {
        val gson = Gson()
        return gson.fromJson<List<T>>(json, object : TypeToken<List<T>>() {

        }.type)
    }

    /**
     * Convert a json string to ArrayList
     * 将json转为ArrayList
     * @param json
     * @return
     */
    fun <T> jsonToArrayList(json: String): ArrayList<T>? {
        val gson = Gson()
        return gson.fromJson<ArrayList<T>>(json, object : TypeToken<ArrayList<T>>() {

        }.type)
    }

    /**
     * Convert a json string to Map
     * 将json转为map
     * @param json
     * @return
     */
    fun <K, V> jsonToMap(json: String): Map<K, V>? {
        val gson = Gson()
        return gson.fromJson<Map<K, V>>(json, object : TypeToken<Map<K, V>>() {

        }.type)
    }

    /**
     * Convert a json string to Generic<T>
     * 将json转为泛型
     * @param json
     * @param <T>
     * @return
    </T></T> */
    fun <T> jsonToGeneric(json: String, token: TypeToken<T>): T? {
        val gson = Gson()
        return gson.fromJson<T>(json, token.type)
    }
}
