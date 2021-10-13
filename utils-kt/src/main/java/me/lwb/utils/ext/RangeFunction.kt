@file:Suppress("NOTHING_TO_INLINE")

package me.lwb.utils.ext

/**
 * 线性范围转换方法，可将一个范围映射到另外一个范围，如 0..1 -> 0..100
 * Created by luowenbin on 2021/10/8.
 */
/**
 * 转换器
 */
typealias RangeFunction<T> = (T) -> T

/**
 * 根据2个点生成线性转换器
 * @param dst 目标范围
 */
@JvmName("linearFloatFunctionTo")
fun ClosedRange<Float>.linearFunctionTo(dst: ClosedRange<Float>): RangeFunction<Float> {
    val factor = dst.length / length
    val offset = dst.start - start * factor
    return { it * factor + offset }
}

/**
 * 根据2个点生成线性转换器
 * @param dst 目标范围
 */
@JvmName("linearDoubleFunctionTo")
fun ClosedRange<Double>.linearFunctionTo(dst: ClosedRange<Double>): RangeFunction<Double> {
    val factor = dst.length / length
    val offset = dst.start - start * factor
    return { it * factor + offset }
}

/**
 * 根据2个点生成线性转换器
 * @param src 源范围
 */
@JvmName("linearFloatFunctionFrom")
inline operator fun ClosedRange<Float>.div(src: ClosedRange<Float>): RangeFunction<Float> {
    return src.linearFunctionTo(this)
}

/**
 * 根据2个点生成线性转换器
 * @param src 源范围
 */
@JvmName("linearDoubleFunctionFrom")
inline operator fun ClosedRange<Double>.div(src: ClosedRange<Double>): RangeFunction<Double> {
    return src.linearFunctionTo(this)
}

/**
 * 将转换器应用于数值
 * @param input 输入值
 */
inline operator fun RangeFunction<Float>.times(input: Float): Float {
    return invoke(input)
}

/**
 * 将转换器应用于数值
 * @param input 输入值
 */
inline operator fun RangeFunction<Double>.times(input: Double): Double {
    return invoke(input)
}