package com.hualala.base.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresPermission
import android.telephony.TelephonyManager
import java.util.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 获取设备信息
 */

object DeviceUtils {

    /**
     * Get bluetooth state
     * 获取蓝牙状态
     *
     * @return STATE_OFF, STATE_TURNING_OFF, STATE_ON, STATE_TURNING_ON, NONE
     * @throws Exception
     */
    val bluetoothState: Int?
        @RequiresPermission(Manifest.permission.BLUETOOTH)
        get() {
            val bluetoothAdapter = BluetoothAdapter
                    .getDefaultAdapter()
            return bluetoothAdapter?.state
        }

    /**
     * Judge bluetooth is open
     * 判断蓝牙是否打开
     *
     * @return true:open, false:close.
     */
    val isBluetoothOpen: Boolean
        get() {
            val bluetoothStateCode = bluetoothState ?: return false
            return if (bluetoothStateCode == BluetoothAdapter.STATE_ON || bluetoothStateCode == BluetoothAdapter.STATE_TURNING_ON)
                true
            else
                false
        }

    @SuppressLint("MissingPermission")
            /**
     * 获取UUID
     * @param context
     * @return
     */
    fun getUUID(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val tmDevice: String
        val tmSerial: String
        val tmPhone: String
        val androidId: String
        tmDevice = "" + tm.deviceId
        tmSerial = "" + tm.simSerialNumber
        androidId = "" + Settings.Secure.getString(context.contentResolver,
                Settings.Secure.ANDROID_ID)

        val deviceUuid = UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())

        return deviceUuid.toString()
    }

    /**
     * Get screen brightness mode，must declare the
     * 获取屏幕亮度模式，必须声明
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param context
     * @return
     */
    fun getScreenBrightnessMode(context: Context): Int {
        return Settings.System.getInt(context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
    }

    /**
     * Judge screen brightness mode is auto mode，must declare the
     * 判断屏幕亮度模式为自动模式，必须声明
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param context
     * @return true:auto;false: manual;default:true
     */
    fun isScreenBrightnessModeAuto(context: Context): Boolean {
        return if (getScreenBrightnessMode(context) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
            true
        else
            false
    }

    /**
     * Set screen brightness mode，must declare the
     * 设置屏幕亮度模式，必须声明
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param context
     * @param auto
     * @return
     */
    fun setScreenBrightnessMode(context: Context, auto: Boolean): Boolean {
        var result = true
        if (isScreenBrightnessModeAuto(context) != auto) {
            result = Settings.System.putInt(context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    if (auto)
                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                    else
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        }
        return result
    }

    /**
     * Get screen brightness, must declare the
     * 获得屏幕亮度，必须声明
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param context
     * @return brightness:0-255; default:255
     */
    fun getScreenBrightness(context: Context): Int {
        return Settings.System.getInt(context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, 255)
    }

    /**
     * Set screen brightness, cannot change window brightness.must declare the
     * 屏幕亮度设置
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param context
     * @param screenBrightness 0-255
     * @return
     */
    fun setScreenBrightness(context: Context,
                            screenBrightness: Int): Boolean {
        var brightness = screenBrightness
        if (screenBrightness < 1) {
            brightness = 1
        } else if (screenBrightness > 255) {
            brightness = screenBrightness % 255
            if (brightness == 0) {
                brightness = 255
            }
        }
        return Settings.System.putInt(context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, brightness)
    }

    /**
     * Set window brightness, cannot change system brightness.
     * 设置窗口亮度，不能改变系统亮度
     *
     * @param activity
     * @param screenBrightness 0-255
     */
    fun setWindowBrightness(activity: Activity,
                            screenBrightness: Float) {
        var brightness = screenBrightness
        if (screenBrightness < 1) {
            brightness = 1f
        } else if (screenBrightness > 255) {
            brightness = screenBrightness % 255
            if (brightness == 0f) {
                brightness = 255f
            }
        }
        val window = activity.window
        val localLayoutParams = window.attributes
        localLayoutParams.screenBrightness = brightness / 255.0f
        window.attributes = localLayoutParams
    }

    /**
     * Set screen brightness and change system brightness, must declare the
     * 设置屏幕亮度并改变系统亮度，必须声明
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param activity
     * @param screenBrightness 0-255
     * @return
     */
    fun setScreenBrightnessAndApply(activity: Activity,
                                    screenBrightness: Int): Boolean {
        val result = setScreenBrightness(activity, screenBrightness)
        if (result) {
            setWindowBrightness(activity, screenBrightness.toFloat())
        }
        return result
    }

    /**
     * Get screen dormant time, must declare the
     * 获得屏幕休眠时间，必须声明
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param context
     * @return dormantTime:ms, default:30s
     */
    fun getScreenDormantTime(context: Context): Int {
        return Settings.System.getInt(context.contentResolver,
                Settings.System.SCREEN_OFF_TIMEOUT, 30000)
    }

    /**
     * Set screen dormant time by millis, must declare the
     * 屏幕休眠时间等信息，必须申报
     * [Manifest.permission.WRITE_SETTINGS] permission in its manifest.
     *
     * @param context
     * @return
     */
    fun setScreenDormantTime(context: Context, millis: Int): Boolean {
        return Settings.System.putInt(context.contentResolver,
                Settings.System.SCREEN_OFF_TIMEOUT, millis)
    }

    /**
     * Get airplane mode, must declare the
     * 获取飞行模式，必须申报
     * [Manifest.permission.WRITE_APN_SETTINGS] permission in its manifest.
     *
     * @param context
     * @return 1:open, 0:close, default:close
     */
    fun getAirplaneMode(context: Context): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.getInt(context.contentResolver,
                    Settings.System.AIRPLANE_MODE_ON, 0)
        } else {
            Settings.Global.getInt(context.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON, 0)
        }
    }

    /**
     * Judge whether airplane is open, must declare the
     * 判断飞行模式是否打开，必须声明
     * [Manifest.permission.WRITE_APN_SETTINGS] permission in its manifest.
     *
     * @param context
     * @return true:open, false:close, default:close
     */
    fun isAirplaneModeOpen(context: Context): Boolean {
        return if (getAirplaneMode(context) == 1) true else false
    }

    /**
     * Set airplane mode, must declare the
     * 设置飞行模式，必须声明
     * [Manifest.permission.WRITE_APN_SETTINGS] permission in its manifest.
     *
     * @param context
     * @param enable
     * @return
     */
    fun setAirplaneMode(context: Context, enable: Boolean): Boolean {
        var result = true
        if (isAirplaneModeOpen(context) != enable) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                result = Settings.System.putInt(context.contentResolver,
                        Settings.System.AIRPLANE_MODE_ON, if (enable) 1 else 0)
            } else {
                result = Settings.Global.putInt(context.contentResolver,
                        Settings.Global.AIRPLANE_MODE_ON, if (enable) 1 else 0)
            }
            context.sendBroadcast(Intent(
                    Intent.ACTION_AIRPLANE_MODE_CHANGED))
        }
        return result
    }

    /**
     * Set bluetooth
     * 设置蓝牙
     *
     * @param enable
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    @Throws(Exception::class)
    fun setBluetooth(enable: Boolean) {
        if (isBluetoothOpen != enable) {
            if (enable) {
                BluetoothAdapter.getDefaultAdapter().enable()
            } else {
                BluetoothAdapter.getDefaultAdapter().disable()
            }
        }
    }

    /**
     * Get media volume
     * 获取音量
     *
     * @param context
     * @return volume:0-15
     */
    fun getMediaVolume(context: Context): Int {
        return (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager).getStreamVolume(AudioManager
                .STREAM_MUSIC)
    }

    /**
     * Set media volume
     * 设置音量
     *
     * @param context
     * @return volume:0-15
     */
    fun setMediaVolume(context: Context, mediaVloume: Int) {
        var mediaVloume = mediaVloume
        if (mediaVloume < 0) {
            mediaVloume = 0
        } else if (mediaVloume > 15) {
            mediaVloume = mediaVloume % 15
            if (mediaVloume == 0) {
                mediaVloume = 15
            }
        }
        (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager).setStreamVolume(AudioManager.STREAM_MUSIC,
                mediaVloume, AudioManager.FLAG_PLAY_SOUND or AudioManager.FLAG_SHOW_UI)
    }

    /**
     * Get ring volume
     * 获取铃声音量
     *
     * @param context
     * @return volume:0-7
     */
    fun getRingVolume(context: Context): Int {
        return (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager).getStreamVolume(AudioManager
                .STREAM_RING)
    }

    /**
     * Set ring volume
     * 设置铃声音量
     *
     * @param context
     * @return volume:0-7
     */
    fun setRingVolume(context: Context, ringVloume: Int) {
        var ringVloume = ringVloume
        if (ringVloume < 0) {
            ringVloume = 0
        } else if (ringVloume > 7) {
            ringVloume = ringVloume % 7
            if (ringVloume == 0) {
                ringVloume = 7
            }
        }
        (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager).setStreamVolume(AudioManager.STREAM_RING,
                ringVloume, AudioManager.FLAG_PLAY_SOUND)
    }
}
