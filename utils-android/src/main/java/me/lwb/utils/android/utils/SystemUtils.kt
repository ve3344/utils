package me.lwb.utils.android.utils

import java.io.Closeable

/**
 * Created by luowenbin on 2021/10/20.
 */
object SystemUtils {
    /**
     * 获取系统属性
     */
    fun getProp(name: String): String {
        val process = Runtime.getRuntime().exec("getprop $name")
        Closeable{ process.destroy() }.use {
            val text = process.inputStream.bufferedReader().readText()
            process.destroy()
            return text
        }

    }
}