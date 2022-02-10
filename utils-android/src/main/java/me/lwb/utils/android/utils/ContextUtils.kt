package me.lwb.utils.android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import me.lwb.utils.android.UtilsContext
import me.lwb.utils.android.ext.packageInfo
import java.io.File
import java.util.concurrent.Executor


@Suppress("DEPRECATION","UNUSED")
object ContextUtils {
    private val context
        get() = UtilsContext.context


    /**
     * app 名
     */
    val appName: String
        get() = applicationInfo.loadLabel(context.packageManager).toString()

    /**
     * app 是否可调试
     */
    val debuggable
        get() = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

    /**
     * app 应用信息
     */
    val applicationInfo: ApplicationInfo
        get() = packageInfo.applicationInfo

    /**
     * app 包名
     */
    val packageName: String
        get() = context.packageName

    /**
     * app 包信息
     */
    val packageInfo: PackageInfo by lazy(LazyThreadSafetyMode.NONE) { context.packageInfo }

    /**
     * app 版本名
     */
    val versionName:String
        get() = packageInfo.versionName

    /**
     * app 版本号
     */
    val versionCode
        get() = packageInfo.versionCode

    /**
     * resources资源
     */
    val resources: Resources
        get() = context.resources

    /**
     * assets资源
     */
    val assets: AssetManager
        get() = context.assets

    /**
     * 主题
     */
    val theme: Resources.Theme
        get() = context.theme

    /**
     * 签名
     */
    val signatures: Array<Signature>
        @SuppressLint("PackageManagerGetSignatures")
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val signingInfo = context.packageManager
                    .getPackageInfo(
                        packageName,
                        PackageManager.GET_SIGNING_CERTIFICATES
                    )?.signingInfo ?: return emptyArray()

                if (signingInfo.hasMultipleSigners()) {
                    signingInfo.apkContentsSigners
                } else {
                    signingInfo.signingCertificateHistory
                }
            } else {
                context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                )?.signatures ?: return emptyArray()

            }
        }

    /**
     * 获取文本
     * @param resId id
     */
    fun getText(@StringRes resId: Int) = context.getText(resId)

    /**
     * 获取数据库路径
     * @param name 数据库名
     */
    fun getDatabasePath(name: String): File = context.getDatabasePath(name)

    /**
     * 获取文件流路径
     * @param name 数据库名
     */
    fun getFileStreamPath(name: String): File = context.getFileStreamPath(name)

    /**
     * 获取SharedPreferences
     * @param name 数据库名
     * @param mode 模式
     */
    fun getSharedPreferences(name: String, mode: Int = Context.MODE_PRIVATE): SharedPreferences =
        context.getSharedPreferences(name, mode)

    /**
     * 获取颜色
     * @param resId
     */
    fun getColor(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)
    /**
     * 获取颜色
     * @param resId
     */
    fun getColorStateList(@ColorRes resId: Int) = ContextCompat.getColorStateList(context, resId)
    /**
     * 获取Drawable
     * @param resId
     */
    fun getDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(context, resId)


    fun getCodeCacheDir(): File = ContextCompat.getCodeCacheDir(context)

    fun getDataDir() = ContextCompat.getDataDir(context)

    fun getNoBackupFilesDir() = ContextCompat.getNoBackupFilesDir(context)

    fun getExternalCacheDirs(): Array<File> = ContextCompat.getExternalCacheDirs(context)

    fun getExternalFilesDirs(type: String?): Array<File> = ContextCompat.getExternalFilesDirs(context, type)

    fun getObbDirs(): Array<File> = ContextCompat.getObbDirs(context)

    fun getMainExecutor(): Executor = ContextCompat.getMainExecutor(context)

    fun checkSelfPermission(permission: String) =
        ContextCompat.checkSelfPermission(context, permission)


}