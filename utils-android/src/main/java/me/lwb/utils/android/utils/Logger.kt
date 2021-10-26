package me.lwb.utils.android.utils

import android.util.Log
import me.lwb.context.AppContext
import me.lwb.utils.android.utils.Logger.LogHandler
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

/**
 * Created by luowenbin on 2021/10/22.
 */

/**
 * 日志
 */
open class BaseLogger(
    var tag: String = "LOG",

    var level: Logger.Level = Logger.Level.VERBOSE,

    var logHandler: LogHandler = Logger.AndroidLogHandler
) {
    inline fun v(block: () -> Any?) =
        Logger.log(Logger.Level.VERBOSE, tag, null, block)

    inline fun d(block: () -> Any?) =
        Logger.log(Logger.Level.DEBUG, tag, null, block)

    inline fun i(block: () -> Any?) =
        Logger.log(Logger.Level.INFO, tag, null, block)

    inline fun w(throwable: Throwable? = null, block: () -> Any?) =
        Logger.log(Logger.Level.WARNING, tag, throwable, block)

    inline fun e(throwable: Throwable? = null, block: () -> Any?) =
        Logger.log(Logger.Level.ERROR, tag, throwable, block)

    inline fun wtf(throwable: Throwable? = null, block: () -> Any?) =
        Logger.log(Logger.Level.WTF, tag, throwable, block)

    operator fun get(tag: String) = BaseLogger("${this.tag}-$tag", level, logHandler)
}
/**
 * 全局日志
 */
object Logger : BaseLogger() {

    fun interface LogHandler {
        fun log(
            level: Level,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
        )
    }

    /**
     * 日志等级
     */
    enum class Level(val shortName: String) : Comparable<Level> {
        VERBOSE("V"),
        DEBUG("D"),
        INFO("I"),
        WARNING("W"),
        ERROR("E"),
        WTF("WTF")
    }


    inline fun log(
        level: Level,
        tag: String = this.tag,
        throwable: Throwable? = null,
        block: () -> Any?
    ) {
        if (this.level <= level) {
            logHandler.log(level, tag, block(), throwable)
        }
    }

    /**
     * 设置日志记录线程
     */
    fun LogHandler.logAt(executor: Executor) =
        LogHandler { level, tag, messageAny, throwable ->
            executor.execute {
                this@logAt.log(level, tag, messageAny, throwable)
            }
        }

    /**
     * 添加额外的日志记录器
     */
    fun LogHandler.logAlso(other: LogHandler) =
        LogHandler { level, tag, messageAny, throwable ->
            this@logAlso.log(level, tag, messageAny, throwable)
            other.log(level, tag, messageAny, throwable)
        }

    //--------------------------
    /**
     * Android日志记录器
     */
    object AndroidLogHandler : LogHandler {
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
    /**
     * Csv文件日志记录器
     */
    class CsvLogHandler constructor(
        private val provideFile: () -> File = defaultAndroidLogFile(),
        dateFormat: String = "yyyy-MM-dd HH:mm:ss.SSS"
    ) : LogHandler {
        private val format = SimpleDateFormat(dateFormat, Locale.ENGLISH)

        companion object Utils {
            fun defaultAndroidLogFile(): () -> File = {
                File(AppContext.context.getExternalFilesDir("log"), "log${dateNow()}.csv")
            }

            fun dateNow(): String = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date())
        }

        private fun getCsvLine(
            level: Level,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
        ): String {
            val message = messageAny.toString() + (throwable?.toString() ?: "")
            val date = Date()
            return buildString {
                append(date.time)
                appendItem(format.format(date))
                appendItem("%3s", level.shortName)
                appendItem("%10s", tag)
                appendItem(message.replace("\n", "<br>"))
                append("\n")
            }
        }

        private fun StringBuilder.appendItem(format: String, vararg args: Any?) {
            append(",")
            append(String.format(format, *args))
        }

        override fun log(
            level: Level,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
        ) {
            kotlin.runCatching {
                provideFile().appendText(getCsvLine(level, tag, messageAny, throwable))
            }.onFailure { it.printStackTrace() }
        }
    }

}


