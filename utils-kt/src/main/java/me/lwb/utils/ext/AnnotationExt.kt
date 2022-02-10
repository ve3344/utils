@file:Suppress("UNUSED")
package me.lwb.utils.ext

import java.util.*

/**
 * Created by luowenbin on 2021/10/8.
 */

/**
 * 获取含有指定注解的方法
 */
inline fun <reified T : Annotation> Class<*>.declaredMethodsAnnotateBy() = declaredMethods
    .mapNotNull {
        val annotation = it.getAnnotation(T::class.java) ?: return@mapNotNull null
        return@mapNotNull Pair(it, annotation)
    }
/**
 * 获取含有指定注解的属性
 */
inline fun <reified T : Annotation> Class<*>.declaredFieldsAnnotateBy() = declaredFields
    .mapNotNull {
        val annotation = it.getAnnotation(T::class.java) ?: return@mapNotNull null
        return@mapNotNull Pair(it, annotation)
    }
