package me.lwb.utils.ext

/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * 遍历pair
 * @param action 遍历动作
 */
fun <A, B> Iterable<Pair<A, B>>.forEach(action: (first: A, second: B) -> Unit) {
    forEach { action(it.first, it.second) }
}

/**
 * 遍历pair
 * @param action 遍历动作
 */
fun <A, B> Array<Pair<A, B>>.forEach(action: (first: A, second: B) -> Unit) {
    this.forEach { it: Pair<A, B> -> action(it.first, it.second) }
}
