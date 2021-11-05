package me.lwb.utils.android.ext

import android.view.View
import android.view.ViewGroup

/**
 * Created by luowenbin on 2021/10/12.
 */

/**
 * 只显示指定子view
 * @param gone 使用gone方式，or invisible
 */
fun ViewGroup.showChildOnly(gone: Boolean = true, predicate: (child: View) -> Boolean) {
    val hidden = if (gone) View.GONE else View.INVISIBLE
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        child.visibility = if (predicate(child)) View.VISIBLE else hidden
    }
}
/**
 * 隐藏所有子view
 * @param gone 使用gone方式，or invisible
 */
fun ViewGroup.hideAllChildren(gone: Boolean = true) = showChildOnly(gone) { false }

/**
 * 隐藏父view中的其它子view
 * @param gone 使用gone方式，or invisible
 */
fun View.showFromParentOnly(gone: Boolean = true) {
    val viewGroup = parent as? ViewGroup
    viewGroup?.showChildOnly(gone) { it == this }
}
/**
 * 从父view中移除当前子view
 */
fun View.removeFromParent() {
    val viewGroup = parent as? ViewGroup
    viewGroup?.removeView(this)
}