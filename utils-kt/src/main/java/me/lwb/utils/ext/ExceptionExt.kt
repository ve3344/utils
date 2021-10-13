@file:Suppress("UNUSED")

package me.lwb.utils.ext

/**
 * Created by luowenbin on 2021/10/8.
 */

/**
 * 错误栈Sequence
 */
val Throwable.errorSequence: Sequence<Throwable>
    get() = sequence {
        var error: Throwable = this@errorSequence
        while (true) {
            this.yield(error)
            error = error.cause ?: break
        }
    }

