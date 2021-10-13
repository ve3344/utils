package me.lwb.utils.ext

/**
 * Created by luowenbin on 2021/10/8.
 */
import java.security.MessageDigest

/**
 * 字节数组转md5字符串
 * @return 结果字符串，当算法转换失败时为 null
 */
fun ByteArray.md5() = digest("MD5")

/**
 * 字节数组转sha1字符串
 * @return 结果字符串，当算法转换失败时为 null
 */
fun ByteArray.sha1() = digest("SHA-1")

/**
 * 字节数组转sha256字符串
 * @return 结果字符串，当算法转换失败时为 null
 */
fun ByteArray.sha256() = digest("SHA-256")

/**
 * 字节数组使用摘要算法转换
 * @return 结果字符串，当算法转换失败时为 null
 */
fun ByteArray.digest(algorithm: String): String? {
    return kotlin.runCatching { MessageDigest.getInstance(algorithm) }
        .getOrNull()?.digest(this)?.hex()
}

/**
 * 字节数组转hex字符串
 * @return hex字符串，当算法转换失败时为 null
 */
fun ByteArray?.hex(): String? {
    this ?: return null
    return fold(StringBuilder()) { acc, byte ->
        val hexStr = Integer.toHexString(byte.toInt() and (0xFF))
        if (hexStr.length == 1) {
            acc.append('0')
        }
        acc.append(hexStr)
    }.toString()
}