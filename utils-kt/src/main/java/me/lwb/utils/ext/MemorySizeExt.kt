package me.lwb.utils.ext

/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * kb转字节
 */
inline val Int.kb2bytes: Long
    get() = (this shl 10).toLong()

/**
 * mb转字节
 */
inline val Int.mb2bytes: Long
    get() = (this shl 20).toLong()

/**
 * gb转字节
 */
inline val Int.gb2bytes: Long
    get() = (this shl 30).toLong()

/**
 * 字节转kb
 */
inline val Long.bytes2kb: Int
    get() = (this shr 10).toInt()

/**
 * 字节转mb
 */
inline val Long.bytes2mb: Int
    get() = (this shr 20).toInt()

/**
 * 字节转gb
 */
inline val Long.bytes2gb: Int
    get() = (this shr 30).toInt()

