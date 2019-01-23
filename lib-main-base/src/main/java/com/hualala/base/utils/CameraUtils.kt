package com.hualala.base.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

import java.io.File

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 打开相机相关类
 */

class CameraUtils {

    /**
     * 打开相机
     *
     * @param activity
     * @param path
     * @param fileName
     */
    @JvmOverloads
    fun openCamera(activity: Activity, path: String, fileName: String = "IMG_" + System.currentTimeMillis() + ".jpg") {
        FileUtils.makeDirs(path)
        val cameraFile = File(path, fileName)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile))
        activity.startActivityForResult(intent, CAMERA_REQ_CODE)
    }

    companion object {
        val CAMERA_REQ_CODE = 0x0011
    }

}
/**
 * 打开相机
 *
 * @param activity
 * @param path
 */
