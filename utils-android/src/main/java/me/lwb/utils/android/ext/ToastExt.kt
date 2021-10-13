package me.lwb.utils.android.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
/**
 * 显示toast
 * @param text toast内容 null则跳过显示
 * @param duration: 显示时间
 */
fun Context.toast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    text?:return
    Toast.makeText(this, text, duration).show()
}
/**
 * 显示toast
 * @param resId toast内容
 * @param duration: 显示时间
 */
fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}
