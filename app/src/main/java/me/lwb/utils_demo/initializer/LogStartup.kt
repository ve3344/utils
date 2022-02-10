package me.lwb.utils_demo.initializer

import android.content.Context
import com.rousetime.android_startup.AndroidStartup
import me.lwb.utils.android.utils.AndroidLogPrinter
import me.lwb.utils.utils.LogUtils

/**
 * Created by luowenbin on 10/2/2022.
 */
@Suppress("UNUSED")
open class LogStartup : AndroidStartup<Unit>() {

    override fun callCreateOnMainThread(): Boolean = true

    override fun waitOnMainThread(): Boolean = false

    override fun create(context: Context) {
        LogUtils.apply {
            logPrinter = AndroidLogPrinter
            level = LogUtils.Level.VERBOSE
            tag="Utils-App"
        }
    }

    override fun dependencies() = null

}