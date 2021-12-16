package me.lwb.utils.ext

/**
 * Created by luowenbin on 2021/12/14.
 */

inline fun String?.ifNullOrEmpty(block: () -> String) = if (isNullOrEmpty()) block() else this