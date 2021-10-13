package me.lwb.utils.ext

import java.util.concurrent.TimeUnit

/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * unix时间戳
 */
typealias Timestamp = Long

/**
 * 秒转毫秒
 */
inline val Int.seconds2millis: Long
    get() = TimeUnit.SECONDS.toMillis(toLong())

/**
 * 分钟转毫秒
 */
inline val Int.minutes2millis: Long
    get() = TimeUnit.MINUTES.toMillis(toLong())

/**
 * 小时转毫秒
 */
inline val Int.hours2millis: Long
    get() = TimeUnit.HOURS.toMillis(toLong())

/**
 * 天转毫秒
 */
inline val Int.days2millis: Long
    get() = TimeUnit.DAYS.toMillis(toLong())

/**
 * 获取指定时间戳离相对于现在的时间差
 */
inline val Timestamp.passedTime: Long
    get() = System.currentTimeMillis() - this
