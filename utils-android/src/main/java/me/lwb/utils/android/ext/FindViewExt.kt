package me.lwb.utils.android.ext

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.PopupWindow
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

/**
 * Created by luowenbin on 2021/12/7.
 */

fun <T : View> View.viewById(id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    findViewById<T>(id)
}

fun <T : View> Dialog.viewById(id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    findViewById<T>(id)
}


fun <T : View> Activity.viewById(id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    findViewById<T>(id)
}

fun <T : View> Fragment.viewById(id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    requireNotNull(view){"Content view is null"}.findViewById<T>(id)
}

fun <T : View> PopupWindow.viewById(id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    contentView.findViewById<T>(id)
}