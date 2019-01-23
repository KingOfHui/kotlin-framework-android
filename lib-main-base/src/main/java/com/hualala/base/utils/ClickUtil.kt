package com.hualala.base.utils


/**
 * @author wlj
 * @date 2017/3/28
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description 点击处理方法，防止二次点击
 */
object ClickUtil {

    // 上次点击时间
    private var sLastTime: Long = 0

    /**
     * 判断此次点击是否响应
     *
     * @return 响应则返回true，否则返回false
     */
    val isClick: Boolean
        get() {

            val time = TimeUtils.curTimeMills
            if (sLastTime > time || time - sLastTime > 500) {
                synchronized(ClickUtil::class.java) {
                    if (sLastTime > time || time - sLastTime > 500) {
                        sLastTime = time
                        return true
                    }
                    return false
                }
            }
            return false
        }

}
