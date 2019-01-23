package com.hualala.base.utils

import java.io.Closeable
import java.io.IOException

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: io操作
 */

object IOUtils {
    /**
     * Close closeable object
     * 关闭可以关闭的对象
     *
     * @param closeable
     */
    fun close(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (e: IOException) {
                LogUtils.d("IOUtils", e.toString())
            }

        }
    }

}
