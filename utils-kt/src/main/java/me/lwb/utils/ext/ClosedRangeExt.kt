@file:Suppress("NOTHING_TO_INLINE")

package me.lwb.utils.ext

/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * 长度int值的数量
 */
val ClosedRange<Int>.length
    get() = (endInclusive - start + 1).coerceAtLeast(0)

/**
 * 减少offset偏移
 * @param o 偏移量
 */
operator fun ClosedRange<Int>.minus(o: Int): ClosedRange<Int> {
    return start - o..endInclusive - o
}

/**
 * 增加offset偏移
 * @param o 偏移量
 */
operator fun ClosedRange<Int>.plus(o: Int): ClosedRange<Int> {
    return start + o..endInclusive + o
}

/**
 * 范围缩小处理
 * @param factor 比例值
 */
operator fun ClosedRange<Int>.div(factor: Int): ClosedRange<Int> {
    return start / factor..endInclusive / factor
}

/**
 * 范围放大处理
 * @param factor 比例值
 */
operator fun ClosedRange<Int>.times(factor: Int): ClosedRange<Int> {
    return start * factor..endInclusive * factor
}

/**
 * 长度
 */
val ClosedRange<Float>.length
    get() = (endInclusive - start).coerceAtLeast(0f)

/**
 * 减少offset偏移
 * @param o 偏移量
 */
operator fun ClosedRange<Float>.minus(o: Float): ClosedRange<Float> {
    return start - o..endInclusive - o
}

/**
 * 增加offset偏移
 * @param o 偏移量
 */
operator fun ClosedRange<Float>.plus(o: Float): ClosedRange<Float> {
    return start + o..endInclusive + o
}

/**
 * 范围缩小处理
 * @param factor 比例值
 */
operator fun ClosedRange<Float>.div(factor: Float): ClosedRange<Float> {
    return start / factor..endInclusive / factor
}

/**
 * 范围放大处理
 * @param factor 比例值
 */
operator fun ClosedRange<Float>.times(factor: Float): ClosedRange<Float> {
    return start * factor..endInclusive * factor
}

/**
 * 长度
 */
val ClosedRange<Double>.length
    get() = (endInclusive - start).coerceAtLeast(0.0)

/**
 * 减少offset偏移
 * @param o 偏移量
 */
operator fun ClosedRange<Double>.minus(o: Double): ClosedRange<Double> {
    return start - o..endInclusive - o
}

/**
 * 增加offset偏移
 * @param o 偏移量
 */
operator fun ClosedRange<Double>.plus(o: Double): ClosedRange<Double> {
    return start + o..endInclusive + o
}

/**
 * 范围缩小处理
 * @param factor 比例值
 */
operator fun ClosedRange<Double>.div(factor: Double): ClosedRange<Double> {
    return start / factor..endInclusive / factor
}

/**
 * 范围放大处理
 * @param factor 比例值
 */
operator fun ClosedRange<Double>.times(factor: Double): ClosedRange<Double> {
    return start * factor..endInclusive * factor
}

/**
 * 调整到区间中
 * @param target 目标区间
 */
@JvmName("coerceInInt")
fun ClosedRange<Int>.coerceIn(
    target: ClosedRange<Int>
) = start.coerceAtLeast(target.start)..endInclusive.coerceAtMost(target.endInclusive)

/**
 * 调整到区间中
 * @param target 目标区间
 */
@JvmName("coerceInFloat")
fun ClosedRange<Float>.coerceIn(
    target: ClosedRange<Float>
) = start.coerceAtLeast(target.start)..endInclusive.coerceAtMost(target.endInclusive)

/**
 * 调整到区间中
 * @param target 目标区间
 */
@JvmName("coerceInLong")
fun ClosedRange<Long>.coerceIn(
    target: ClosedRange<Long>
) = start.coerceAtLeast(target.start)..endInclusive.coerceAtMost(target.endInclusive)

/**
 * 调整到区间中
 * @param target 目标区间
 */
@JvmName("coerceInDouble")
fun ClosedRange<Double>.coerceIn(
    target: ClosedRange<Double>
) = start.coerceAtLeast(target.start)..endInclusive.coerceAtMost(target.endInclusive)
