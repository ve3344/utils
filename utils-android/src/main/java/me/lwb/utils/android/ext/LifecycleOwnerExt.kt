package me.lwb.utils.android.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
/**
 * 观察livedata
 * @param liveData liveData
 * @param action action
 */
inline fun <reified T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    noinline action: (t: T) -> Unit
) =
    liveData.observe(this, action)

/**
 * 观察livedata(使用viewLifecycleOwner)
 * @param liveData liveData
 * @param action action
 */
inline fun <reified T> Fragment.observe(liveData: LiveData<T>, noinline action: (t: T) -> Unit) =
    liveData.observe(viewLifecycleOwner, action)

