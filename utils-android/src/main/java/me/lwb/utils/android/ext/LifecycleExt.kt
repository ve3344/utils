package me.lwb.utils.android.ext

import androidx.lifecycle.*

/**
 * 当到达指定生命周期状态时执行
 * @param targetEvent 生命周期事件
 * @param block 处理内容
 */
fun LifecycleOwner.doOnLifecycle(targetEvent: Lifecycle.Event, block: () -> Unit) {
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (targetEvent == event) {
                block()
            }
        }
    })
}


/**
 * 当到达指定生命周期状态时执行（仅一次）
 * @param targetEvent 生命周期事件
 * @param block 处理内容
 */
fun LifecycleOwner.doOnLifecycleOnce(targetEvent: Lifecycle.Event, block: () -> Unit) {
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (targetEvent == event) {
                block()
                lifecycle.removeObserver(this)
            }
        }
    })
}

/**
 * 当到达生命周期onDestroy执行（仅一次）
 * @param block 处理内容
 */
fun LifecycleOwner.doOnDestroyOnce(block: () -> Unit) =
    doOnLifecycleOnce(Lifecycle.Event.ON_DESTROY, block)




