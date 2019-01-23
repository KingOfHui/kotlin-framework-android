package com.hualala.base.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*

object QRCode {

    private var IMAGE_HALFWIDTH = 50//宽度值，影响中间图片大小

    /**
     * 生成二维码
     *
     * @param text 需要生成二维码的文字、网址等
     * @param size 需要生成二维码的大小（）
     * @return bitmap
     */
    @JvmOverloads
    fun createQRCode(text: String, size: Int = 500): Bitmap? {
        try {
            val hints = Hashtable<EncodeHintType, String>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = -0x1000000
                    } else {
                        pixels[y * size + x] = -0x1
                    }

                }
            }
            val bitmap = Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 生成带logo的二维码，默认二维码的大小为500，logo为二维码的1/5
     *
     * @param text    需要生成二维码的文字、网址等
     * @param mBitmap logo文件
     * @return bitmap
     */
    fun createQRCodeWithLogo(text: String, mBitmap: Bitmap): Bitmap? {
        return createQRCodeWithLogo(text, 500, mBitmap)
    }

    /**
     * 生成带logo的二维码，logo默认为二维码的1/5
     *
     * @param text    需要生成二维码的文字、网址等
     * @param size    需要生成二维码的大小（）
     * @param mBitmap logo文件
     * @return bitmap
     */
    fun createQRCodeWithLogo(text: String, size: Int, mBitmap: Bitmap): Bitmap? {
        var mBitmap = mBitmap
        try {
            IMAGE_HALFWIDTH = size / 10
            val hints = Hashtable<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            /*
             * 设置容错级别，默认为ErrorCorrectionLevel.L
             * 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
             */
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)

            val width = bitMatrix.width//矩阵高度
            val height = bitMatrix.height//矩阵宽度
            val halfW = width / 2
            val halfH = height / 2

            val m = Matrix()
            val sx = 2.toFloat() * IMAGE_HALFWIDTH / mBitmap.width
            val sy = 2.toFloat() * IMAGE_HALFWIDTH / mBitmap.height
            m.setScale(sx, sy)
            //设置缩放信息
            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
                    mBitmap.width, mBitmap.height, m, false)

            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                            && y > halfH - IMAGE_HALFWIDTH
                            && y < halfH + IMAGE_HALFWIDTH) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = mBitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH)
                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * size + x] = -0x1000000
                        } else {
                            pixels[y * size + x] = -0x1
                        }
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }

    }
}
/**
 * 生成二维码，默认大小为500*500
 *
 * @param text 需要生成二维码的文字、网址等
 * @return bitmap
 */


