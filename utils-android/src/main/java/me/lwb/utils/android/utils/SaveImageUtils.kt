package me.lwb.utils.android.utils

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore.Images.Media
import androidx.annotation.RequiresPermission
import me.lwb.context.AppContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Created by luowenbin on 2021/9/30.
 */
object SaveImageUtils {
    /**
     * 获取压缩格式的拓展名
     */
    @Suppress("DEPRECATION")
    private fun Bitmap.CompressFormat.getExtension(): String {
        return when (this) {
            Bitmap.CompressFormat.JPEG -> "jpeg"
            Bitmap.CompressFormat.PNG -> "png"
            Bitmap.CompressFormat.WEBP -> "webp"
            else -> "webp"
        }
    }


    /**
     * 获取文件名
     */
    private val Options.filename
        get() = name + "." + format.getExtension()

    /**
     * 保存选项
     */
    data class Options(
        /**
         * 保存格式
         */
        val format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        /**
         * 文件名
         */
        val name: String = "image_${System.currentTimeMillis()}",
        /**
         * 保存的子目录
         */
        val dir: String = "",
        /**
         * 压缩图片质量
         */
        val quality: Int = 90
    )

    /**
     * 保存图片
     * @param bitmap Bitmap
     * @param options 保存选项
     */
    @Suppress("DEPRECATION")
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImage(bitmap: Bitmap, options: Options = Options()): Boolean {
        val resolver = AppContext.context.contentResolver

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val path = if (options.dir.isEmpty()) {
                Environment.DIRECTORY_PICTURES
            } else {
                File(Environment.DIRECTORY_PICTURES, options.dir).path
            }

            val values = contentValues {
                put(Media.MIME_TYPE, "image/${options.format.getExtension()}")

                put(Media.DISPLAY_NAME, options.filename)
                put(Media.RELATIVE_PATH, path)
                put(Media.IS_PENDING, true)
            }
            val uri = resolver.insert(Media.EXTERNAL_CONTENT_URI, values) ?: return false

            bitmap.saveTo(resolver.openOutputStream(uri), options.format, options.quality)

            values.clear()
            values.put(Media.IS_PENDING, false)
            resolver.update(uri, values, null, null)
            return true
        } else {
            val dir = if (options.dir.isEmpty()) {
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            } else {
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    options.dir
                )
            }
            if (!dir.exists() && !dir.mkdirs()) {
                return false
            }
            val file = File(dir, options.filename)
            bitmap.saveTo(FileOutputStream(file), options.format, options.quality)
            val values = contentValues {
                put(Media.MIME_TYPE, "image/${options.format.getExtension()}")
                put(Media.DATA, file.absolutePath)
            }
            resolver.insert(Media.EXTERNAL_CONTENT_URI, values)
            AppContext.context.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(file)
                )
            )
            return true
        }
    }


    private fun contentValues(block: ContentValues.() -> Unit): ContentValues {
        return ContentValues().apply {
            put(Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            put(Media.DATE_TAKEN, System.currentTimeMillis());
        }.apply(block)
    }

    private fun Bitmap.saveTo(
        outputStream: OutputStream?,
        format: Bitmap.CompressFormat,
        quality: Int
    ): Boolean {
        return kotlin.runCatching {
            outputStream ?: return false
            outputStream.use {
                compress(format, quality, outputStream)
            }
        }.onFailure { it.printStackTrace() }
            .isSuccess

    }

}