package me.lwb.utils.android.utils

import android.os.LocaleList

import android.os.Build
import java.util.*


/**
 * Created by luowenbin on 2021/10/19.
 */
object LocaleUtils {
    /**
     * 获取系统语言
     */
    fun getSystemLocale() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        LocaleList.getDefault()[0]
    } else {
        Locale.getDefault()
    }
}