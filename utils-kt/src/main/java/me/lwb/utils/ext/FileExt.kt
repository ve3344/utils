@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package me.lwb.utils.ext

import java.io.File

/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * path 转 file
 */
inline fun String.toFile() = File(this)

/**
 * 创建父目录(如果不存在)
 * @return 是否成功
 */
fun File.createParentIfNotExists(): Boolean {
    val parent = parentFile
    if (!parent.exists()) {
        return parent.mkdirs()
    }
    return true
}

/**
 * 创建文件(如果不存在)
 * @return 是否成功
 */
fun File.createFileIfNotExists(): Boolean {
    if (!exists()) {
        return createNewFile()
    }
    return true

}

/**
 * 创建文件夹(如果不存在)
 * @return 是否成功
 */
fun File.createDirIfNotExists(): Boolean {
    if (!exists()) {
        return mkdir()
    }
    return true

}

/**
 * 确保为文件夹，如果不是，便创建文件夹
 * @throws RuntimeException 没有操作权限（删除失败，创建失败）
 */
fun File.ensureDir(): File {
    if (isDirectory) {
        return this
    }
    if (exists() && !delete()) {
        throw RuntimeException("Can not delete file $path")
    }
    if (!mkdir()) {
        throw RuntimeException("Can not create dir $path")
    }
    return this
}

/**
 * 删除文件 忽略异常
 * @return 是否成功
 */
fun File.deleteQuietly() = kotlin.runCatching { delete() }.isSuccess

/**
 * 目录和文件名拼接
 * @return 拼接后的文件
 */
operator fun File.div(child: String) = File(this, child)

/**
 * 列出子文件
 * @return 子文件
 */
fun File.children(): Array<File> = listFiles() ?: emptyArray()