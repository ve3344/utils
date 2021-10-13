package me.lwb.utils.android.ext

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import me.lwb.utils.android.utils.androidUri
import java.io.File

/**
 * 设置Intent内容
 * @param key 键
 * @param value 值
 */
operator fun Intent.set(key: String, value: Any?) {
    when (value) {
        null -> putExtra(key, null as java.io.Serializable?)
        is Boolean -> putExtra(key, value)
        is Byte -> putExtra(key, value)
        is Char -> putExtra(key, value)
        is Short -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Long -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Double -> putExtra(key, value)
        is CharSequence -> putExtra(key, value)
        is String -> putExtra(key, value)
        is Intent -> putExtra(key, value)
        is Bundle -> putExtra(key, value)
        is Parcelable -> putExtra(key, value)
        is java.io.Serializable -> putExtra(key, value)

        is BooleanArray -> putExtra(key, value)
        is ByteArray -> putExtra(key, value)
        is ShortArray -> putExtra(key, value)
        is IntArray -> putExtra(key, value)
        is LongArray -> putExtra(key, value)
        is FloatArray -> putExtra(key, value)
        is DoubleArray -> putExtra(key, value)
        is CharArray -> putExtra(key, value)
        is Array<*> -> {
            when {
                value.isArrayOf<CharSequence>() -> putExtra(key, value)
                value.isArrayOf<String>() -> putExtra(key, value)
                value.isArrayOf<Parcelable>() -> putExtra(key, value)
                else -> throw IllegalArgumentException("Unsupported value type ${value.javaClass.name} of key $key")
            }
        }

        else -> throw IllegalArgumentException("Unsupported value type ${value.javaClass.name} of key $key")

    }
}

/**
 * 设置Bundle内容
 * @param key 键
 * @param value 值
 */
@Suppress("UNCHECKED_CAST")
operator fun Bundle.set(key: String, value: Any?) {
    when (value) {
        null -> putSerializable(key, null)
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is Short -> putShort(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is CharSequence -> putCharSequence(key, value)
        is String -> putString(key, value)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is java.io.Serializable -> putSerializable(key, value)

        is BooleanArray -> putBooleanArray(key, value)
        is ByteArray -> putByteArray(key, value)
        is ShortArray -> putShortArray(key, value)
        is IntArray -> putIntArray(key, value)
        is LongArray -> putLongArray(key, value)
        is FloatArray -> putFloatArray(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is CharArray -> putCharArray(key, value)
        is Array<*> -> {
            when {
                value.isArrayOf<CharSequence>() -> putCharSequenceArray(
                    key,
                    value as? Array<out CharSequence>
                )
                value.isArrayOf<String>() -> putStringArray(key, value as? Array<out String>)
                value.isArrayOf<Parcelable>() -> putParcelableArray(
                    key,
                    value as? Array<out Parcelable>
                )
                else -> throw IllegalArgumentException("Unsupported value type ${value.javaClass.name} of key $key")
            }
        }
        else -> throw IllegalArgumentException("Unsupported value type ${value.javaClass.name} of key $key")
    }
}


/**
 * 获取参数
 * @param key 键
 * @param defaultValue 默认值
 * @return lazy 代理
 */
inline fun <reified T> Activity.args(
    key: String,
    crossinline defaultValue: () -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    intent?.extras?.get(key) as? T ?: defaultValue()
}

/**
 * 获取参数
 * @param key 键
 * @param defaultValue 默认值
 * @return lazy 代理
 */
inline fun <reified T> Activity.args(
    key: String,
    defaultValue: T
) = lazy(LazyThreadSafetyMode.NONE) {
    intent?.extras?.get(key) as? T ?: defaultValue
}

/**
 * 获取参数
 * @param key 键
 * @param defaultValue 默认值
 * @return lazy 代理
 */
inline fun <reified T> Fragment.args(
    key: String,
    crossinline defaultValue: () -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    arguments?.get(key) as? T ?: defaultValue()
}

/**
 * 获取参数
 * @param key 键
 * @param defaultValue 默认值
 * @return lazy 代理
 */
inline fun <reified T> Fragment.args(
    key: String, defaultValue: T
) = lazy(LazyThreadSafetyMode.NONE) {
    arguments?.get(key) as? T ?: defaultValue
}

/**
 * 设置参数
 * @param args 参数列表
 */
fun <T : Fragment> T.withArgs(
    vararg args: Pair<String, Any?>
): T {
    arguments = Bundle().also {
        for ((key, value) in args) {
            it[key] = value
        }
    }
    return this
}

/**
 * 设置参数
 * @param build 参数列表构建器
 */
fun <T : Fragment> T.withArgs(build: ArgsBuilder.() -> Unit): T {
    arguments = Bundle().withArgs(build)
    return this
}

/**
 * 设置参数
 * @param args 参数列表
 */
fun Intent.withArgs(vararg args: Pair<String, Any?>): Intent {
    for ((key, value) in args) {
        this[key] = value
    }
    return this
}

/**
 * 设置参数
 * @param build 参数列表构建器
 */
fun Intent.withArgs(build: ArgsBuilder.() -> Unit): Intent {
    ArgsBuilder { key, value ->
        this[key] = value
    }.build()
    return this
}

/**
 * 设置参数
 * @param args 参数列表
 */
fun Bundle.withArgs(vararg args: Pair<String, Any?>): Bundle {
    for ((key, value) in args) {
        this[key] = value
    }
    return this
}

/**
 * 设置参数
 * @param build 参数列表构建器
 */
fun Bundle.withArgs(build: ArgsBuilder.() -> Unit): Bundle {
    ArgsBuilder { key, value ->
        this[key] = value
    }.build()
    return this
}

/**
 * 参数列表构建器
 */
class ArgsBuilder(private val addArg: (key: String, value: Any?) -> Unit) {
    infix fun String.to(value: Any?) {
        addArg(this, value)
    }
}

/**
 * 设置Intent数据
 * @param file 文件
 * @param type mine类型
 * @param writeable 是否授予写权限
 */
fun Intent.setDataAndType(
    file: File,
    type: String,
    writeable: Boolean = true
) {
    this.setDataAndType(file.androidUri(), type)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (writeable) {
            this.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }
}