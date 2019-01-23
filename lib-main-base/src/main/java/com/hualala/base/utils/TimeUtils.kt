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
 */

package com.hualala.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author wlj
 * @date 2017/3/28
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description 时间工具类
 */
class TimeUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         *
         * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
         * 格式的意义如下： 日期和时间模式 <br></br>
         *
         * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
         * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
         * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
         *
         * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br></br>
         * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
        presentation, and examples.">
         * <tr>
         * <th align="left">字母</th>
         * <th align="left">日期或时间元素</th>
         * <th align="left">表示</th>
         * <th align="left">示例</th>
        </tr> *
         * <tr>
         * <td>`G`</td>
         * <td>Era 标志符</td>
         * <td>Text</td>
         * <td>`AD`</td>
        </tr> *
         * <tr>
         * <td>`y` </td>
         * <td>年 </td>
         * <td>Year </td>
         * <td>`1996`; `96` </td>
        </tr> *
         * <tr>
         * <td>`M` </td>
         * <td>年中的月份 </td>
         * <td>Month </td>
         * <td>`July`; `Jul`; `07` </td>
        </tr> *
         * <tr>
         * <td>`w` </td>
         * <td>年中的周数 </td>
         * <td>Number </td>
         * <td>`27` </td>
        </tr> *
         * <tr>
         * <td>`W` </td>
         * <td>月份中的周数 </td>
         * <td>Number </td>
         * <td>`2` </td>
        </tr> *
         * <tr>
         * <td>`D` </td>
         * <td>年中的天数 </td>
         * <td>Number </td>
         * <td>`189` </td>
        </tr> *
         * <tr>
         * <td>`d` </td>
         * <td>月份中的天数 </td>
         * <td>Number </td>
         * <td>`10` </td>
        </tr> *
         * <tr>
         * <td>`F` </td>
         * <td>月份中的星期 </td>
         * <td>Number </td>
         * <td>`2` </td>
        </tr> *
         * <tr>
         * <td>`E` </td>
         * <td>星期中的天数 </td>
         * <td>Text </td>
         * <td>`Tuesday`; `Tue` </td>
        </tr> *
         * <tr>
         * <td>`a` </td>
         * <td>Am/pm 标记 </td>
         * <td>Text </td>
         * <td>`PM` </td>
        </tr> *
         * <tr>
         * <td>`H` </td>
         * <td>一天中的小时数（0-23） </td>
         * <td>Number </td>
         * <td>`0` </td>
        </tr> *
         * <tr>
         * <td>`k` </td>
         * <td>一天中的小时数（1-24） </td>
         * <td>Number </td>
         * <td>`24` </td>
        </tr> *
         * <tr>
         * <td>`K` </td>
         * <td>am/pm 中的小时数（0-11） </td>
         * <td>Number </td>
         * <td>`0` </td>
        </tr> *
         * <tr>
         * <td>`h` </td>
         * <td>am/pm 中的小时数（1-12） </td>
         * <td>Number </td>
         * <td>`12` </td>
        </tr> *
         * <tr>
         * <td>`m` </td>
         * <td>小时中的分钟数 </td>
         * <td>Number </td>
         * <td>`30` </td>
        </tr> *
         * <tr>
         * <td>`s` </td>
         * <td>分钟中的秒数 </td>
         * <td>Number </td>
         * <td>`55` </td>
        </tr> *
         * <tr>
         * <td>`S` </td>
         * <td>毫秒数 </td>
         * <td>Number </td>
         * <td>`978` </td>
        </tr> *
         * <tr>
         * <td>`z` </td>
         * <td>时区 </td>
         * <td>General time zone </td>
         * <td>`Pacific Standard Time`; `PST`; `GMT-08:00` </td>
        </tr> *
         * <tr>
         * <td>`Z` </td>
         * <td>时区 </td>
         * <td>RFC 822 time zone </td>
         * <td>`-0800` </td>
        </tr> *
        </table> *
         * <pre>
         * HH:mm    15:44
         * h:mm a    3:44 下午
         * HH:mm z    15:44 CST
         * HH:mm Z    15:44 +0800
         * HH:mm zzzz    15:44 中国标准时间
         * HH:mm:ss    15:44:40
         * yyyy-MM-dd    2016-08-12
         * yyyy-MM-dd HH:mm    2016-08-12 15:44
         * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
         * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
         * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
         * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
         * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
         * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
         * K:mm a    3:44 下午
         * EEE, MMM d, ''yy    星期五, 八月 12, '16
         * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
         * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
         * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
         * yyMMddHHmmssZ    160812154440+0800
         * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
         * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
        </pre> *
         */
        val DEFAULT_SDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val CN_MM_DD_SDF = SimpleDateFormat("MM月dd日", Locale.getDefault())
        val CN_M_D_SDF = SimpleDateFormat("M月d日", Locale.getDefault())
        val EN_YYYY_MM_DD_SDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val CN_YYYY_MM_DD_SDF = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
        val CN_YYYY_M_D_SDF = SimpleDateFormat("yyyy年M月d日", Locale.getDefault())
        val EN_M_D_SDF = SimpleDateFormat("M/d", Locale.getDefault())
        val EN_MM_DD_SDF = SimpleDateFormat("MM-dd", Locale.getDefault())
        val EN_HH_mm = SimpleDateFormat("HH:mm", Locale.getDefault())

        /**
         * 将时间戳转为时间字符串
         *
         * 格式为用户自定义
         *
         * @param milliseconds 毫秒时间戳
         * @param format       时间格式
         * @return 时间字符串
         */
        @JvmOverloads
        fun milliseconds2String(milliseconds: Long, format: SimpleDateFormat = DEFAULT_SDF): String {
            return format.format(Date(milliseconds))
        }

        /**
         * 将时间字符串转为时间戳
         *
         * 格式为用户自定义
         *
         * @param time   时间字符串
         * @param format 时间格式
         * @return 毫秒时间戳
         */
        @JvmOverloads
        fun string2Milliseconds(time: String, format: SimpleDateFormat = DEFAULT_SDF): Long {
            try {
                return format.parse(time).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return -1
        }


        /**
         * 将时间字符串转为Date类型
         *
         * 格式为用户自定义
         *
         * @param time   时间字符串
         * @param format 时间格式
         * @return Date类型
         */
        @JvmOverloads
        fun string2Date(time: String, format: SimpleDateFormat = DEFAULT_SDF): Date {
            return Date(string2Milliseconds(time, format))
        }

        /**
         * 将Date类型转为时间字符串
         *
         * 格式为用户自定义
         *
         * @param time   Date类型时间
         * @param format 时间格式
         * @return 时间字符串
         */
        @JvmOverloads
        fun date2String(time: Date, format: SimpleDateFormat = DEFAULT_SDF): String {
            return format.format(time)
        }

        /**
         * 将Date类型转为时间戳
         *
         * @param time Date类型时间
         * @return 毫秒时间戳
         */
        fun date2Milliseconds(time: Date): Long {
            return time.time
        }

        /**
         * 将时间戳转为Date类型
         *
         * @param milliseconds 毫秒时间戳
         * @return Date类型时间
         */
        fun milliseconds2Date(milliseconds: Long): Date {
            return Date(milliseconds)
        }


        /**
         * 获取两个毫秒值之间的时间差(天数)
         *
         * @param time1
         * @param time2     一般time2写较大的值
         * @return
         */
        fun getIntervalTime(time1: Long, time2: Long): Int {
            return ((time2 - time1) / (1000 * 3600 * 24)).toInt()
        }

        /**
         * 获取当前时间
         *
         * @return 毫秒时间戳
         */
        val curTimeMills: Long
            get() = System.currentTimeMillis()

        /**
         * 获取当前时间
         *
         * 格式为yyyy-MM-dd HH:mm:ss
         *
         * @return 时间字符串
         */
        val curTimeString: String
            get() = date2String(Date())

        /**
         * 获取当前时间
         *
         * 格式为用户自定义
         *
         * @param format 时间格式
         * @return 时间字符串
         */
        fun getCurTimeString(format: SimpleDateFormat): String {
            return date2String(Date(), format)
        }

        /**
         * 获取当前时间
         *
         * Date类型
         *
         * @return Date类型时间
         */
        val curTimeDate: Date
            get() = Date()


        /**
         * 判断闰年
         *
         * @param year 年份
         * @return `true`: 闰年<br></br>`false`: 平年
         */
        fun isLeapYear(year: Int): Boolean {
            return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
        }

        /**
         * 获取当前日期是星期几<br></br>
         *
         * @return 当前日期是星期几
         */
        val weekOfDate: String
            get() {
                val weekDays = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
                val cal = Calendar.getInstance()
                cal.time = Date()

                var w = cal.get(Calendar.DAY_OF_WEEK) - 1
                if (w < 0)
                    w = 0

                return weekDays[w]
            }

        /**
         * 获取当前日期是星期几<br></br>
         *
         * @return 当前日期是星期几
         */
        val week: String
            get() {
                val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
                val cal = Calendar.getInstance()
                cal.time = Date()

                var w = cal.get(Calendar.DAY_OF_WEEK) - 1
                if (w < 0)
                    w = 0

                return weekDays[w]
            }

        /**
         * 获取当前日期的下一天是星期几<br></br>
         *
         * @return 当前日期是星期几
         */
        val weekOfNextDate: String
            get() {
                val weekDays = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
                val cal = Calendar.getInstance()
                cal.time = Date()

                var w = cal.get(Calendar.DAY_OF_WEEK)
                if (w == 0)
                    w = 1
                if (w > 6)
                    w = 0
                return weekDays[w]
            }


        /**
         * 获取桌面时钟的时间
         */
        val deskClockTime: String
            get() {
                val c = Calendar.getInstance()
                c.timeZone = TimeZone.getTimeZone("GMT+8:00")
                var mHuor = c.get(Calendar.HOUR_OF_DAY).toString()
                var imnute = c.get(Calendar.MINUTE).toString()
                if (imnute.length < 2) {
                    imnute = "0$imnute"
                }
                if (mHuor.length < 2) {
                    mHuor = "0$mHuor"
                }
                return "$mHuor:$imnute"
            }

        /**
         * 获取当前月份是几月
         */
        /**
         * 月份从0开始计算
         */
        val curMonth: String
            get() {

                val c = Calendar.getInstance()

                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH) + 1
                var monthString: String? = null

                if (month < 10) {

                    monthString = 0.toString() + "" + month
                } else {

                    monthString = "" + month
                }
                return year.toString() + "-" + monthString
            }

        /**
         * 获取两个月份差值
         *
         * @param month1
         * @param month2
         * @return
         */
        @Throws(ParseException::class)
        fun getDistanceOfTwoMonth(month1: String, month2: String): Int {

            val sdf = SimpleDateFormat("yyyy-MM")
            val bef = Calendar.getInstance()
            val aft = Calendar.getInstance()
            bef.time = sdf.parse(month1)
            aft.time = sdf.parse(month2)
            val result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH)
            val month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12

            return Math.abs(month + result)
        }

        /**
         * 判断旧日期和新日期【新旧根据获取先后而定】的先后关系，如果新日期大于旧日期则返回true
         */
        fun compareDates(oldYear: Int, oldMonth: Int, oldDay: Int,
                         newYear: Int, newMonth: Int, newDay: Int): Boolean {
            if (newYear != oldYear)
                return newYear >= oldYear

            if (newMonth != oldMonth)
                return newMonth >= oldMonth

            return if (newDay != oldDay) newDay >= oldDay else false

        }

        /**
         * 获取当前时
         */
        val hour: Int
            get() {
                val c = Calendar.getInstance()
                c.timeZone = TimeZone.getTimeZone("GMT+8:00")
                return c.get(Calendar.HOUR_OF_DAY)
            }

        /**
         * 获取指定时间的小时
         */
        fun getHour(timeMills: Long): Int {
            val c = Calendar.getInstance()
            c.timeInMillis = timeMills
            c.timeZone = TimeZone.getTimeZone("GMT+8:00")
            return c.get(Calendar.HOUR_OF_DAY)
        }


        /**
         * 获取当前月的天数
         *
         * @return
         */
        val curMonthLength: Int
            get() {
                val a = Calendar.getInstance()
                a.set(Calendar.DATE, 1)
                a.roll(Calendar.DATE, -1)
                return a.get(Calendar.DATE)
            }

        /**
         * 获取当前日期
         *
         * @return
         */
        val curDayOFMonth: String
            get() {
                val c = Calendar.getInstance()
                return c.get(Calendar.DAY_OF_MONTH).toString()
            }

        /**
         * 获取当前年份
         * @return
         */
        val curYear: String
            get() {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                return year.toString() + ""
            }
    }
}
/**
 * 将时间戳转为时间字符串
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param milliseconds 毫秒时间戳
 * @return 时间字符串
 */
/**
 * 将时间字符串转为时间戳
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return 毫秒时间戳
 */
/**
 * 将时间字符串转为Date类型
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return Date类型
 */
/**
 * 将Date类型转为时间字符串
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time Date类型时间
 * @return 时间字符串
 */