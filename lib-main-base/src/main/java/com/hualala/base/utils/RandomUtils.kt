package com.hualala.base.utils

import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 获取随机数操作类
 */

object RandomUtils {
    /**
     * Returns a pseudo-random uniformly distributed `int`.
     * 返回伪随机均匀分布
     *
     * @return
     */
    fun randomInt(): Int {
        val random = Random()
        return random.nextInt()
    }

    /**
     * Returns a pseudo-random uniformly distributed `int` in the half-open range [0, n).
     *
     * @param n
     * @return
     */
    fun randomInt(n: Int): Int {
        val random = Random()
        return random.nextInt(n)
    }

    /**
     * Returns a pseudo-random uniformly distributed `int` in the half-open range [min, max].
     *
     * @param min
     * @param max
     * @return
     */
    fun randomInt(min: Int, max: Int): Int {
        val random = Random()
        return random.nextInt(max) % (max - min + 1) + min
    }

    /**
     * Returns a pseudo-random uniformly distributed `int` in the half-open range [0, n).
     *
     * @param n
     * @return
     */
    fun randomInt2(n: Int): Int {
        return (System.currentTimeMillis() % n).toInt()
    }

    /**
     * Returns a pseudo-random uniformly distributed `int` in the half-open range [0, n).
     *
     * @param n
     * @return
     */
    fun randomInt3(n: Int): Int {
        return (Math.random() * 100).toInt()
    }

    /**
     * Returns a pseudo-random uniformly distributed `float` in the half-open range [0.0, 1.0).
     *
     * @return
     */
    fun randomFloat(): Float {
        val random = Random()
        return random.nextFloat()
    }

    /**
     * Returns a pseudo-random uniformly distributed `double` in the half-open range [0.0, 1.0).
     *
     * @return
     */
    fun randomDouble(): Double {
        val random = Random()
        return random.nextDouble()
    }

    /**
     * Returns a pseudo-random uniformly distributed `long`.
     *
     * @return
     */
    fun randomLong(): Long {
        val random = Random()
        return random.nextLong()
    }

    /**
     * Returns a pseudo-random uniformly distributed `boolean`.
     *
     * @return
     */
    fun randomBoolean(): Boolean {
        val random = Random()
        return random.nextBoolean()
    }

    /**
     * Returns a pseudo-random (approximately) normally distributed `double` with mean 0.0 and standard deviation
     * 1.0. This method uses the *polar method* of G. E. P. Box, M. E. Muller, and G. Marsaglia, as described by
     * Donald E. Knuth in *The Art of Computer Programming, Volume 2: Seminumerical Algorithms*, section 3.4.1,
     * subsection C, algorithm P.
     *
     * @return
     */
    fun randomGaussian(): Double {
        val random = Random()
        return random.nextGaussian()
    }

    /**
     * Fills `buf` with random bytes.
     *
     * @param buf
     */
    fun randomBytes(buf: ByteArray) {
        val random = Random()
        random.nextBytes(buf)
    }

    /**
     * Get a random string
     * 得到一个随机字符串
     *
     * @param source
     * @param length
     * @return
     */
    fun randomString(source: String, length: Int): String? {
        return if (StringUtils.isEmpty(source)) null else randomString(source.toCharArray(), length)
    }

    /**
     * Get a random string
     * 得到一个随机字符串
     *
     * @param sourceChar
     * @param length
     * @return
     */
    fun randomString(sourceChar: CharArray?, length: Int): String? {
        if (sourceChar == null || sourceChar.size == 0 || length < 0) {
            return null
        }
        val builder = StringBuilder(length)
        for (i in 0 until length) {
            builder.append(sourceChar[randomInt(sourceChar.size)])
        }
        return builder.toString()
    }

    /**
     * Shuffling a int array
     * 重新排序数组
     *
     * @param intArray
     * @return
     */
    fun shuffle(intArray: IntArray?): IntArray? {
        return if (intArray == null) {
            null
        } else shuffle(intArray, intArray.size)
    }

    /**
     * Shuffling a int array
     * 重新排序数组
     *
     * @param intArray
     * @param shuffleCount
     * @return
     */
    fun shuffle(intArray: IntArray?, shuffleCount: Int): IntArray? {
        val length: Int = intArray?.size ?: 0
        if (intArray == null || shuffleCount < 0 || length < shuffleCount) {
            return null
        }

        val out = IntArray(shuffleCount)
        for (i in 1..shuffleCount) {
            val random = randomInt(length - i)
            out[i - 1] = intArray[random]
            val temp = intArray[length - i]
            intArray[length - i] = intArray[random]
            intArray[random] = temp
        }
        return out
    }
}
