package me.lwb.utils.android.ext

import android.view.Window
import android.view.WindowManager
/**
 * 配置窗口属性
 * @param config 配置器
 */
fun Window.configAttributes(config: WindowManager.LayoutParams.() -> Unit) {
    attributes = (attributes).apply(config)
}
