package com.dysen.baselib.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.dysen.baselib.R
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * dysen.
 * dy.sen@qq.com    11/17/20  11:36 AM
 * <p>
 * Info：
 */
object BitmapUtils {
    var TAG = "BitmapUtils"
    private var ScreenshotPrefixName = "App_"

    /**
     * 设置截图图片的前缀名称 (默认 ctivity activity, )
     *
     * @param screenshotPrefixName
     */
    fun setScreenshotPrefixName(screenshotPrefixName: String) {
        ScreenshotPrefixName = screenshotPrefixName + "_"
    }


    // 获取指定Activity的截屏，保存到png文件
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    fun takeScreenShot(activity: Activity): Bitmap? {

        //View是你需要截图的View
        val view = activity.window.decorView // 获取Activity整个窗口最顶层的View
        view.isDrawingCacheEnabled = true // 设置控件允许绘制缓存
        view.buildDrawingCache()
        val b1 = view.drawingCache // 获取控件的绘制缓存（快照）

        //获取状态栏高度
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        println(statusBarHeight)

        //获取屏幕长和高
        val width = activity.windowManager.defaultDisplay.width
        val height = activity.windowManager.defaultDisplay.height

        //去掉标题栏
        //Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        val baseBitmap =
            Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return baseBitmap
    }

    /**
     * 有水印的保存
     *
     * @param srcBitmap 当前截图的bitmap
     * @return
     */
    fun addMarkPic(srcBitmap: Bitmap, markBitmap: Bitmap): Bitmap? {
        var srcBitmap = srcBitmap
        if (markBitmap.width < srcBitmap.width) srcBitmap = scaleWithWH(srcBitmap, markBitmap.width.toDouble(), srcBitmap.height.toDouble())
        val photoMark =
            Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(photoMark)
        canvas.drawBitmap(srcBitmap, 0f, 0f, null)
        val bitmapMark = markBitmap.copy(Bitmap.Config.ARGB_8888, true)
        //        bitmapMark = scaleWithWH(bitmapMark, srcBitmap.getWidth(), bitmapMark.getHeight());
        canvas.drawBitmap(
            bitmapMark,
            srcBitmap.width - bitmapMark.width.toFloat(),
            srcBitmap.height - bitmapMark.height.toFloat(),
            null
        )
        canvas.save()
        canvas.restore()
        return scaleWithWH(
            photoMark,
            photoMark.width.toDouble(),
            photoMark.height.toDouble()
        )
    }

    fun addMarkPic2(srcBitmap: Bitmap, markBitmap: Bitmap): Bitmap? {
        val photoMark = Bitmap.createBitmap(
            srcBitmap.width,
            srcBitmap.height + markBitmap.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(photoMark)
        canvas.drawBitmap(srcBitmap, 0f, 0f, null)
        val bitmapMark = markBitmap.copy(Bitmap.Config.ARGB_8888, true)
        canvas.drawBitmap(bitmapMark, 0f, srcBitmap.height.toFloat(), null)
        canvas.save()
        canvas.restore()
        return scaleWithWH(
            photoMark,
            photoMark.width.toDouble(),
            photoMark.height.toDouble()
        )
    }

    fun addNewsPic(srcBitmap: Bitmap, markBitmap: Bitmap, topHeight: Int): Bitmap? {
        val photoMark =
            Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(photoMark)
        canvas.drawBitmap(srcBitmap, 0f, 0f, null)
        val bitmapMark = markBitmap.copy(Bitmap.Config.ARGB_8888, true)
        canvas.drawBitmap(bitmapMark, 0f, topHeight.toFloat(), null)
        canvas.save()
        canvas.restore()
        return scaleWithWH(
            photoMark,
            photoMark.width.toDouble(),
            photoMark.height.toDouble()
        )
    }


    /**
     * 把一个layout转化成bitmap对象
     */
    fun getLayoutBitmap(activity: Activity, layoutId: Int): Bitmap? {
        val view = activity.layoutInflater.inflate(layoutId, null)
        val me = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(me, me)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache
    }

    var bitmap: Bitmap? = null

    fun getUrl2BitMap(url: String?): Bitmap? {
        Thread {
            var imageurl: URL? = null
            try {
                imageurl = URL(url)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            try {
                val conn = imageurl!!.openConnection() as HttpURLConnection
                conn.doInput = true
                conn.connect()
                val `is` = conn.inputStream
                bitmap = BitmapFactory.decodeStream(`is`)
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
        return bitmap
    }

    /**
     * 把图片转成bitmap对象
     *
     * @param context
     * @param vectorDrawableId
     * @return
     */
    fun getResBitmap(context: Context, @DrawableRes vectorDrawableId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable = context.getDrawable(vectorDrawableId)
            vectorDrawable?.let {
                if (it.intrinsicHeight>0 && it.intrinsicWidth>0){

                bitmap = Bitmap.createBitmap(
                    it.intrinsicWidth,
                    it.intrinsicHeight, Bitmap.Config.ARGB_8888
                )
                bitmap?.let { bitmap ->
                    val canvas = Canvas(bitmap)
                    it.setBounds(0, 0, canvas.width, canvas.height)
                    it.draw(canvas)
                }
                }else{
                    bitmap = BitmapFactory.decodeResource(context.resources, vectorDrawableId)
                }
            }

        } else {
            bitmap = BitmapFactory.decodeResource(context.resources, vectorDrawableId)
        }
        return bitmap
    }

    /**
     * 通过 pic path获得pic的bitmap对象
     *
     * @param pathString
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    fun getDiskBitmap(pathString: String?): Bitmap? {
        var getBbitmap: Bitmap? = null
        //Bitmap复用  减小内存开销
        try {
            val file = File(pathString)
            if (file.exists()) {
                val options = BitmapFactory.Options()
                if (getBbitmap == null) {
                    options.inMutable = true
                    getBbitmap = BitmapFactory.decodeFile(pathString, options)
                } else {
                    // 使用inBitmap属性，这个属性必须设置；
                    options.inBitmap = getBbitmap
                    options.inMutable = true
                    getBbitmap = BitmapFactory.decodeFile(pathString, options)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    System.out.println(getBbitmap+"========bitmap=====1======="+getBbitmap.getAllocationByteCount());
                } else {
//                    System.out.println(getBbitmap+"========bitmap=====2======="+getBbitmap.getByteCount());
                }
            }
        } catch (e: Exception) {
        }
        return getBbitmap
    }

    fun compressScale(bitmap: Bitmap, scale: Float): Bitmap? {
        val matrix = Matrix()
        matrix.setScale(scale, scale)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param w
     * @param h
     * @return
     */
    fun scaleWithWH(src: Bitmap, w: Double, h: Double): Bitmap {
        return if (w == 0.0 || h == 0.0 || src == null) {
            src
        } else {
            // 记录src的宽高
            val width = src.width
            val height = src.height
            // 创建一个matrix容器
            val matrix = Matrix()
            // 计算缩放比例
            val scaleWidth = (w / width).toFloat()
            val scaleHeight = (h / height).toFloat()
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight)
            // 创建缩放后的图片
            Bitmap.createBitmap(src, 0, 0, width, height, matrix, true)
        }
    }

    /**
     * 图片的缩放方法
     *
     * @param bitmap  ：源图片资源
     * @param maxSize ：图片允许最大空间  单位:KB
     * @return
     */
    fun getZoomImage(bitmap: Bitmap?, maxSize: Double): Bitmap? {
        if (bitmap?.isRecycled!!) {
            return null
        }

        // 单位：从 Byte 换算成 KB
        var currentSize = bitmapToByteArray(bitmap, false)!!.size / 1024.toDouble()
        // 判断bitmap占用空间是否大于允许最大空间,如果大于则压缩,小于则不压缩
        while (currentSize > maxSize) {
            // 计算bitmap的大小是maxSize的多少倍
            val multiple = currentSize / maxSize
            // 开始压缩：将宽度和高度压缩掉对应的平方根倍
            // 1.保持新的宽度和高度，与bitmap原来的宽高比率一致
            // 2.压缩后达到了最大大小对应的新bitmap，显示效果最好
            this.bitmap = bitmap?.let {
                getZoomImage(
                    it,
                    bitmap!!.width / Math.sqrt(multiple),
                    bitmap.height / Math.sqrt(multiple)
                )
            }
            currentSize = bitmapToByteArray(bitmap, false)!!.size / 1024.toDouble()
        }
        return bitmap
    }

    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    fun getZoomImage(orgBitmap: Bitmap, newWidth: Double, newHeight: Double): Bitmap? {
        if (null == orgBitmap) {
            return null
        }
        if (orgBitmap.isRecycled) {
            return null
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null
        }

        // 获取图片的宽和高
        val width = orgBitmap.width.toFloat()
        val height = orgBitmap.height.toFloat()
        // 创建操作图片的matrix对象
        val matrix = Matrix()
        // 计算宽高缩放率
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(
            orgBitmap, 0, 0,
            width.toInt(), height.toInt(), matrix, true
        )
    }

    /**
     * 质量压缩
     *
     * @param bitmap
     * @param quality 0 --- 100
     * @return
     */
    fun qualityCompress(bitmap: Bitmap?, quality: Int): Bitmap? {
        val baos = ByteArrayOutputStream()
        if (bitmap == null) return null
        //这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        var options = 95
        baos.reset()
        //每次减少5%质量
        options = if (quality > 5) { //避免出现options<=0
            quality
        } else {
            5
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)


        // 把压缩后的数据baos存放到ByteArrayInputStream中
        val isBm = ByteArrayInputStream(baos.toByteArray())
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    /**
     * bitmap转换成byte数组
     *
     * @param bitmap
     * @param needRecycle
     * @return
     */
    fun bitmapToByteArray(bitmap: Bitmap?, needRecycle: Boolean): ByteArray? {
        if (null == bitmap) {
            return null
        }
        if (bitmap.isRecycled) {
            return null
        }
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bitmap.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: java.lang.Exception) {
            LogUtils.e(TAG, e.toString())
        }
        return result
    }

    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    /**
     * @param bitmap
     * @param width
     * @param height
     * @return 裁剪长图分割返回
     */
    fun cropBitmap(@NonNull bitmap: Bitmap, width: Int, height: Int): List<Bitmap>? {
        val newBitmaps: MutableList<Bitmap> = ArrayList()
        val w = bitmap.width // 得到图片的宽，高
        val h = bitmap.height
        var i = 0
        while (i < h) {
            newBitmaps.add(
                Bitmap.createBitmap(
                    bitmap, 0, i, width,
                    if (h - i < height) h - i else height, null, false
                )
            )
            i += height
        }
        return newBitmaps
    }

    fun cropBitmap2(@NonNull bitmap: Bitmap, topHeight: Int, bottomHeight: Int): Bitmap? {
        var newBitmap: Bitmap? = null
        val w = bitmap.width // 得到图片的宽，高
        val h = bitmap.height
        val cropWidth = w // 裁切后所取的正方形区域边长
        val cropHeight = h - topHeight - bottomHeight
        newBitmap = Bitmap.createBitmap(bitmap, 0, topHeight, w, cropHeight, null, false)
        return newBitmap
    }

    /**
     * 保存bitmap 对象到本地
     * 默认保存到Pictures目录下， 也可自定义目录
     *
     * @param bitmap
     * @param path
     * @return
     */
    fun saveBitmap(activity: Activity, bitmap: Bitmap, path: String?, isTip: Boolean): String? {
        val sdf = SimpleDateFormat("yyyyMMdd-HHmmss")
        var file = File(
            if (path!!.isEmpty() || path == null) Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ).toString() + "/ZB/Screenshots/" else path
        )
        FileUtils.checkDirectory(file)
        val saveFileName = file.absolutePath + "/" + ScreenshotPrefixName + sdf.format(
            Date()
        ) + ".jpg"
        file = File(saveFileName)
        val b: Boolean = FileUtils.saveBitmap(bitmap, file)
        //        if (isTip)
//            Tools.toast(b ? Tools.getString(R.string.share_save_success) : Tools.getString(R.string.share_save_failed));
        notifyImageMedia(activity, file)
        return saveFileName
    }

    /**
     * 保存到应用目录下
     *
     * @param activity
     * @param bitmap
     * @param isTip
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    fun saveBitmap(activity: Activity, bitmap: Bitmap, isTip: Boolean): String? {
        val sdf = SimpleDateFormat("yyyyMMdd-HHmmss")
        var file = File(activity.getExternalFilesDir(null)!!.absolutePath + "/ZB/Screenshots/")
        FileUtils.checkDirectory(file)
        val path =
            file.absolutePath + "/" + ScreenshotPrefixName + sdf.format(Date()) + ".jpg"
        file = File(path)
        val b: Boolean = FileUtils.saveBitmap(bitmap, file)
        if (isTip) Tools.toast(
            if (b) Tools.getString(R.string.share_save_success) else Tools.getString(
                R.string.share_save_failed
            )
        )
        //        notifyImageMedia(activity, file);
        return path
    }

    /**
     * 保存图片到本地，通知系统图库
     *
     * @param context
     * @param file
     */
    private fun notifyImageMedia(context: Context, file: File) {

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                file.absolutePath, file.name, null
            )
            Tools.toast(Tools.getString(R.string.share_save_success))
        } catch (e: FileNotFoundException) {
            Tools.toast(Tools.getString(R.string.share_save_failed))
            e.printStackTrace()
        }
        // 最后通知图库更新
        context.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(File(file.path))
            )
        )
    }


    fun actionSendApp(activity: Activity) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val packageManager = activity.packageManager
        val resolveInfo = packageManager.queryIntentActivities(shareIntent, 0)
        if (!resolveInfo.isEmpty()) {
            val targetedShareIntents: List<Intent> = ArrayList()
            for (info in resolveInfo) {
                LogUtils.e("dysen", info.activityInfo.packageName + "====" + info.activityInfo.name)
            }
        }
    }

    fun compressImage(image: Bitmap, quality: Int): Bitmap? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 70, baos) //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 100
        while (baos.toByteArray().size / 1024 > quality) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset() //重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos) //这里压缩options%，把压缩后的数据存放到baos中
            options -= 10 //每次都减少10
        }
        val isBm = ByteArrayInputStream(baos.toByteArray()) //把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    fun getimage(srcPath: String?): Bitmap? {
        val newOpts = BitmapFactory.Options()
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true
        var bitmap = BitmapFactory.decodeFile(srcPath, newOpts) //此时返回bm为空
        newOpts.inJustDecodeBounds = false
        val w = newOpts.outWidth
        val h = newOpts.outHeight
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        val hh = 800f //这里设置高度为800f
        val ww = 480f //这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1 //be=1表示不缩放
        if (w > h && w > ww) { //如果宽度大的话根据宽度固定大小缩放
            be = (newOpts.outWidth / ww).toInt()
        } else if (w < h && h > hh) { //如果高度高的话根据宽度固定大小缩放
            be = (newOpts.outHeight / hh).toInt()
        }
        if (be <= 0) be = 1
        newOpts.inSampleSize = be //设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts)
        return bitmap
//        return compressImage(bitmap, 300);//压缩好比例大小后再进行质量压缩
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    fun rotaingImageView(bitmap: Bitmap, angle: Int): Bitmap? {
        //旋转图片 动作
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        // 创建新的图片
        return Bitmap.createBitmap(
            bitmap, 0, 0,
            bitmap.width, bitmap.height, matrix, true
        )
    }

    /**
     * 读取照片exif信息中的旋转角度
     * @param path 照片路径
     * @return角度
     */
    fun readPictureDegree(path: String?): Int {
        //传入图片路径
        var degree = 0
        try {
            val exifInterface = ExifInterface(path!!)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
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

}