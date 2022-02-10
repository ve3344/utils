@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE","UNUSED")
package me.lwb.utils.ext
/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * boolean三目运算
 */
inline fun <T> Boolean.map(trueValue: T, falseValue: T): T = if (this) trueValue else falseValue
/**
 * true 时执行
 */
inline fun Boolean.onTrue(action: () -> Unit): Boolean {
    if (this) {
        action()
    }
    return this
}
/**
 * false 时执行
 */
inline fun Boolean.onFalse(action: () -> Unit): Boolean {
    if (!this) {
        action()
    }
    return this
}