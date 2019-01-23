/*
 * Copyright 2015-2016 北京博瑞彤芸文化传播股份有限公司
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

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * @author wlj
 * @date 2018/09/01
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description 键盘工具类
 */
object KeyboardUtil {

    /**
     * 隐藏键盘
     */
    fun hideSoftInput(acitivity: Activity) {
        val imm = acitivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(acitivity.window.decorView.applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 显示键盘
     */
    fun showSoftInput(et: EditText) {
        et.requestFocus()
        val imm = et.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    /**
     * 展示键盘并选中最后一个
     */
    @JvmOverloads
    fun showSoftInputSelect(et: EditText, delayMillis: Long = 300) {
        et.postDelayed({
            showSoftInput(et)
            et.setSelection(et.text.length)
        }, delayMillis)
    }

}

