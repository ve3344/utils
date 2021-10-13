@file:Suppress("DEPRECATION")
package me.lwb.utils.android.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

/**
 * 尝试将LifecycleOwner 转换为context
 */
fun LifecycleOwner?.asContext(): Context? {
    if (this is Context) {
        return this
    }
    if (this is Fragment) {
        return this.context
    }
    if (this is android.app.Fragment) {
        return this.activity
    }
    return null
}
/**
 * 尝试使用LifecycleOwner启动activity
 * @param intent intent
 * @param requestCode requestCode
 */
fun LifecycleOwner?.startActivityForResult(intent: Intent?, requestCode: Int): Boolean {
    if (this is Activity) {
        (this as Activity).startActivityForResult(intent, requestCode)
        return true
    }
    if (this is Fragment) {
        this.startActivityForResult(intent, requestCode)
        return true
    }
    if (this is android.app.Fragment) {
        this.startActivityForResult(intent, requestCode)
        return true
    }
    return false
}

