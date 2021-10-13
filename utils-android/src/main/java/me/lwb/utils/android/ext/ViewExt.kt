@file:Suppress("UNUSED")

package me.lwb.utils.android.ext

import android.os.CountDownTimer
import android.util.Size
import android.view.View
import android.view.ViewGroup
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 设置点击并消抖
 */
private class AntiShakeClickListener(
    val shakeTime: Long = 500,
    val onShakeClick: () -> Unit = {},
    val onActionClick: (view: View) -> Unit
) : View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < shakeTime)) {
            //点击过快
            onShakeClick()
            return
        }
        lastClickTime = currentTime
        onActionClick(v)
    }
}

/**
 * 倒计时
 */
private class CountdownClickListener(
    val coolingTimeTotalSeconds: Int = 60,
    val onCoolingChange: (coolingTime: Int) -> Unit = {},
    val onCoolingClick: (coolingTime: Int) -> Unit = {},
    val onActionClick: (view: View) -> Unit
) : View.OnClickListener {
    private var coolingTime = 0
    var countDownTimer: CountDownTimer? = null

    init {
        onCoolingChange(coolingTime)
    }

    override fun onClick(v: View) {
        if (coolingTime > 0) {
            onCoolingClick(coolingTime)
        } else {
            startCountdown()
            onActionClick(v)
        }
    }

    fun stopCountdown() {
        countDownTimer?.cancel()
        countDownTimer = null
        coolingTime = 0
        onCoolingChange(coolingTime)
    }

    private fun startCountdown() {
        stopCountdown()
        coolingTime = coolingTimeTotalSeconds
        object : CountDownTimer(coolingTime * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                if (coolingTime > 0) {
                    --coolingTime
                    onCoolingChange(coolingTime)
                }
            }

            override fun onFinish() {
                coolingTime = 0
            }

        }.also { countDownTimer = it }
            .start()
    }
}

private class MultipleClickListener(
    val atLeastCount: Int = 2,
    val maxTime: Long = 2000,
    val onProgressClick: (clickedCount: Int) -> Unit = {},
    val onCompleteClick: (view: View) -> Unit
) : View.OnClickListener {
    private var lastClickTime = 0L
    private var clickCount = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime == 0L || (currentTime - lastClickTime > maxTime)) {
            //第一次点击，或者离上次时间太长
            clickCount = 0//reset
        }
        ++clickCount
        lastClickTime = currentTime
        if (clickCount >= atLeastCount) {
            clickCount = 0
            lastClickTime = 0L
            onCompleteClick(v)
        } else {
            onProgressClick(clickCount)
        }
    }

}

/**
 * 设置点击并倒计时（发送验证码）
 * @param coolingTimeTotalSeconds 倒计时总时间
 * @param onCoolingChange 倒计时变更回调
 * @param onCoolingClick 在倒计时期间点击
 * @param onActionClick 点击事件
 */
fun View.doOnClickCountdown(
    coolingTimeTotalSeconds: Int = 60,
    onCoolingChange: (coolingTime: Int) -> Unit = {},
    onCoolingClick: (coolingTime: Int) -> Unit = {},
    onActionClick: (view: View) -> Unit
) {

    setOnClickListener(
        CountdownClickListener(
            coolingTimeTotalSeconds,
            onCoolingChange,
            onCoolingClick,
            onActionClick
        )
    )
}

/**
 * 设置点击并消抖
 * @param shakeTime 消抖时间
 * @param onShakeClick 无效的点击事件
 * @param onClick 点击事件
 */
fun View.doOnClickAntiShake(
    shakeTime: Long = 500,
    onShakeClick: () -> Unit = {},
    onClick: (view: View) -> Unit
) {
    setOnClickListener(AntiShakeClickListener(shakeTime, onShakeClick, onClick))
}

/**
 * 监听在一定时间内连续点击多次（首页双击退出）
 * @param triggerCount onCompleteClick 触发时的点击次数
 * @param maxTime 视为连续点击的最大时间（ms）
 * @param onProgressClick 连续点击
 * @param onProgressClick 连续点击完成
 */
fun View.doOnClickMultiple(
    triggerCount: Int = 2,
    maxTime: Long = 500,
    onProgressClick: (count: Int) -> Unit = {},
    onCompleteClick: (view: View) -> Unit
) {
    setOnClickListener(
        MultipleClickListener(
            triggerCount,
            maxTime,
            onProgressClick,
            onCompleteClick
        )
    )
}

/**
 * 配置LayoutParams
 * @param config 配置器
 */
inline fun <reified V : ViewGroup.LayoutParams> View.configLayoutParams(config: V.() -> Unit) {
    layoutParams = ((layoutParams ?: newParams<V>()) as V).apply(config)
}
/**
 * 新建LayoutParams
 */
inline fun <reified V : ViewGroup.LayoutParams> newParams(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
): V =
    V::class.java.getConstructor(Int::class.java, Int::class.java)
        .newInstance(width, height)

/**
 * 等待按下
 */
suspend fun View.awaitClick() = suspendCoroutine<View> { c ->
    setOnClickListener { c.resume(this) }
}
/**
 * 等待view post （绘制完成）
 */
suspend fun View.awaitSize() = suspendCoroutine<Unit> { c ->
    post { c.resume(Unit) }
}
