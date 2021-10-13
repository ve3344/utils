package me.lwb.utils.android.ext

import androidx.annotation.MainThread
import androidx.core.util.Predicate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun interface Transformer<X, Y> {
    fun transform(up: LiveData<X?>, down: MediatorLiveData<Y?>)
}

/**
 * 数据转换
 */
@MainThread
inline fun <X, Y> LiveData<X?>.map(crossinline transform: (X?) -> Y): LiveData<Y?> =
    Transformations.map(this) { transform(it) }
/**
 * 数据集转换
 */
@MainThread
inline fun <X, Y> LiveData<X?>.switchMap(
    crossinline transform: (X?) -> LiveData<Y>
): LiveData<Y> = Transformations.switchMap(this) { transform(it) }
//--------------------------
/**
 * 数据中转
 */
@MainThread
fun <X, Y> LiveData<X?>.transform(transformFunction: Transformer<X?, Y?>): LiveData<Y?> {
    val result = MediatorLiveData<Y?>()
    result.addSource(this) {
        transformFunction.transform(this, result)
    }
    return result
}


/**
 * 数据过滤
 */
@MainThread
fun <X> LiveData<X?>.filter(predicate: Predicate<X?>) =
    transform<X?, X?> { up, down ->
        val value = up.value
        if (predicate.test(value)) {
            down.value = value
        }
    }

/**
 * 数据监听
 */
@MainThread
inline fun <T> LiveData<T?>.doBefore(crossinline block: (t: T?) -> Unit) =
    transform<T?, T?> { up, down ->
        val value = up.value
        block(value)
        down.value = value
    }
/**
 * 数据监听
 */
@MainThread
inline fun <T> LiveData<T?>.doAfter(crossinline block: (t: T?) -> Unit) =
    transform<T?, T?> { up, down ->
        val value = up.value
        down.value = value
        block(value)
    }

/**
 * 数据过滤null
 */
@MainThread
fun <X> LiveData<X?>.filterNonNull() = filter { it != null }
/**
 * 数据过滤不变的
 */
@MainThread
fun <X> LiveData<X?>.distinctUntilChanged() = transform(object : Transformer<X?, X?> {
    var isFirstTime = true
    override fun transform(up: LiveData<X?>, down: MediatorLiveData<X?>) {
        val value = up.value
        if (isFirstTime || down.value != value) {
            down.value = value
            isFirstTime = false
        }
    }
})
/**
 * 数据过滤曾经有过的
 */
@MainThread
fun <X> LiveData<X?>.distinct() = filter(object : Predicate<X?> {
    val triggeredSet = mutableSetOf<X?>()
    override fun test(t: X?): Boolean {
        if (value != null && !triggeredSet.contains(value)){
            triggeredSet.add(value)
            return true
        }
        return false
    }

})

