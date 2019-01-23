package com.hualala.base.utils

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 对象操作
 */

object ObjectUtils {
    /**
     * Returns true if a and b are equal.
     * 如果a和b相等，返回TRUE
     *
     * @param a
     * @param b
     * @return
     */
    fun equals(a: Any?, b: Any?): Boolean {
        return a === b || if (a == null) b == null else a == b
    }
}
