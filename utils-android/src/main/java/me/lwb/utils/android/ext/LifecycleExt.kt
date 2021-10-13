package me.lwb.utils.android.ext

import androidx.lifecycle.*

/**
 * 监听LifecycleOwner 生命周期
 * @param lifecycleOwner lifecycleOwner
 * @param targetEvent 目标事件
 * @param block 处理内容，返回true结束监听
 */
private class LifecycleListener(
    lifecycleOwner: LifecycleOwner,
    private val targetEvent: Lifecycle.Event,
    block: () -> Boolean
) : LifecycleEventObserver {

    private var lifecycleOwner: LifecycleOwner? = lifecycleOwner

    private var block: (() -> Boolean?)? = block


    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (targetEvent == event) {
            if (block?.invoke() == true) {
                destroy()
            }
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            destroy()
        }
    }

    /**
     * 清理引用
     */
    private fun destroy() {
        if (lifecycleOwner == null) {
            return
        }
        lifecycleOwner?.lifecycle?.removeObserver(this)
        lifecycleOwner = null
        block = null
    }
}

/**
 * 当到达指定生命周期状态时执行
 * @param event 生命周期事件
 * @param block 处理内容
 */
fun LifecycleOwner.doOnLifecycle(event: Lifecycle.Event, block: () -> Boolean) {
    LifecycleListener(this, event, block)
}


/**
 * 当到达指定生命周期状态时执行（仅一次）
 * @param event 生命周期事件
 * @param block 处理内容
 */
inline fun LifecycleOwner.doOnLifecycleOnce(event: Lifecycle.Event, crossinline block: () -> Unit) {
    doOnLifecycle(event) { block(); true }
}

/**
 * 当到达生命周期onDestroy执行（仅一次）
 * @param block 处理内容
 */
inline fun LifecycleOwner.doOnDestroyOnce(crossinline block: () -> Unit) =
    doOnLifecycleOnce(Lifecycle.Event.ON_DESTROY, block)



