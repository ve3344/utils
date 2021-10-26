package me.lwb.utils.android.ext

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by luowenbin on 2021/10/25.
 */

/**
 * SharedPreferences 属性代理
 */
class PreferencesProperty<T>(
    private val sp: SharedPreferences,
    private val key: String? = null,
    private val defaultValue: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor,
    private val useApply: Boolean = true
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        sp.getter(key ?: property.name, defaultValue) ?: defaultValue

    @SuppressLint("ApplySharedPref")
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        sp.edit().apply {
            setter(key ?: property.name, value)
            if (useApply) {
                apply()
            } else {
                commit()
            }
        }
    }
}
/**
 * 代理int类型
 */
fun SharedPreferences.int(
    defaultValue: Int = 0,
    key: String? = null,
    useApply: Boolean = true
) = PreferencesProperty(
    this,
    key,
    defaultValue,
    SharedPreferences::getInt,
    SharedPreferences.Editor::putInt,
    useApply
)
/**
 * 代理long类型
 */
fun SharedPreferences.long(
    defaultValue: Long = 0,
    key: String? = null,
    useApply: Boolean = true
) = PreferencesProperty(
    this,
    key,
    defaultValue,
    SharedPreferences::getLong,
    SharedPreferences.Editor::putLong,
    useApply
)
/**
 * 代理float类型
 */
fun SharedPreferences.float(
    defaultValue: Float = 0f,
    key: String? = null,
    useApply: Boolean = true
) = PreferencesProperty(
    this,
    key,
    defaultValue,
    SharedPreferences::getFloat,
    SharedPreferences.Editor::putFloat,
    useApply
)
/**
 * 代理boolean类型
 */
fun SharedPreferences.boolean(
    defaultValue: Boolean = false,
    key: String? = null,
    useApply: Boolean = true
) = PreferencesProperty(
    this,
    key,
    defaultValue,
    SharedPreferences::getBoolean,
    SharedPreferences.Editor::putBoolean,
    useApply
)
/**
 * 代理stringSet类型
 */
fun SharedPreferences.stringSet(
    defaultValue: Set<String> = emptySet(),
    key: String? = null,
    useApply: Boolean = true
) = PreferencesProperty(
    this,
    key,
    defaultValue,
    SharedPreferences::getStringSet,
    SharedPreferences.Editor::putStringSet,
    useApply
)
/**
 * 代理string类型
 */
fun SharedPreferences.string(
    defaultValue: String = "",
    key: String? = null,
    useApply: Boolean = true
) = PreferencesProperty(
    this,
    key,
    defaultValue,
    SharedPreferences::getString,
    SharedPreferences.Editor::putString,
    useApply
)
