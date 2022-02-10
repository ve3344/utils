package me.lwb.utils.android.utils

import android.util.Log
import me.lwb.utils.utils.LogUtils
import me.lwb.utils.utils.LogUtils.Level

/**
 * Created by luowenbin on 2021/10/22.
 */

/**
 * Android日志记录器
 */
object AndroidLogPrinter : LogUtils.LogPrinter {
    override fun log(
        level: Level,
        tag: String,
        messageAny: Any?,
        throwable: Throwable?
    ) {
        val message = messageAny.toString()
        when (level) {
            Level.VERBOSE -> Log.v(tag, message, throwable)
            Level.DEBUG -> Log.d(tag, message, throwable)
            Level.INFO -> Log.i(tag, message, throwable)
            Level.WARNING -> Log.w(tag, message, throwable)
            Level.ERROR -> Log.e(tag, message, throwable)
            Level.WTF -> Log.wtf(tag, message, throwable)
        }
    }

}
