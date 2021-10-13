@file:Suppress("DEPRECATION")
package me.lwb.utils.android.ext

import android.hardware.Camera

/**
 * 配置camera参数
 * @param config 配置器
 */
inline fun Camera.configParameters(config: Camera.Parameters.() -> Unit) {
    parameters = (parameters).apply(config)
}

