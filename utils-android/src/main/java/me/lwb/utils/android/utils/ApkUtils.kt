package me.lwb.utils.android.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Build
import androidx.annotation.RequiresPermission
import me.lwb.context.AppContext
import me.lwb.utils.android.ext.activityManager
import me.lwb.utils.android.ext.setDataAndType
import java.io.File
import java.lang.Exception

object ApkUtils {
    /**
     * 安装apk
     * @param context Context
     * @param apkPath apk路径
     * @return 执行安装操作成功
     */
    @SuppressLint("InlinedApi")
    @RequiresPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
    fun installApk(context: Context, apkPath: String): Boolean {
        val file = File(apkPath)
        if (!file.canRead() || file.length() <= 0) {
            return false
        }

        return kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.setDataAndType(file, "application/vnd.android.package-archive", false)
            context.startActivity(intent)
        }.isSuccess

    }

    /**
     * 获取apk信息
     * @param apkPath apk路径
     */
    fun getApkInfo(apkPath: String): PackageInfo? {
        return AppContext.context.packageManager.getPackageArchiveInfo(apkPath, 0)
    }

    /**
     * 判断是否app在前台
     * @param packageName 目标app 包名
     */
    fun isAppOnForeground(packageName: String = ContextUtils.packageName): Boolean {
        val processes = AppContext.context.activityManager.runningAppProcesses ?: return false
        return processes.any { it.importance < RunningAppProcessInfo.IMPORTANCE_BACKGROUND && it.processName == packageName }
    }
}