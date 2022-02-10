package me.lwb.utils.android.ext

/**
 * Created by luowenbin on 4/1/2022.
 */
inline fun <T, R : Any> Array<out T>.firstNotNullOfOrNull(transform: (T) -> R?): R? {
    for (element in this) {
        val result = transform(element)
        if (result != null) {
            return result
        }
    }
    return null
}


inline fun <T, R : Any> Iterable<T>.firstNotNullOfOrNull(transform: (T) -> R?): R? {
    for (element in this) {
        val result = transform(element)
        if (result != null) {
            return result
        }
    }
    return null
}
inline fun <T, R : Any> Sequence<T>.firstNotNullOfOrNull(transform: (T) -> R?): R? {
    for (element in this) {
        val result = transform(element)
        if (result != null) {
            return result
        }
    }
    return null
}