package me.lwb.utils.utils

import me.lwb.utils.utils.LogUtils.LogPrinter
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

    var level: LogUtils.Level = LogUtils.Level.VERBOSE,

    var logPrinter: LogPrinter = LogUtils.ConsoleLogPrinter
) {
    inline fun v(block: () -> Any?) =
        log(LogUtils.Level.VERBOSE, tag, null, block)

    inline fun d(block: () -> Any?) =
        log(LogUtils.Level.DEBUG, tag, null, block)

    inline fun i(block: () -> Any?) =
        log(LogUtils.Level.INFO, tag, null, block)

    inline fun w(throwable: Throwable? = null, block: () -> Any?) =
        log(LogUtils.Level.WARNING, tag, throwable, block)

    inline fun e(throwable: Throwable? = null, block: () -> Any?) =
        log(LogUtils.Level.ERROR, tag, throwable, block)

    inline fun wtf(throwable: Throwable? = null, block: () -> Any?) =
        log(LogUtils.Level.WTF, tag, throwable, block)


    inline fun log(
        level: LogUtils.Level,
        tag: String = this.tag,
        throwable: Throwable? = null,
        block: () -> Any?
    ) {
        if (this.level <= level) {
            logPrinter.log(level, tag, block(), throwable)
        }
    }

    var loggerFactory: (childTag: String) -> BaseLogger =
        { BaseLogger("${this.tag}-$it", level, logPrinter) }

    operator fun get(childTag: String) = loggerFactory(childTag)
}

/**
 * 全局日志
 */
object LogUtils : BaseLogger(tag = "LogUtils") {

    fun interface LogPrinter {
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


    /**
     * 设置日志记录线程
     */
    fun LogPrinter.logAt(executor: Executor) =
        LogPrinter { level, tag, messageAny, throwable ->
            executor.execute {
                this@logAt.log(level, tag, messageAny, throwable)
            }
        }

    /**
     * 添加额外的日志记录器
     */
    fun LogPrinter.logAlso(other: LogPrinter) =
        LogPrinter { level, tag, messageAny, throwable ->
            this@logAlso.log(level, tag, messageAny, throwable)
            other.log(level, tag, messageAny, throwable)
        }

    /**
     * 日志过滤
     */
    fun LogPrinter.filter(
        predicate: (
            level: Level,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
        ) -> Boolean
    ) =
        LogPrinter { level, tag, messageAny, throwable ->
            if (predicate(level, tag, messageAny, throwable)) {
                this@filter.log(level, tag, messageAny, throwable)
            }
        }

    /**
     * 日志过滤
     */
    fun LogPrinter.filterLevel(minLevel: Level) =
        filter { level, _, _, _ -> level >= minLevel }

    //--------------------------
    /**
     * Android日志记录器
     */
    object ConsoleLogPrinter : LogPrinter {
        private val formatter = CsvLogFormatter()

        override fun log(
            level: Level,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
        ) {
            val message = formatter.format(level, tag, messageAny, throwable)
            val target = when (level) {
                Level.VERBOSE -> System.out
                Level.DEBUG -> System.out
                Level.INFO -> System.out
                Level.WARNING -> System.err
                Level.ERROR -> System.err
                Level.WTF -> System.err
            }
            target.print(message)
        }

    }

    class CsvLogFormatter(dateFormat: String = "yyyy-MM-dd HH:mm:ss.SSS") {
        private val format = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        fun format(
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

        private fun StringBuilder.appendItem(format: String, vararg args: Any) {
            append(",")
            append(String.format(format, *args))
        }

    }

    /**
     * Csv文件日志记录器
     */
    class CsvLogPrinter constructor(
        private val provideFile: () -> File,
        dateFormat: String = "yyyy-MM-dd HH:mm:ss.SSS"
    ) : LogPrinter {
        private val formatter = CsvLogFormatter(dateFormat)

        override fun log(
            level: Level,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
        ) {
            kotlin.runCatching {
                provideFile().appendText(formatter.format(level, tag, messageAny, throwable))
            }.onFailure { it.printStackTrace() }
        }
    }

}


