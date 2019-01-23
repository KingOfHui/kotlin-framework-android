package com.hualala.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.os.Build
import android.view.View
import java.io.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: bitmap图片的相互转换工具类
 */

object BitmapUtils {
    val UNSPECIFIED = 0

    /**
     * Convert resId to drawable
     *
     * @param context
     * @param resId
     * @return
     */
    fun resToDrawable(context: Context, resId: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(resId)
        } else context.resources.getDrawable(resId)
    }

    /**
     * Convert Bitmap to byte array
     *
     * @param b
     * @return
     */
    fun bitmapToByte(b: Bitmap?): ByteArray? {
        if (b == null) {
            return null
        }
        val o = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.PNG, 100, o)
        return o.toByteArray()
    }

    /**
     * Convert byte array to Bitmap
     *
     * @param b
     * @return
     */
    fun byteToBitmap(b: ByteArray?): Bitmap? {
        return if (b == null || b.size == 0) null else BitmapFactory.decodeByteArray(b, 0, b.size)
    }

    /**
     * Convert Drawable to Bitmap
     *
     * @param d
     * @return
     */
    fun drawableToBitmap(d: Drawable?): Bitmap? {
        return if (d == null) null else (d as BitmapDrawable).bitmap
    }

    /**
     * Convert Bitmap to Drawable
     *
     * @param b
     * @return
     */
    fun bitmapToDrawable(b: Bitmap?): Drawable? {
        return if (b == null) null else BitmapDrawable(b)
    }

    /**
     * Convert Drawable to byte array
     *
     * @param d
     * @return
     */
    fun drawableToByte(d: Drawable): ByteArray? {
        return bitmapToByte(drawableToBitmap(d))
    }

    /**
     * Convert byte array to Drawable
     *
     * @param b
     * @return
     */
    fun byteToDrawable(b: ByteArray): Drawable? {
        return bitmapToDrawable(byteToBitmap(b))
    }

    /**
     * Convert view to bitmap
     *
     * @param view
     * @param width
     * @param height
     * @return
     */
    @JvmOverloads
    fun convertViewToBitmap(view: View, width: Int = UNSPECIFIED, height: Int = UNSPECIFIED): Bitmap {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, if (width == UNSPECIFIED)
            View.MeasureSpec.UNSPECIFIED
        else
            View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, if (height == UNSPECIFIED)
                    View.MeasureSpec.UNSPECIFIED
                else
                    View.MeasureSpec.EXACTLY))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))
        return bitmap
    }

    /**
     * Resize image by width and height
     *
     * @param originalBitmap
     * @param w
     * @param h
     * @return
     */
    fun resizeImage(originalBitmap: Bitmap?, w: Int, h: Int): Bitmap? {
        if (originalBitmap == null) {
            return null
        }
        var width = originalBitmap.width
        var height = originalBitmap.height
        if (width <= w && height <= h) {
            return originalBitmap
        }
        val screenRatio = w.toFloat() / h
        val ratio = width.toFloat() / height
        if (screenRatio >= ratio) {
            width = (h * ratio).toInt()
            height = h
        } else {
            height = (w / ratio).toInt()
            width = w
        }
        return Bitmap.createScaledBitmap(originalBitmap, width, height, true)
    }

    /**
     * Decode bitmap
     *
     * @param is
     * @param context
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun decodeBitmap(`is`: InputStream, context: Context): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true // 设置成了true,不占用内存，只获取bitmap宽高
        val data = isToByte(`is`)//将InputStream转为byte数组，可以多次读取
        //        BitmapFactory.decodeStream(is, null, options);InputStream流只能被读取一次，下次读取就为空了。
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        options.inSampleSize = calculateInSampleSize(options, context) // 调用上面定义的方法计算inSampleSize值
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(data, 0, data.size, options)
    }

    /**
     * Calculate inSampleSize
     *
     * @param options
     * @param context
     * @return
     */
    private fun calculateInSampleSize(options: BitmapFactory.Options, context: Context): Int {
        // 源图片的高度和宽度
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        val h = DisplayUtils.getScreenHeight(context)
        val w = DisplayUtils.getScreenWidth(context)
        if (height > h || width > w) {
            // 计算出实际宽高和目标宽高的比率
            val heightRatio = Math.round(height.toFloat() / h.toFloat())
            val widthRatio = Math.round(width.toFloat() / w.toFloat())
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    /**
     * Convert InputStream to byte array
     *
     * @param is
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun isToByte(`is`: InputStream): ByteArray {
        val baos = ByteArrayOutputStream()
        val buff = ByteArray(1024)
        var len = 0
        while (`is`.read(buff).also { len = it } != -1) {
            baos.write(buff, 0, len)
        }
        `is`.close()
        baos.close()
        return baos.toByteArray()
    }

    /**
     * take a screenshot
     *
     * @param activity
     * @param filePath
     * @return
     */
    fun screenshot(activity: Activity, filePath: String): Boolean {
        val decorView = activity.window.decorView
        decorView.isDrawingCacheEnabled = true
        decorView.buildDrawingCache()
        var bitmap: Bitmap? = decorView.drawingCache
        val imagePath = File(filePath)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(imagePath)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
                if (null != bitmap) {
                    bitmap.recycle()
                    bitmap = null
                }
            } catch (e: Exception) {
            }

            decorView.destroyDrawingCache()
            decorView.isDrawingCacheEnabled = false
        }
        return false
    }

    /**
     * Combine bitmaps
     *
     * @param bgBitmap 背景Bitmap
     * @param fgBitmap 前景Bitmap
     * @return 合成后的Bitmap
     */
    fun combineBitmap(bgBitmap: Bitmap, fgBitmap: Bitmap): Bitmap {
        val bmp: Bitmap

        val width = if (bgBitmap.width > fgBitmap.width)
            bgBitmap.width
        else
            fgBitmap
                    .width
        val height = if (bgBitmap.height > fgBitmap.height)
            bgBitmap.height
        else
            fgBitmap
                    .height

        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)

        val canvas = Canvas(bmp)
        canvas.drawBitmap(bgBitmap, 0f, 0f, null)
        canvas.drawBitmap(fgBitmap, 0f, 0f, paint)

        return bmp
    }

    /**
     * Combine bitmaps
     *
     * @param bgd 后景Bitmap
     * @param fg  前景Bitmap
     * @return 合成后Bitmap
     */
    fun combineBitmapInSameSize(bgd: Bitmap, fg: Bitmap): Bitmap {
        var bgd = bgd
        var fg = fg
        val bmp: Bitmap

        val width = if (bgd.width < fg.width)
            bgd.width
        else
            fg
                    .width
        val height = if (bgd.height < fg.height)
            bgd.height
        else
            fg
                    .height

        if (fg.width != width && fg.height != height) {
            fg = zoom(fg, width, height)
        }
        if (bgd.width != width && bgd.height != height) {
            bgd = zoom(bgd, width, height)
        }

        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)

        val canvas = Canvas(bmp)
        canvas.drawBitmap(bgd, 0f, 0f, null)
        canvas.drawBitmap(fg, 0f, 0f, paint)

        return bmp
    }

    /**
     * zoom bitmap
     *
     * @param bitmap 源Bitmap
     * @param w      宽
     * @param h      高
     * @return 目标Bitmap
     */
    fun zoom(bitmap: Bitmap, w: Int, h: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val matrix = Matrix()
        val scaleWidht = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidht, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true)
    }

    /**
     * Get rounded corner bitmap
     *
     * @param bitmap
     * @param roundPx 圆角大小
     * @return
     */
    fun createRoundedCornerBitmap(bitmap: Bitmap, roundPx: Float): Bitmap {

        val output = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    /**
     * Get reflection bitmap
     *
     * @param bitmap 源Bitmap
     * @return 带倒影的Bitmap
     */
    fun createReflectionBitmap(bitmap: Bitmap): Bitmap {
        val reflectionGap = 4
        val width = bitmap.width
        val height = bitmap.height

        val matrix = Matrix()
        matrix.preScale(1f, -1f)

        val reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false)

        val bitmapWithReflection = Bitmap.createBitmap(width,
                height + height / 2, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmapWithReflection)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val deafalutPaint = Paint()
        canvas.drawRect(0f, height.toFloat(), width.toFloat(), (height + reflectionGap).toFloat(), deafalutPaint)

        canvas.drawBitmap(reflectionImage, 0f, (height + reflectionGap).toFloat(), null)

        val paint = Paint()
        val shader = LinearGradient(0f, bitmap.height.toFloat(), 0f,
                (bitmapWithReflection.height + reflectionGap).toFloat(), 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP)
        paint.shader = shader
        // Set the Transfer mode to be porter duff and destination in
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0f, height.toFloat(), width.toFloat(), (bitmapWithReflection.height + reflectionGap).toFloat(), paint)

        return bitmapWithReflection
    }

    /**
     * Compress bitmap
     *
     * @param bmp 源Bitmap
     * @return 压缩后的Bitmap
     */
    fun compressBitmap(bmp: Bitmap): Bitmap? {

        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 100
        while (baos.toByteArray().size / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset()// 重置baos即清空baos
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos)// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10// 每次都减少10
        }
        val isBm = ByteArrayInputStream(baos.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 源Bitmap
     * @return 返回转换好的位图
     */
    fun convertGreyImg(img: Bitmap): Bitmap {
        val width = img.width // 获取位图的宽
        val height = img.height // 获取位图的高

        val pixels = IntArray(width * height) // 通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height)
        val alpha = 0xFF shl 24
        for (i in 0 until height) {
            for (j in 0 until width) {
                var grey = pixels[width * i + j]

                val red = grey and 0x00FF0000 shr 16
                val green = grey and 0x0000FF00 shr 8
                val blue = grey and 0x000000FF

                grey = (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Get round bitmap
     *
     * @param bitmap
     * @return
     */
    fun createRoundBitmap(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val roundPx: Float
        val left: Float
        val top: Float
        val right: Float
        val bottom: Float
        val dst_left: Float
        val dst_top: Float
        val dst_right: Float
        val dst_bottom: Float
        if (width <= height) {
            roundPx = (width / 2).toFloat()
            top = 0f
            bottom = width.toFloat()
            left = 0f
            right = width.toFloat()
            height = width
            dst_left = 0f
            dst_top = 0f
            dst_right = width.toFloat()
            dst_bottom = width.toFloat()
        } else {
            roundPx = (height / 2).toFloat()
            val clip = ((width - height) / 2).toFloat()
            left = clip
            right = width - clip
            top = 0f
            bottom = height.toFloat()
            width = height
            dst_left = 0f
            dst_top = 0f
            dst_right = height.toFloat()
            dst_bottom = height.toFloat()
        }

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val src = Rect(left.toInt(), top.toInt(), right.toInt(),
                bottom.toInt())
        val dst = Rect(dst_left.toInt(), dst_top.toInt(),
                dst_right.toInt(), dst_bottom.toInt())
        val rectF = RectF(dst)

        paint.isAntiAlias = true

        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, src, dst, paint)
        return output
    }

    /**
     * Returns a Bitmap representing the thumbnail of the specified Bitmap.
     *
     * @param bitmap
     * @param context
     * @return
     */
    fun createThumbnailBitmap(bitmap: Bitmap, context: Context): Bitmap {
        var sIconWidth = -1
        var sIconHeight = -1
        val resources = context.resources
        sIconHeight = resources
                .getDimension(android.R.dimen.app_icon_size).toInt()
        sIconWidth = sIconHeight

        val sPaint = Paint()
        val sBounds = Rect()
        val sOldBounds = Rect()
        val sCanvas = Canvas()

        var width = sIconWidth
        var height = sIconHeight

        sCanvas.drawFilter = PaintFlagsDrawFilter(Paint.DITHER_FLAG,
                Paint.FILTER_BITMAP_FLAG)

        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height

        if (width > 0 && height > 0) {
            if (width < bitmapWidth || height < bitmapHeight) {
                val ratio = bitmapWidth.toFloat() / bitmapHeight

                if (bitmapWidth > bitmapHeight) {
                    height = (width / ratio).toInt()
                } else if (bitmapHeight > bitmapWidth) {
                    width = (height * ratio).toInt()
                }

                val c = if (width == sIconWidth && height == sIconHeight)
                    bitmap
                            .config
                else
                    Bitmap.Config.ARGB_8888
                val thumb = Bitmap.createBitmap(sIconWidth,
                        sIconHeight, c)
                sCanvas.setBitmap(thumb)
                sPaint.isDither = false
                sPaint.isFilterBitmap = true
                sBounds.set((sIconWidth - width) / 2,
                        (sIconHeight - height) / 2, width, height)
                sOldBounds.set(0, 0, bitmapWidth, bitmapHeight)
                sCanvas.drawBitmap(bitmap, sOldBounds, sBounds, sPaint)
                return thumb
            } else if (bitmapWidth < width || bitmapHeight < height) {
                val c = Bitmap.Config.ARGB_8888
                val thumb = Bitmap.createBitmap(sIconWidth,
                        sIconHeight, c)
                sCanvas.setBitmap(thumb)
                sPaint.isDither = false
                sPaint.isFilterBitmap = true
                sCanvas.drawBitmap(bitmap, ((sIconWidth - bitmapWidth) / 2).toFloat(),
                        ((sIconHeight - bitmapHeight) / 2).toFloat(), sPaint)
                return thumb
            }
        }

        return bitmap
    }

    /**
     * Create bitmap with watermark, in bottom right corner.
     *
     * @param src
     * @param watermark
     * @return
     */
    fun createWatermarkBitmap(src: Bitmap?, watermark: Bitmap): Bitmap? {
        if (src == null) {
            return null
        }
        val w = src.width
        val h = src.height
        val ww = watermark.width
        val wh = watermark.height
        // create the new blank bitmap
        val newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)// 创建一个新的和SRC长度宽度一样的位图
        val cv = Canvas(newb)
        // draw src into
        cv.drawBitmap(src, 0f, 0f, null)// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, (w - ww + 5).toFloat(), (h - wh + 5).toFloat(), null)// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG)// 保存
        // store
        cv.restore()// 存储
        return newb
    }

    /**
     * 重新编码Bitmap
     *
     * @param src     需要重新编码的Bitmap
     * @param format  编码后的格式（目前只支持png和jpeg这两种格式）
     * @param quality 重新生成后的bitmap的质量
     * @return 返回重新生成后的bitmap
     */
    fun decodeBitmap(src: Bitmap, format: Bitmap.CompressFormat,
                     quality: Int): Bitmap {
        val os = ByteArrayOutputStream()
        src.compress(format, quality, os)

        val array = os.toByteArray()
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }

    /**
     * 图片压缩，如果bitmap本身的大小小于maxSize，则不作处理
     *
     * @param bitmap  要压缩的图片
     * @param maxSize 压缩后的大小，单位kb
     */
    fun compressBitmap(bitmap: Bitmap, maxSize: Double) {
        var bitmap = bitmap
        // 将bitmap放至数组中，意在获得bitmap的大小（与实际读取的原文件要大）
        val baos = ByteArrayOutputStream()
        // 格式、质量、输出流
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos)
        val b = baos.toByteArray()
        // 将字节换成KB
        val mid = (b.size / 1024).toDouble()
        // 获取bitmap大小 是允许最大大小的多少倍
        val i = mid / maxSize
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (i > 1) {
            // 缩放图片 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （保持宽高不变，缩放后也达到了最大占用空间的大小）
            bitmap = scale(bitmap, bitmap.width / Math.sqrt(i),
                    bitmap.height / Math.sqrt(i))
        }
    }

    /**
     * scale bitmap
     *
     * @param src
     * @param newWidth
     * @param newHeight
     * @return
     */
    fun scale(src: Bitmap, newWidth: Double, newHeight: Double): Bitmap {
        // 记录src的宽高
        val width = src.width.toFloat()
        val height = src.height.toFloat()
        // 创建一个matrix容器
        val matrix = Matrix()
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 开始缩放
        matrix.postScale(scaleWidth, scaleHeight)
        // 创建缩放后的图片
        return Bitmap.createBitmap(src, 0, 0, width.toInt(), height.toInt(),
                matrix, true)
    }

    /**
     * scale bitmap
     *
     * @param src
     * @param scaleMatrix
     * @return
     */
    fun scale(src: Bitmap, scaleMatrix: Matrix): Bitmap {
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height,
                scaleMatrix, true)
    }

    /**
     * scale bitmap
     *
     * @param src
     * @param scaleX
     * @param scaleY
     * @return
     */
    fun scale(src: Bitmap, scaleX: Float, scaleY: Float): Bitmap {
        val matrix = Matrix()
        matrix.postScale(scaleX, scaleY)
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height,
                matrix, true)
    }

    /**
     * scale bitmap with the same scale
     *
     * @param src
     * @param scale
     * @return
     */
    fun scale(src: Bitmap, scale: Float): Bitmap {
        return scale(src, scale, scale)
    }

    /**
     * rotate bitmap
     *
     * @param bitmap
     * @param angle
     * @return
     */
    fun rotate(bitmap: Bitmap, angle: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width,
                bitmap.height, matrix, true)
    }

    /**
     * 水平翻转处理
     *
     * @param bitmap 原图
     * @return 水平翻转后的图片
     */
    fun reverseHorizontal(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.preScale(-1f, 1f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width,
                bitmap.height, matrix, false)
    }

    /**
     * 垂直翻转处理
     *
     * @param bitmap 原图
     * @return 垂直翻转后的图片
     */
    fun reverseVertical(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.preScale(1f, -1f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width,
                bitmap.height, matrix, false)
    }

    /**
     * 更改图片色系，变亮或变暗
     *
     * @param delta 图片的亮暗程度值，越小图片会越亮，取值范围(0,24)
     * @return
     */
    fun adjustTone(src: Bitmap, delta: Int): Bitmap? {
        if (delta >= 24 || delta <= 0) {
            return null
        }
        // 设置高斯矩阵
        val gauss = intArrayOf(1, 2, 1, 2, 4, 2, 1, 2, 1)
        val width = src.width
        val height = src.height
        val bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565)

        var pixR = 0
        var pixG = 0
        var pixB = 0
        var pixColor = 0
        var newR = 0
        var newG = 0
        var newB = 0
        var idx = 0
        val pixels = IntArray(width * height)

        src.getPixels(pixels, 0, width, 0, 0, width, height)
        var i = 1
        val length = height - 1
        while (i < length) {
            var k = 1
            val len = width - 1
            while (k < len) {
                idx = 0
                for (m in -1..1) {
                    for (n in -1..1) {
                        pixColor = pixels[(i + m) * width + k + n]
                        pixR = Color.red(pixColor)
                        pixG = Color.green(pixColor)
                        pixB = Color.blue(pixColor)

                        newR += pixR * gauss[idx]
                        newG += pixG * gauss[idx]
                        newB += pixB * gauss[idx]
                        idx++
                    }
                }
                newR /= delta
                newG /= delta
                newB /= delta
                newR = Math.min(255, Math.max(0, newR))
                newG = Math.min(255, Math.max(0, newG))
                newB = Math.min(255, Math.max(0, newB))
                pixels[i * width + k] = Color.argb(255, newR, newG, newB)
                newR = 0
                newG = 0
                newB = 0
                k++
            }
            i++
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    /**
     * 将彩色图转换为黑白图
     *
     * @param bmp 位图
     * @return 返回转换好的位图
     */
    fun convertToBlackWhite(bmp: Bitmap): Bitmap {
        val width = bmp.width
        val height = bmp.height
        val pixels = IntArray(width * height)
        bmp.getPixels(pixels, 0, width, 0, 0, width, height)

        val alpha = 0xFF shl 24 // 默认将bitmap当成24色图片
        for (i in 0 until height) {
            for (j in 0 until width) {
                var grey = pixels[width * i + j]

                val red = grey and 0x00FF0000 shr 16
                val green = grey and 0x0000FF00 shr 8
                val blue = grey and 0x000000FF

                grey = (red * 0.3 + green * 0.59 + blue * 0.11).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height)
        return newBmp
    }

    /**
     * 读取图片属性：图片被旋转的角度
     *
     * @param path 图片绝对路径
     * @return 旋转的角度
     */
    fun getImageDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree
    }


    /**
     * 饱和度处理
     *
     * @param bitmap          原图
     * @param saturationValue 新的饱和度值
     * @return 改变了饱和度值之后的图片
     */
    fun saturation(bitmap: Bitmap, saturationValue: Int): Bitmap {
        // 计算出符合要求的饱和度值
        val newSaturationValue = saturationValue * 1.0f / 127
        // 创建一个颜色矩阵
        val saturationColorMatrix = ColorMatrix()
        // 设置饱和度值
        saturationColorMatrix.setSaturation(newSaturationValue)
        // 创建一个画笔并设置其颜色过滤器
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(saturationColorMatrix)
        // 创建一个新的图片并创建画布
        val newBitmap = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return newBitmap
    }

    /**
     * 亮度处理
     *
     * @param bitmap   原图
     * @param lumValue 新的亮度值
     * @return 改变了亮度值之后的图片
     */
    fun lum(bitmap: Bitmap, lumValue: Int): Bitmap {
        // 计算出符合要求的亮度值
        val newlumValue = lumValue * 1.0f / 127
        // 创建一个颜色矩阵
        val lumColorMatrix = ColorMatrix()
        // 设置亮度值
        lumColorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1f)
        // 创建一个画笔并设置其颜色过滤器
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(lumColorMatrix)
        // 创建一个新的图片并创建画布
        val newBitmap = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return newBitmap
    }

    /**
     * 色相处理
     *
     * @param bitmap   原图
     * @param hueValue 新的色相值
     * @return 改变了色相值之后的图片
     */
    fun hue(bitmap: Bitmap, hueValue: Int): Bitmap {
        // 计算出符合要求的色相值
        val newHueValue = (hueValue - 127) * 1.0f / 127 * 180
        // 创建一个颜色矩阵
        val hueColorMatrix = ColorMatrix()
        // 控制让红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(0, newHueValue)
        // 控制让绿红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(1, newHueValue)
        // 控制让蓝色区在色轮上旋转的角度
        hueColorMatrix.setRotate(2, newHueValue)
        // 创建一个画笔并设置其颜色过滤器
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(hueColorMatrix)
        // 创建一个新的图片并创建画布
        val newBitmap = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return newBitmap
    }

    /**
     * 亮度、色相、饱和度处理
     *
     * @param bitmap          原图
     * @param lumValue        亮度值
     * @param hueValue        色相值
     * @param saturationValue 饱和度值
     * @return 亮度、色相、饱和度处理后的图片
     */
    fun lumAndHueAndSaturation(bitmap: Bitmap, lumValue: Int,
                               hueValue: Int, saturationValue: Int): Bitmap {
        // 计算出符合要求的饱和度值
        val newSaturationValue = saturationValue * 1.0f / 127
        // 计算出符合要求的亮度值
        val newlumValue = lumValue * 1.0f / 127
        // 计算出符合要求的色相值
        val newHueValue = (hueValue - 127) * 1.0f / 127 * 180

        // 创建一个颜色矩阵并设置其饱和度
        val colorMatrix = ColorMatrix()

        // 设置饱和度值
        colorMatrix.setSaturation(newSaturationValue)
        // 设置亮度值
        colorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1f)
        // 控制让红色区在色轮上旋转的角度
        colorMatrix.setRotate(0, newHueValue)
        // 控制让绿红色区在色轮上旋转的角度
        colorMatrix.setRotate(1, newHueValue)
        // 控制让蓝色区在色轮上旋转的角度
        colorMatrix.setRotate(2, newHueValue)

        // 创建一个画笔并设置其颜色过滤器
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        // 创建一个新的图片并创建画布
        val newBitmap = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return newBitmap
    }

    /**
     * 怀旧效果
     *
     * @param bitmap
     * @return
     */
    fun nostalgic(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565)
        var pixColor = 0
        var pixR = 0
        var pixG = 0
        var pixB = 0
        var newR = 0
        var newG = 0
        var newB = 0
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        for (i in 0 until height) {
            for (k in 0 until width) {
                pixColor = pixels[width * i + k]
                pixR = Color.red(pixColor)
                pixG = Color.green(pixColor)
                pixB = Color.blue(pixColor)
                newR = (0.393 * pixR + 0.769 * pixG + 0.189 * pixB).toInt()
                newG = (0.349 * pixR + 0.686 * pixG + 0.168 * pixB).toInt()
                newB = (0.272 * pixR + 0.534 * pixG + 0.131 * pixB).toInt()
                val newColor = Color.argb(255, if (newR > 255) 255 else newR,
                        if (newG > 255) 255 else newG, if (newB > 255) 255 else newB)
                pixels[width * i + k] = newColor
            }
        }
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    /**
     * 柔化效果
     *
     * @param bitmap
     * @return
     */
    fun soften(bitmap: Bitmap): Bitmap {
        // 高斯矩阵
        val gauss = intArrayOf(1, 2, 1, 2, 4, 2, 1, 2, 1)

        val width = bitmap.width
        val height = bitmap.height
        val newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565)

        var pixR = 0
        var pixG = 0
        var pixB = 0

        var pixColor = 0

        var newR = 0
        var newG = 0
        var newB = 0

        val delta = 16 // 值越小图片会越亮，越大则越暗

        var idx = 0
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var i = 1
        val length = height - 1
        while (i < length) {
            var k = 1
            val len = width - 1
            while (k < len) {
                idx = 0
                for (m in -1..1) {
                    for (n in -1..1) {
                        pixColor = pixels[(i + m) * width + k + n]
                        pixR = Color.red(pixColor)
                        pixG = Color.green(pixColor)
                        pixB = Color.blue(pixColor)

                        newR = newR + pixR * gauss[idx]
                        newG = newG + pixG * gauss[idx]
                        newB = newB + pixB * gauss[idx]
                        idx++
                    }
                }

                newR /= delta
                newG /= delta
                newB /= delta

                newR = Math.min(255, Math.max(0, newR))
                newG = Math.min(255, Math.max(0, newG))
                newB = Math.min(255, Math.max(0, newB))

                pixels[i * width + k] = Color.argb(255, newR, newG, newB)

                newR = 0
                newG = 0
                newB = 0
                k++
            }
            i++
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    /**
     * 光照效果
     *
     * @param bitmap
     * @param centerX 光源在X轴的位置
     * @param centerY 光源在Y轴的位置
     * @return
     */
    fun sunshine(bitmap: Bitmap, centerX: Int, centerY: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565)

        var pixR = 0
        var pixG = 0
        var pixB = 0

        var pixColor = 0

        var newR = 0
        var newG = 0
        var newB = 0
        val radius = Math.min(centerX, centerY)

        val strength = 150f // 光照强度 100~150
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var pos = 0
        var i = 1
        val length = height - 1
        while (i < length) {
            var k = 1
            val len = width - 1
            while (k < len) {
                pos = i * width + k
                pixColor = pixels[pos]

                pixR = Color.red(pixColor)
                pixG = Color.green(pixColor)
                pixB = Color.blue(pixColor)

                newR = pixR
                newG = pixG
                newB = pixB

                // 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
                val distance = (Math.pow((centerY - i).toDouble(), 2.0) + Math.pow(
                        (centerX - k).toDouble(), 2.0)).toInt()
                if (distance < radius * radius) {
                    // 按照距离大小计算增加的光照值
                    val result = (strength * (1.0 - Math.sqrt(distance.toDouble()) / radius)).toInt()
                    newR = pixR + result
                    newG = pixG + result
                    newB = pixB + result
                }

                newR = Math.min(255, Math.max(0, newR))
                newG = Math.min(255, Math.max(0, newG))
                newB = Math.min(255, Math.max(0, newB))

                pixels[pos] = Color.argb(255, newR, newG, newB)
                k++
            }
            i++
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    /**
     * 底片效果
     *
     * @param bitmap
     * @return
     */
    fun film(bitmap: Bitmap): Bitmap {
        // RGBA的最大值
        val MAX_VALUE = 255
        val width = bitmap.width
        val height = bitmap.height
        val newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565)

        var pixR = 0
        var pixG = 0
        var pixB = 0

        var pixColor = 0

        var newR = 0
        var newG = 0
        var newB = 0

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var pos = 0
        var i = 1
        val length = height - 1
        while (i < length) {
            var k = 1
            val len = width - 1
            while (k < len) {
                pos = i * width + k
                pixColor = pixels[pos]

                pixR = Color.red(pixColor)
                pixG = Color.green(pixColor)
                pixB = Color.blue(pixColor)

                newR = MAX_VALUE - pixR
                newG = MAX_VALUE - pixG
                newB = MAX_VALUE - pixB

                newR = Math.min(MAX_VALUE, Math.max(0, newR))
                newG = Math.min(MAX_VALUE, Math.max(0, newG))
                newB = Math.min(MAX_VALUE, Math.max(0, newB))

                pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB)
                k++
            }
            i++
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    /**
     * 锐化效果
     *
     * @param bitmap
     * @return
     */
    fun sharpen(bitmap: Bitmap): Bitmap {
        // 拉普拉斯矩阵
        val laplacian = intArrayOf(-1, -1, -1, -1, 9, -1, -1, -1, -1)

        val width = bitmap.width
        val height = bitmap.height
        val newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565)

        var pixR = 0
        var pixG = 0
        var pixB = 0

        var pixColor = 0

        var newR = 0
        var newG = 0
        var newB = 0

        var idx = 0
        val alpha = 0.3f
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var i = 1
        val length = height - 1
        while (i < length) {
            var k = 1
            val len = width - 1
            while (k < len) {
                idx = 0
                for (m in -1..1) {
                    for (n in -1..1) {
                        pixColor = pixels[(i + n) * width + k + m]
                        pixR = Color.red(pixColor)
                        pixG = Color.green(pixColor)
                        pixB = Color.blue(pixColor)

                        newR = newR + (pixR.toFloat() * laplacian[idx].toFloat() * alpha).toInt()
                        newG = newG + (pixG.toFloat() * laplacian[idx].toFloat() * alpha).toInt()
                        newB = newB + (pixB.toFloat() * laplacian[idx].toFloat() * alpha).toInt()
                        idx++
                    }
                }

                newR = Math.min(255, Math.max(0, newR))
                newG = Math.min(255, Math.max(0, newG))
                newB = Math.min(255, Math.max(0, newB))

                pixels[i * width + k] = Color.argb(255, newR, newG, newB)
                newR = 0
                newG = 0
                newB = 0
                k++
            }
            i++
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    /**
     * 浮雕效果
     *
     * @param bitmap
     * @return
     */
    fun emboss(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val newBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565)

        var pixR = 0
        var pixG = 0
        var pixB = 0

        var pixColor = 0

        var newR = 0
        var newG = 0
        var newB = 0

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var pos = 0
        var i = 1
        val length = height - 1
        while (i < length) {
            var k = 1
            val len = width - 1
            while (k < len) {
                pos = i * width + k
                pixColor = pixels[pos]

                pixR = Color.red(pixColor)
                pixG = Color.green(pixColor)
                pixB = Color.blue(pixColor)

                pixColor = pixels[pos + 1]
                newR = Color.red(pixColor) - pixR + 127
                newG = Color.green(pixColor) - pixG + 127
                newB = Color.blue(pixColor) - pixB + 127

                newR = Math.min(255, Math.max(0, newR))
                newG = Math.min(255, Math.max(0, newG))
                newB = Math.min(255, Math.max(0, newB))

                pixels[pos] = Color.argb(255, newR, newG, newB)
                k++
            }
            i++
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

}
/**
 * Convert view to bitmap
 *
 * @param view
 * @return
 */
