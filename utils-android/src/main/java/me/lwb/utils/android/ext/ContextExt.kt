@file:Suppress("UNUSED", "DEPRECATION")

package me.lwb.utils.android.ext

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.res.Configuration
import android.graphics.Point
import android.util.Size
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * 启动Activity
 * @param configIntent intent 配置器
 */
inline fun <reified A : Activity> Context.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

/**
 * 启动Activity
 * @param cls activity Class
 * @param configIntent intent 配置器
 */
inline fun <reified A : Activity> Context.startActivity(
    cls: Class<out Activity>,
    configIntent: Intent.() -> Unit = {}
) {
    startActivity(Intent(this, cls).apply(configIntent))
}


/**
 * 启动 ForegroundService
 * @param configIntent intent 配置器
 */
inline fun <reified S : Service> Context.startForegroundService(configIntent: Intent.() -> Unit = {}) {
    ContextCompat.startForegroundService(this, Intent(this, S::class.java).apply(configIntent))
}

/**
 * 启动 ForegroundService
 * @param configIntent intent 配置器
 */
inline fun <reified S : Service> Context.startForegroundService(
    cls: Class<out Service>,
    configIntent: Intent.() -> Unit = {}
) {
    ContextCompat.startForegroundService(this, Intent(this, cls).apply(configIntent))
}


/**
 * 判断处于竖屏
 */
fun Context.isPortrait(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

/**
 * 判断处于横屏
 */
fun Context.isLandscape(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

/**
 * 显示器大小
 */
val Context.displaySize: Size
    get() {
        val point = Point()
        windowManager.defaultDisplay.getRealSize(point)
        return Size(point.x, point.y)
    }

/**
 * 显示器旋转值
 * @see android.view.Surface.ROTATION_0
 * @see android.view.Surface.ROTATION_90
 * @see android.view.Surface.ROTATION_180
 * @see android.view.Surface.ROTATION_270
 */
val Context.displayRotation
    get() = windowManager.defaultDisplay.rotation

/**
 * 本App的包信息
 */
val Context.packageInfo: PackageInfo
    get() = packageManager.getPackageInfo(packageName, 0)

/**
 * 导航栏高度
 */
val Context.navigationBarHeight: Int
    get() = resources.getAndroidDimensionPx("navigation_bar_height")

/**
 * 状态栏高度
 */
val Context.statusBarHeight: Int
    get() = resources.getAndroidDimensionPx("status_bar_height")
