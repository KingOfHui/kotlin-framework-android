/*
 * Copyright 2016 北京博瑞彤芸文化传播股份有限公司
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.hualala.base.utils

import android.text.TextUtils

import java.io.File
import java.io.IOException
import java.math.BigDecimal


/**
 * @author wlj
 * @date 2018/09/01
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description 获取文件大小的类
 */
class FileSize(private val file: File) {

    private var longSize: Long = 0

    private fun getFileSize(file: File?) {

        if (file == null || !file.exists()) {
            return
        }

        if (file.isFile) {
            this.longSize += file.length()
            return
        }

        val childArray = file.listFiles()
        if (childArray == null || childArray.size == 0) {
            return
        }

        for (child in childArray) {
            getFileSize(child)
        }

    }

    @Throws(RuntimeException::class)
    override fun toString(): String {
        try {
            try {
                getFileSize(file)
            } catch (e: RuntimeException) {
                return ""
            }

            return convertSizeToString(this.longSize)

        } catch (ex: IOException) {
            ex.printStackTrace()
            throw RuntimeException(ex.message)
        }

    }

    /**
     * 获取文件的大小单位是byte
     * @return 返回文件的大小
     * @throws RuntimeException
     */
    @Throws(RuntimeException::class)
    fun getLongSize(): Long {
        try {

            getFileSize(file)
            return longSize
        } catch (ex: IOException) {
            ex.printStackTrace()
            throw RuntimeException(ex.message)
        }

    }

    companion object {

        val SIZE_BT = 1024L

        val SIZE_KB = SIZE_BT * 1024L

        val SIZE_MB = SIZE_KB * 1024L

        val SIZE_GB = SIZE_MB * 1024L

        val SIZE_TB = SIZE_GB * 1024L

        val SACLE = 2

        /**
         * 格式化输出文件大小
         * @param fileSize
         * @return
         */
        fun convertSizeToString(fileSize: Long): String {
            if (fileSize >= 0 && fileSize < SIZE_BT) {
                return fileSize.toString() + "B"
            } else if (fileSize >= SIZE_BT && fileSize < SIZE_KB) {
                return (fileSize / SIZE_BT).toString() + "KB"
            } else if (fileSize >= SIZE_KB && fileSize < SIZE_MB) {
                return (fileSize / SIZE_KB).toString() + "MB"
            } else if (fileSize >= SIZE_MB && fileSize < SIZE_GB) {
                val longs = BigDecimal(java.lang.Double.valueOf(fileSize.toString() + "")!!.toString())
                val sizeMB = BigDecimal(java.lang.Double.valueOf(SIZE_MB.toString() + "")!!.toString())
                val result = longs.divide(sizeMB, SACLE, BigDecimal.ROUND_HALF_UP).toString()
                // double result=this.longSize/(double)SIZE_MB;
                return result + "GB"
            } else {
                val longs = BigDecimal(java.lang.Double.valueOf(fileSize.toString() + "")!!.toString())
                val sizeMB = BigDecimal(java.lang.Double.valueOf(SIZE_GB.toString() + "")!!.toString())
                val result = longs.divide(sizeMB, SACLE, BigDecimal.ROUND_HALF_UP).toString()
                return result + "TB"
            }
        }

        /**
         * MB转KB
         * @param fileSize
         * @return
         */
        fun getKBSize(fileSize: String): Double {
            var fileSize = fileSize
            if (TextUtils.isEmpty(fileSize)) {
                return 0.0
            }
            fileSize = fileSize.toUpperCase()
            var index: Int = fileSize.indexOf("MB")
            if (index > 0) {
                val str = fileSize.substring(0, index).trim { it <= ' ' }
                return string2double(str) * 1024
            }
            index = fileSize.indexOf("KB")
            if (index > 0) {
                val str = fileSize.substring(0, index).trim { it <= ' ' }
                return string2double(str)
            }
            return 0.0
        }

        fun string2double(doubleStr: String): Double {
            try {
                return java.lang.Double.valueOf(doubleStr)!!
            } catch (e: Exception) {
            }

            return 0.0
        }
    }

}
