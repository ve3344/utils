package me.lwb.utils.ext

import me.lwb.utils.ext.CloseableUtils.makeUse

/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * 生成use方法
 */
inline fun <R> Process.use(block: (Process) -> R) = makeUse(block) { destroy() }

/**
 * 生成带错误处理的use方法
 */
inline fun <R> Process.use(block: (Process) -> R, error: (Throwable) -> R) =
    makeUse(block, error) { destroy() }