package com.hualala.base.utils

import java.io.*
import java.util.regex.Pattern

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 获取CPU信息
 */

object CpuUtils {
    /**
     * Get cpu max frequence, Unit KHZ.
     * 获得CPU最大频率，单位千赫。
     *
     * @return
     */
    val cpuMaxFreq: String
        get() {
            var result = "N/A"
            val cmd: ProcessBuilder
            try {
                val args = arrayOf("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq")
                cmd = ProcessBuilder(*args)
                val process = cmd.start()
                val `in` = process.inputStream
                val re = ByteArray(24)
                while (`in`.read(re) != -1) {
                    result = result + String(re)
                }
                `in`.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }

            return result.trim { it <= ' ' }
        }

    /**
     * Get cpu min frequence, Unit KHZ.
     * 获取CPU最小频率，单位千赫
     *
     * @return
     */
    val cpuMinFreq: String
        get() {
            var result = "N/A"
            val cmd: ProcessBuilder
            try {
                val args = arrayOf("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq")
                cmd = ProcessBuilder(*args)
                val process = cmd.start()
                val `in` = process.inputStream
                val re = ByteArray(24)
                while (`in`.read(re) != -1) {
                    result = result + String(re)
                }
                `in`.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }

            return result.trim { it <= ' ' }
        }

    /**
     * Get cpu current frequence, Unit KHZ.
     * 获取CPU电流频率，单位千赫。
     *
     * @return
     */
    val cpuCurFreq: String
        get() {
            var result = "N/A"
            try {
                val fr = FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")
                val br = BufferedReader(fr)
                val text = br.readLine()
                result = text.trim { it <= ' ' }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return result
        }

    /**
     * Get cpu name
     * 获得CPU的名字
     *
     * @return
     */
    val cpuName: String?
        get() {
            try {
                val fr = FileReader("/proc/cpuinfo")
                val br = BufferedReader(fr)
                val text = br.readLine()
                val array = text.split(":\\s+".toRegex(), 2).toTypedArray()
                for (i in array.indices) {
                }
                return array[1]
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

    /**
     * Get cpu cores
     * 获取CPU核心数
     *
     * @return
     */
    val cpuCores: Int
        get() {
            class CpuFilter : FileFilter {
                override fun accept(pathname: File): Boolean {
                    return if (Pattern.matches("cpu[0-9]", pathname.name)) {
                        true
                    } else false
                }
            }

            try {
                val dir = File("/sys/devices/system/cpu/")
                val files = dir.listFiles(CpuFilter())
                return files.size
            } catch (e: Exception) {
                e.printStackTrace()
                return 1
            }

        }
}
