package com.hualala.base.utils

import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author wlj
 * @date 2017/3/28
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description 带日志文件输入的，又可控开关的日志调试
 */
object FileLog {

    //modified by suxm
    private var MYLOG_SWITCH: Boolean? = false // 日志文件总开关
    private var MYLOG_WRITE_TO_FILE: Boolean? = false//日志写入文件开关
    private val MYLOG_TYPE = 'v'// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private val SDCARD_LOG_FILE_SAVE_DAYS = 0// sd卡中日志文件的最多保存天数
    private var sDirs: String? = null // 目录
    private val MYLOGFILEName = "Log.txt"// 本类输出的日志文件名称
    private val myLogSdf = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss")// 日志的输出格式
    private val logfile = SimpleDateFormat("yyyy-MM-dd")// 日志文件格式

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private val dateBefore: Date
        get() {
            val nowtime = Date()
            val now = Calendar.getInstance()
            now.time = nowtime
            now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS)
            return now.time
        }

    fun w(tag: String, msg: Any) { // 警告信息
        log(tag, msg.toString(), 'w')
    }

    fun e(tag: String, msg: Any) { // 错误信息
        log(tag, msg.toString(), 'e')
    }

    fun d(tag: String, msg: Any) {// 调试信息
        log(tag, msg.toString(), 'd')
    }

    fun i(tag: String, msg: Any) {//
        log(tag, msg.toString(), 'i')
    }

    fun v(tag: String, msg: Any) {
        log(tag, msg.toString(), 'v')
    }

    fun w(tag: String, text: String) {
        log(tag, text, 'w')
    }

    fun e(tag: String, text: String) {
        log(tag, text, 'e')
    }

    fun d(tag: String, text: String) {
        log(tag, text, 'd')
    }

    fun i(tag: String, text: String) {
        log(tag, text, 'i')
    }

    fun v(tag: String, text: String) {
        log(tag, text, 'v')
    }

    /**
     * 配置文件log
     * @param dirs 目录
     * @param isShowLog true表示写入
     */
    fun setConfig(dirs: String, isShowLog: Boolean) {
        sDirs = dirs
        MYLOG_SWITCH = isShowLog
        MYLOG_WRITE_TO_FILE = isShowLog
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     * @return void
     * @since v 1.0
     */
    private fun log(tag: String, msg: String, level: Char) {
        if (MYLOG_SWITCH!!) {
            if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg)
            } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.w(tag, msg)
            } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.d(tag, msg)
            } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.i(tag, msg)
            } else {
                Log.v(tag, msg)

            }
            if (MYLOG_WRITE_TO_FILE!!)
                writeLogtoFile(level.toString(), tag, msg)
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     */
    private fun writeLogtoFile(mylogtype: String, tag: String, text: String) {// 新建或打开日志文件
        val nowtime = Date()
        val needWriteFiel = logfile.format(nowtime)
        val needWriteMessage = (myLogSdf.format(nowtime) + "    " + mylogtype
                + "    " + tag + "    " + text)
        val dir = File(sDirs!!)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(sDirs, needWriteFiel + MYLOGFILEName)
        try {
            val filerWriter = FileWriter(file, true)//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            val bufWriter = BufferedWriter(filerWriter)
            bufWriter.write(needWriteMessage)
            bufWriter.newLine()
            bufWriter.close()
            filerWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * 删除制定的日志文件
     */
    fun delFile() {// 删除日志文件
        val needDelFiel = logfile.format(dateBefore)
        val file = File(sDirs, needDelFiel + MYLOGFILEName)
        if (file.exists()) {
            file.delete()
        }
    }

}