@file:Suppress("UNUSED")
package me.lwb.json

fun Any?.toJson(): String {
    return Json.toJson(this)
}

fun Any?.toPrettyJson(): String {
    return Json.toPretty(this)
}

inline fun <reified T> String.asObj(): T {
    return Json.getObj(this)
}
