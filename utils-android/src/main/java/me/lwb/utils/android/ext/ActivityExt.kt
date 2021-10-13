@file:Suppress("UNUSED", "DEPRECATION")

package me.lwb.utils.android.ext

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner

/**
 * 注册BroadcastReceiver，onDestroy时注销
 * @param intentFilter 过滤器
 * @param onReceive 处理
 */
fun <T> T.registerReceiver(
    intentFilter: IntentFilter,
    onReceive: (intent: Intent?) -> Unit
): BroadcastReceiver where T : Context, T : LifecycleOwner {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            onReceive(intent)
        }
    }
    this.registerReceiver(receiver, intentFilter)

    doOnDestroyOnce {
        this.unregisterReceiver(receiver)
    }
    return receiver
}
/**
 * 注册BroadcastReceiver，onDestroy时注销
 * @param actions 过滤actions
 * @param onReceive 处理
 */
fun <T> T.registerReceiver(
    vararg actions: String,
    onReceive: (intent: Intent?) -> Unit
) where T : Context, T : LifecycleOwner {
    registerReceiver(IntentFilter().apply { actions.forEach { addAction(it) } }, onReceive)
}


private inline fun <T> View.withDrawingCache(block: (Bitmap) -> T): T {
    isDrawingCacheEnabled = true
    buildDrawingCache()
    val result = block(drawingCache)
    destroyDrawingCache()
    return result
}

/**
 * 截图
 * @param includeStatusBar 是否包括状态栏
 */
fun Activity.screenshot(includeStatusBar: Boolean = true): Bitmap {
    return window.decorView.withDrawingCache {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        if (includeStatusBar) {
            Bitmap.createBitmap(it, 0, 0, dm.widthPixels, dm.heightPixels)
        } else {
            val statusBarHeight = statusBarHeight
            Bitmap.createBitmap(
                it,
                0,
                statusBarHeight,
                dm.widthPixels,
                dm.heightPixels - statusBarHeight
            )
        }
    }

}


/**
 * 设置全屏
 */
fun Activity.setFullScreen() {
    if (this is AppCompatActivity) {
        this.supportActionBar?.hide()
    }
    window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
}

/**
 * 设置状态栏黑暗模式
 * @param dark 是否黑暗模式
 */
fun Activity.setStatusBarMode(dark: Boolean) {
    window.decorView.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            systemUiVisibility = if (dark) {
                systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv() or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }

    }

}

/**
 * 显示输入法
 * @param show 是否显示
 */
fun Activity.showInputMethod(show: Boolean) {
    val view = this.currentFocus ?: View(this)
    if (show) {
        view.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
        this.inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    } else {
        this.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }
}



