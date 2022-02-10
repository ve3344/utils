@file:Suppress("NOTHING_TO_INLINE")

package me.lwb.utils.android.ext

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

/**
 * 获取尺寸
 * @param idName id名
 * @return 尺寸 in PixelSize
 */
fun Resources.getAndroidDimensionPx(idName: String): Int {
    return getDimensionPixelSize(getIdentifier(idName, "dimen", "android"))
}


inline fun Context.getColorCompat(@ColorRes resId: Int): Int {
    return ResourcesCompat.getColor(resources, resId, theme)
}

inline fun Context.getColorStateListCompat(@ColorRes resId: Int): ColorStateList? {
    return ResourcesCompat.getColorStateList(resources, resId, theme)
}

inline fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, resId, theme)
}

inline fun Context.getDrawableForDensityCompat(@DrawableRes resId: Int, density: Int): Drawable? {
    return ResourcesCompat.getDrawableForDensity(resources, resId, density, theme)
}

inline fun Context.getFontCompat(@FontRes resId: Int): Typeface? {
    return ResourcesCompat.getFont(this, resId)
}
