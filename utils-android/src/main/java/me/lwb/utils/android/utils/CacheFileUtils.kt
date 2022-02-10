package me.lwb.utils.android.utils

import android.os.StatFs
import me.lwb.utils.android.UtilsContext
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by luowenbin on 2021/9/13.
 */
object CacheFileUtils {
    /**
     * 确保为目录
     */
    private fun File.ensureDir(): File {
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
     * 子缓存目录
     * @param type 类型
     */
    private fun childDir(type: String) = File(cacheDir, type).ensureDir()

    /**
     * 生成文件名
     * @param suffix 后缀（如.jpg）
     */
    private fun generateFilename(suffix: String): String {
        return uniqueIdentifier +suffix
    }

    /**
     * 缓存目录
     */
    val cacheDir: File
        get() = (UtilsContext.context.externalCacheDir ?: UtilsContext.context.cacheDir).ensureDir()


    /**
     * 创建缓存文件
     * @param suffix 后缀（如.jpg）
     */
    fun createCacheFile(suffix: String) = File(cacheDir, generateFilename(suffix))

    /**
     * 创建缓存文件
     * @param type 类型
     * @param suffix 后缀（如.jpg）
     */
    fun createCacheFile(type: String, suffix: String) =
        File(childDir(type), generateFilename(suffix))

    /**
     * 获取缓存文件
     * @param type 类型
     * @param name 文件名
     */
    fun cacheFile(type: String, name: String) =
        File(childDir(type), name)

    /**
     * 缓存目录
     * @param type 类型
     */
    fun cacheDir(type: String) =
        childDir(type)

    /**
     * 清除缓存文件
     */
    fun cleanCacheFile() = cacheDir.deleteRecursively()

    /**
     * 判断文件是否App私有存储
     * @param file 文件
     */
    fun isInternalFile(file: File): Boolean {
        val appInternalDir = UtilsContext.context.cacheDir.parentFile ?: return false
        return file.absolutePath.startsWith(appInternalDir.absolutePath)
    }

    /**
     * 缓存文件大小
     */
    val cacheSizeBytes
        get() = StatFs(cacheDir.absolutePath).totalBytes

    private val increasingFlag = AtomicInteger()
    private val uniqueIdentifier get() = "${System.nanoTime()}${increasingFlag.getAndIncrement()}"


}