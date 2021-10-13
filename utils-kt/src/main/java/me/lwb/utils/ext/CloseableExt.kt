package me.lwb.utils.ext

import java.io.Closeable
/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * 关闭Closeable 忽略异常
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <reified T : Closeable?> T.closeQuietly() {
    try {
        this?.close()
    } catch (e: Throwable) {
    }
}
/**
 * 带错误处理的use
 */
inline fun <T : Closeable?, R> T.use(block: (T) -> R, error: (Throwable) -> R): R {
    return try {
        use(block)
    } catch (e: Throwable) {
        error(e)
    }
}
/**
 * 将其它非Closeable生成use方法
 */
object CloseableUtils{
    /**
     * 生成use方法
     * example Process.use(block: (Process) -> R) = makeUse(block) { destroy() }inline fun <R> Process.use(block: (Process) -> R)=makeUse(block) { destroy() }
     * @param block 传递的use内容
     * @param closeable 最终执行的close动作
     */
    inline fun <T, R> T.makeUse(block: (T) -> R, closeable: Closeable): R {
        return closeable.use { block.invoke(this) }
    }
    /**
     * 生成带错误处理的use方法
     * @param block 传递的use内容
     * @param error 错误处理
     * @param closeable 最终执行的close动作
     */
    inline fun <T, R> T.makeUse(
        block: (T) -> R,
        error: (Throwable) -> R,
        closeable: Closeable
    ): R {
        return closeable.use({ block.invoke(this) }, error)
    }


}
