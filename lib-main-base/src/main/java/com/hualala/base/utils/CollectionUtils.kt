package com.hualala.base.utils

import android.text.TextUtils
import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 集合操作
 */

object CollectionUtils {
    private val DELIMITER = ","

    /**
     * 判断集合是否为空
     *
     * @param c
     * @param <V>
     * @return
    </V> */
    fun <V> isEmpty(c: Collection<V>?): Boolean {
        return c == null || c.size == 0
    }

    /**
     * Join collection to string, separator is [.DELIMITER]
     *
     * @param tokens
     * @return
     */
    fun join(tokens: Iterable<*>?): String {
        return if (tokens == null) "" else TextUtils.join(DELIMITER, tokens)
    }

    /**
     * 将数组转为list
     *
     * @param array
     * @param <T>
     * @return
    </T> */
    fun <T> arrayToList(vararg array: T): List<T> {
        return Arrays.asList(*array)
    }

    /**
     * 将数组转为set集合
     *
     * @param array
     * @param <T>
     * @return
    </T> */
    fun <T> arrayToSet(vararg array: T): Set<T> {
        return HashSet(arrayToList(*array))
    }

    /**
     * 集合转为数组
     *
     * @param c
     * @return
     */
    fun listToArray(c: Collection<*>): Array<Any>? {
        return if (!isEmpty(c)) {
            c.toTypedArray() as? Array<Any>?
        } else null
    }

    /**
     * list转为set
     *
     * @param list
     * @param <T>
     * @return
    </T> */
    fun <T> listToSet(list: List<T>): Set<T> {
        return HashSet(list)
    }

    /**
     * Convert set to list
     * set转为list
     *
     * @param set
     * @param <T>
     * @return
    </T> */
    fun <T> setToList(set: Set<T>): List<T> {
        return ArrayList(set)
    }

    /**
     * Traverse collection
     * 遍历集合
     *
     * @param c
     * @param <T>
     * @return
    </T> */
    fun <T> traverseCollection(c: Collection<T>): String? {
        if (!isEmpty(c)) {
            val len = c.size
            val builder = StringBuilder(len)
            var i = 0
            for (t in c) {
                if (t == null) {
                    continue
                }
                builder.append(t.toString())
                i++
                if (i < len) {
                    builder.append(DELIMITER)
                }
            }
            return builder.toString()
        }
        return null
    }

}
