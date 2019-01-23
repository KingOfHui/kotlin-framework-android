package com.hualala.base.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.*

object ViewScreenshotUtils {

    fun viewSaveToImage(view: View) {
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        view.drawingCacheBackgroundColor = Color.WHITE

        // 把一个View转换成图片
        val cachebmp = loadBitmapFromView(view)

        val fos: FileOutputStream
        var imagePath = ""
        var file: File? = null
        try {
            // 判断手机设备是否有SD卡
            val isHasSDCard = Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
            if (isHasSDCard) {
                // SD卡根目录
                val sdRoot = Environment.getExternalStorageDirectory()
                file = File(sdRoot, Calendar.getInstance().timeInMillis.toString() + ".png")
                fos = FileOutputStream(file)
            } else
                throw Exception("创建文件失败!")

            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos)

            fos.flush()
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        file?.let {
            val context = view.context
            MediaStore.Images.Media.insertImage(context.getContentResolver(), it.absolutePath, it.name, null)
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            val uri = Uri.fromFile(file)
            intent.setData(uri)
            context.sendBroadcast(intent)
        }



        view.destroyDrawingCache()
    }

    fun loadBitmapFromView(v: View): Bitmap {
        val w = v.width
        val h = v.height

        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)

        c.drawColor(Color.WHITE)
        /** 如果不设置canvas画布为白色，则生成透明  */

        v.layout(0, 0, w, h)
        v.draw(c)

        return bmp
    }

}
