package me.lwb.utils.android.ext

import android.content.res.Resources
/**
 * 获取尺寸
 * @param idName id名
 * @return 尺寸 in PixelSize
 */
fun Resources.getAndroidDimensionPx(idName: String): Int {
    return getDimensionPixelSize(getIdentifier(idName, "dimen", "android"))
}