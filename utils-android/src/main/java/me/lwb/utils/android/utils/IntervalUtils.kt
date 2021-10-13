package me.lwb.utils.android.utils

import android.os.CountDownTimer

object IntervalUtils {

    /**
     * 每1s定时执行
     * @param totalSecond 总时长
     * @param tick 执行内容，主线程
     */
    inline fun intervalSecond(
        totalSecond: Int,
        crossinline tick: (millisUntilFinished: Long) -> Unit
    ) =
        interval(totalSecond * 1000L, 1000, tick)

    /**
     * 每1s定时执行，无限时长(仅debug使用)
     * @param tick 执行内容，主线程
     */
    inline fun intervalSecondForever(crossinline tick: (millisUntilFinished: Long) -> Unit) =
        interval(Long.MAX_VALUE, 1000, tick)

    /**
     * 每一段时间定时执行
     * @param totalTime 总时长
     * @param tick 执行内容，主线程
     */
    inline fun interval(
        totalTime: Long,
        intervalTime: Long,
        crossinline tick: (millisUntilFinished: Long) -> Unit,
        crossinline finish: () -> Unit = {}
    ): CountDownTimer {
        return object : CountDownTimer(totalTime, intervalTime) {
            override fun onTick(millisUntilFinished: Long) {
                tick(millisUntilFinished)
            }

            override fun onFinish() {
                finish()
            }

        }.also {
            it.start()
        }
    }
}