@file:Suppress("UNUSED")

package me.lwb.utils.android.ext

import android.util.DisplayMetrics
import me.lwb.utils.android.UtilsContext

private val displayMetrics: DisplayMetrics
    get() = UtilsContext.context.resources.displayMetrics

/**
 * dp转px
 */
val Float.dp: Int
    get() = (this * displayMetrics.density + 0.5f).toInt()

/**
 * sp转px
 */
val Float.sp: Int
    get() = (this * displayMetrics.scaledDensity + 0.5f).toInt()

/**
 * dp转px
 */
val Int.dp: Int
    get() = (this * displayMetrics.density + 0.5f).toInt()

/**
 * sp转px
 */
val Int.sp: Int
    get() = (this * displayMetrics.scaledDensity + 0.5f).toInt()
