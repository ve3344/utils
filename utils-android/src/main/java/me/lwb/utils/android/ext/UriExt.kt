@file:Suppress("DEPRECATION")
package me.lwb.utils.android.ext

import android.content.ContentResolver
import android.content.ContentResolver.SCHEME_FILE
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.provider.MediaStore
import me.lwb.utils.android.UtilsContext
import me.lwb.utils.android.utils.CacheFileUtils
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Array

/**
 * Created by luowenbin on 2021/11/2.
 */
val Uri.isExternalStorageDocument
    get() = ("com.android.externalstorage.documents" == authority)


val Uri.isDownloadsDocument
    get() = ("com.android.providers.downloads.documents" == authority)

val Uri.isMediaDocument
    get() = ("com.android.providers.media.documents" == authority)


val Uri.isGooglePhotosUri
    get() = ("com.google.android.apps.photos.content" == authority)


val Uri.isQqUri
    get() = ("com.tencent.mtt.fileprovider" == authority)


val Uri.isHuaweiUri
    get() = ("com.huawei.hidisk.fileprovider" == authority)

/**
 * 根据uri获取路径
 */
fun Uri.toPath(): String? =
    getFilePath() ?: getDocumentPath() ?: getDownloadPath() ?: getMediaPath() ?: getContentPath()

fun Uri.saveFile(
    file: File = CacheFileUtils.createCacheFile("uri_cache", ".tmp")
): File {
    FileOutputStream(file).use { out ->
        UtilsContext.context.contentResolver
            .openInputStream(this)
            ?.use { it.copyTo(out) }
    }
    return file
}

private fun Uri.getFilePath() = if (SCHEME_FILE.equals(scheme, true)) path else null

private fun Uri.getDocumentPath(): String? {
    if (!DocumentsContract.isDocumentUri(UtilsContext.context, this)) {
        return null
    }
    val docId = docId() ?: return null
    return kotlin.runCatching {
        val (type, id) = docId.split(":")
        if (type == "primary") {
            File(Environment.getExternalStorageDirectory(), id).absolutePath
        } else {
            getStoragePath(type, id)
        }
    }.getOrNull()
}

private val CONTENT_DOWNLOADS = arrayOf(
    "content://downloads/public_downloads",
    "content://downloads/all_downloads",
    "content://downloads/my_downloads"
)

private fun Uri.getDownloadPath(): String? {
    val docId = docId() ?: return null

    if (docId.startsWith("raw:")) {
        return docId.substring(4)
    }
    val idLong = docId.removePrefix("msf:").toLongOrNull() ?: return null

    return CONTENT_DOWNLOADS.firstNotNullOfOrNull {
        ContentUris.withAppendedId(Uri.parse(it), idLong).queryFile()
    }
}

private fun Uri.queryFile(
    selection: String? = null,
    selectionArgs: kotlin.Array<String>? = null
): String? {
    if (isGooglePhotosUri) {
        return lastPathSegment?.let { return it }
    }
    if (isQqUri) {
        return path?.let {
            return File(
                Environment.getExternalStorageDirectory(),
                it.substringAfter("/QQBrowser")
            ).absolutePath
        }
    }
    if (isHuaweiUri) {
        return path?.let {
            return it.replace("/root", "")
        }
    }

    return getDataColumn(UtilsContext.context, selection, selectionArgs)
}

/**
 * 根据数据库列字符串
 */
private fun Uri.getDataColumn(
    context: Context,
    selection: String? = null,
    selectionArgs: kotlin.Array<String>? = null
) = context.contentResolver
    .query(this, arrayOf(MediaStore.MediaColumns.DATA), selection, selectionArgs, null)?.use {
        if (!it.moveToFirst()) {
            return null
        }
        val columnIndex = it.getColumnIndex(MediaStore.MediaColumns.DATA)
        if (columnIndex < 0) {
            return null
        }
        it.getString(columnIndex)
    }

private fun Any.asAnySequence() = sequence<Any?> {
    val length = Array.getLength(this@asAnySequence)
    repeat(length) {
        yield(Array.get(this@asAnySequence, it))
    }
}

private fun getStoragePath(type: String, id: String): String? {
    val mStorageManager = UtilsContext.context.storageManager()
    return runCatching {
        val storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
        val getVolumeList = StorageManager::class.java.getMethod("getVolumeList")
        val getUuid = storageVolumeClazz.getMethod("getUuid")
        val getState = storageVolumeClazz.getMethod("getState")
        val getPath = storageVolumeClazz.getMethod("getPath")
        val isPrimary = storageVolumeClazz.getMethod("isPrimary")
        val isEmulated = storageVolumeClazz.getMethod("isEmulated")
        val result = getVolumeList(mStorageManager) ?: return null
        result.asAnySequence()
            .firstNotNullOfOrNull {
                val state = getState(it)
                val mounted = Environment.MEDIA_MOUNTED == state ||
                        Environment.MEDIA_MOUNTED_READ_ONLY == state

                if (!mounted) return null

                if (isPrimary(it) as Boolean && isEmulated(it) as Boolean) {
                    return null
                }
                if (getUuid(it) != type) {
                    return null
                }

                val path = getPath(it)?.toString() ?: return null
                File(path, id).absolutePath
            }

    }.getOrNull()
}


private fun Uri.docId(): String? = DocumentsContract.getDocumentId(this)

private fun Uri.getMediaPath(): String? {
    if (!isMediaDocument) {
        return null
    }
    val docId = docId() ?: return null
    val (type, id) = docId.split(":")
    val contentUri = when (type) {
        "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        else -> return null
    }
    return contentUri.queryFile("_id=?", arrayOf(id))
}

private fun Uri.getContentPath(): String? {
    if (ContentResolver.SCHEME_CONTENT.equals(scheme, true)) {
        return null
    }
    return queryFile()
}

