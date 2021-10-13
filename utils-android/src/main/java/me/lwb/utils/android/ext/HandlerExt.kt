package me.lwb.utils.android.ext

import android.os.Handler

/**
 * 延迟执行
 * @param delayMillis 延迟时间
 * @param runnable 执行内容
 */
fun Handler.postDelay(delayMillis: Long, runnable: () -> Unit) = postDelayed(runnable, delayMillis)

