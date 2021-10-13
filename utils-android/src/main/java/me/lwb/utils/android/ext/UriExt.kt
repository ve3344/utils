@file:Suppress("DEPRECATION")
package me.lwb.utils.android.ext

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import me.lwb.context.AppContext
/**
 * 根据uri获取路径
 */
fun Uri.targetPath(): String? {

    val context = AppContext.context
    val scheme = scheme ?: return path
    when (scheme) {
        ContentResolver.SCHEME_FILE -> return path
        ContentResolver.SCHEME_CONTENT -> {
            return context.contentResolver.query(
                this,
                arrayOf(MediaStore.Images.ImageColumns.DATA),
                null,
                null,
                null
            )?.use {
                if (it.moveToFirst()) {
                    it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                } else {
                    null
                }
            } ?: kotlin.runCatching { getMediaPath(context, this) }.getOrNull()

        }
        else -> return null
    }
}
/**
 * 根据uri获取媒体文件路径
 */
private fun getMediaPath(context: Context, imageUri: Uri): String? {
    when {
        DocumentsContract.isDocumentUri(context, imageUri) -> {
            when {
                imageUri.isExternalStorageDocument -> {
                    val docId = DocumentsContract.getDocumentId(imageUri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory()
                            .toString() + "/" + split[1]
                    }
                }
                imageUri.isDownloadsDocument -> {
                    val docId = DocumentsContract.getDocumentId(imageUri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        docId.toLong()
                    )
                    return getDataColumn(context, contentUri, null, null)
                }
                imageUri.isMediaDocument -> {
                    val docId = DocumentsContract.getDocumentId(imageUri)
                    val split = docId.split(":").toTypedArray()

                    val contentUri = when (split[0]) {
                        "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        else -> return null
                    }
                    val selection = MediaStore.Images.Media._ID + "=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            }
        }
        "content".equals(imageUri.scheme, ignoreCase = true) -> {
            return if (imageUri.isGooglePhotosUri) imageUri.lastPathSegment else getDataColumn(
                context,
                imageUri,
                null,
                null
            )
        }
        "file".equals(imageUri.scheme, ignoreCase = true) -> return imageUri.path

    }
    return null
}
/**
 * 根据数据库列字符串
 */
private fun getDataColumn(
    context: Context,
    uri: Uri,
    selection: String?,
    selectionArgs: Array<String>?
): String? {
    val contentResolver = context.contentResolver
    contentResolver.query(
        uri,
        arrayOf(MediaStore.Images.Media.DATA),
        selection,
        selectionArgs,
        null
    )?.use {
        if (it.moveToFirst()) {
            return it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
        }
    }
    return null
}

val Uri.isExternalStorageDocument
    get() = ("com.android.externalstorage.documents" == authority)


val Uri.isDownloadsDocument
    get() = ("com.android.providers.downloads.documents" == authority)

val Uri.isMediaDocument
    get() = ("com.android.providers.media.documents" == authority)


val Uri.isGooglePhotosUri
    get() = ("com.google.android.apps.photos.content" == authority)
