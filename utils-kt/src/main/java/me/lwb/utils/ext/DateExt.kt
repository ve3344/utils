package me.lwb.utils.ext

import java.util.*

/**
 * Created by luowenbin on 2021/12/14.
 */
fun Date.toCalendar() = Calendar.getInstance().also { it.time = this }