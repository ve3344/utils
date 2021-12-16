package me.lwb.utils.android.ext

import android.graphics.Rect
import android.view.View

/**
 * Created by luowenbin on 2021/12/15.
 */

fun View.measureRect(): Rect {
    val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(w, h)

    return Rect(0, 0, measuredWidth, measuredHeight)

}

fun View.getViewBoundsOfScreen(): Rect {
    val position = IntArray(2)
    getLocationInWindow(position)
    return Rect(position[0], position[1], position[0] + width, position[1] + height)
}