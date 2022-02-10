package me.lwb.utils.android.ext

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Created by luowenbin on 10/2/2022.
 */
private const val STORE_LAST_CLICK_TIME = "STORE_LAST_CLICK_TIME"
private fun ContextStoreManager.checkDoubleClick(checkPeriod: Long): Boolean {
    val lastClickExitTime: Long = this[STORE_LAST_CLICK_TIME] ?: 0L
    val currentTimeMillis: Long = System.currentTimeMillis()
    this[STORE_LAST_CLICK_TIME] = currentTimeMillis
    return currentTimeMillis - lastClickExitTime < checkPeriod
}

fun Fragment.checkDoubleClick(checkPeriod: Long = 1000) =
    contextStore().checkDoubleClick(checkPeriod)

fun FragmentActivity.checkDoubleClick(checkPeriod: Long = 1000) =
    contextStore().checkDoubleClick(checkPeriod)

fun FragmentActivity.doOnBackPressedTwice(
    checkPeriod: Long = 1000,
    onBackPressFirst: () -> Unit = {},
    onBackPressTwice: () -> Unit = { finish() }
) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (checkDoubleClick(checkPeriod)) {
                onBackPressTwice()
            } else {
                onBackPressFirst()
            }
        }
    })
}

