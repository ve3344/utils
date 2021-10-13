package me.lwb.utils.android.ext

import androidx.lifecycle.Observer

/**
 * 过滤元素
 */
fun <T> Observer<in T>.filter(filter: (value: T) -> Boolean) = Observer<T> {
    if (!filter(it)) {
        onChanged(it)
    }
}

/**
 * 过滤null元素
 */
fun <T> Observer<in T>.filterNotNull() = filter { it != null }
/**
 * 跳过多个元素
 * @param count 跳过数量
 */
fun <T> Observer<in T>.skip(count: Int) = filter(SkipPredicate(count))

/**
 * 跳过第一个元素
 */
fun <T> Observer<in T>.skipFirst() = skip(1)

internal class SkipPredicate<T>(private val count: Int) : (T) -> Boolean {
    private var skipped = 0
    override fun invoke(p1: T): Boolean {
        if (skipped < count) {
            ++skipped
            return false
        }
        return true
    }
}