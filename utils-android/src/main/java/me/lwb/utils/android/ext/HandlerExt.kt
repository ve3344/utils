package me.lwb.utils.android.ext

import android.os.Handler
import android.os.Looper


private val mainThreadHandler by lazy { Handler(Looper.getMainLooper()) }

/**
 * 在主线程执行
 * @param delayMillis 延迟时间
 * @param runnable 执行内容
 */
fun doOnMainThread(delayMillis: Long = 0L, runnable: () -> Unit) {
    val handler = mainThreadHandler

    if (delayMillis > 0) {
        handler.postDelay(delayMillis, runnable)
    }
    if (Looper.myLooper() != Looper.getMainLooper()) {
        handler.post(runnable)
    } else {
        runnable()
    }
}

/**
 * 延迟执行
 * @param delayMillis 延迟时间
 * @param runnable 执行内容
 */
fun Handler.postDelay(delayMillis: Long, runnable: () -> Unit) = postDelayed(runnable, delayMillis)

