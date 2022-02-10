package me.lwb.utils.android.utils

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import androidx.annotation.RawRes
import androidx.core.content.FileProvider
import me.lwb.utils.android.UtilsContext
import java.io.File


object UriUtils {
    private val packageName
        get() = ContextUtils.packageName
    private const val RESOURCE_URI_PREFIX = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"

    private const val ASSET_URI_PREFIX = ContentResolver.SCHEME_FILE + "://" + "/android_asset/"

    /**
     * uri是否属于Resource
     * @param uri uri
     */
    fun isResourceUri(uri: String) = uri.startsWith(RESOURCE_URI_PREFIX)

    /**
     * uri是否属于Asset
     * @param uri uri
     */
    fun isAssetUri(uri: String) = uri.startsWith(ASSET_URI_PREFIX)

    /**
     * 生成Resource文件uri
     * @param resName res 名字
     */
    fun fromResource(resName: String): String {
        return "$RESOURCE_URI_PREFIX$packageName/$resName"
    }

    /**
     * 生成Resource文件uri
     * @param resId res id
     */
    fun fromResource(@RawRes resId: Int): String {
        return "$RESOURCE_URI_PREFIX$packageName/$resId"
    }

    /**
     * 生成Asset文件uri
     * @param path Asset 路径
     */
    fun fromAsset(path: String): String {
        return ASSET_URI_PREFIX + path
    }

    //---------------------------
    private var authorityConfig: String? = null

    val authority: String
        get() = authorityConfig ?: "$packageName.fileprovider"

    /**
     * 设置认证
     * @param authority 认证
     */
    fun setAuthority(authority: String) {
        authorityConfig = authority
    }
    /**
     * 路径转uri
     */
    fun getUriByPath(path: String): Uri {
        return File(path).androidUri()
    }
}
/**
 * 文件转uri
 */
fun File.androidUri(): Uri
    = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(UtilsContext.context, UriUtils.authority, this)
    } else {
        Uri.fromFile(this)
    }

