package me.lwb.utils.android.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * Created by luowenbin on 9/2/2022.
 */
fun FragmentActivity.contextStore() = supportFragmentManager.contextStoreManager()
fun Fragment.contextStore() = childFragmentManager.contextStoreManager()

private fun FragmentManager.contextStoreManager(): ContextStoreManager {
    return getOrPut("utils.ContextStoreManager") {
        ContextStoreManagerImpl()
    }
}

fun <T> ContextStoreManager.getOrPut(key: String, creator: () -> T): T {
    val value: T? = this[key]
    if (value != null) {
        return value
    }
    return creator().also { this[key] = it }
}

interface ContextStoreManager {
    operator fun <V> get(key: String): V?
    operator fun <V> set(key: String, value: V)

}

class ContextStoreManagerImpl(private val map: MutableMap<String, Any?> = HashMap(1)) : Fragment(),
    ContextStoreManager {
    override fun <V> get(key: String) = map[key] as V
    override fun <V> set(key: String, value: V) {
        map[key] = value
    }
}

