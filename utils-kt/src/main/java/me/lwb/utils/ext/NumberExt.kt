package me.lwb.utils.ext

/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Double?>.toDoubleArray(default: Double = 0.0): DoubleArray {
    return map { it ?: default }.toDoubleArray()
}

/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Int?>.toIntArray(default: Int = 0): IntArray {
    return map { it ?: default }.toIntArray()
}

/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Float?>.toFloatArray(default: Float = 0f): FloatArray {
    return map { it ?: default }.toFloatArray()
}

/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Boolean?>.toBooleanArray(default: Boolean = false): BooleanArray {
    return map { it ?: default }.toBooleanArray()
}

/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Long?>.toLongArray(default: Long = 0L): LongArray {
    return map { it ?: default }.toLongArray()
}

/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Short?>.toShortArray(default: Short = 0): ShortArray {
    return map { it ?: default }.toShortArray()
}

/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Char?>.toCharArray(default: Char = 0.toChar()): CharArray {
    return map { it ?: default }.toCharArray()
}

/**
 * 可空数组转换，将null用默认值代替
 * @param default 默认值
 */
fun Array<Byte?>.toByteArray(default: Byte = 0.toByte()): ByteArray {
    return map { it ?: default }.toByteArray()
}