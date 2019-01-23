package com.hualala.base.utils

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: Map相关操作
 */

object MapUtils {
    private val DELIMITER = ","

    /**
     * Judge whether a map is null or size is 0
     * 判断是否为空或者为零
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
    </V></K> */
    fun <K, V> isEmpty(map: Map<K, V>?): Boolean {
        return map == null || map.size == 0
    }

    /**
     * Map遍历
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
    </V></K> */
    fun <K, V> traverseMap(map: Map<K, V>): String? {
        if (!isEmpty(map)) {
            val len = map.size
            val builder = StringBuilder(len)
            var i = 0
            for (entry in map.entries) {
                if (entry == null) {
                    continue
                }
                builder.append(entry.key.toString() + ":" + entry.value.toString())
                i++
                if (i < len) {
                    builder.append(DELIMITER)
                }
            }
            return builder.toString()
        }
        return null
    }

    /**
     * 根据值获取键
     *
     * @param map
     * @param value
     * @param <K>
     * @param <V>
     * @return
    </V></K> */
    fun <K, V> getKeyByValue(map: Map<K, V>, value: V): K? {
        if (isEmpty(map)) {
            return null
        }
        for ((key, value1) in map) {
            if (ObjectUtils.equals(value1, value)) {
                return key
            }
        }
        return null
    }

}
