@file:Suppress("UNUSED")
package me.lwb.utils.android.ext

import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

/**
 * 监听文本变化
 * @param afterTextChanged 文本变化回调
 */
inline fun EditText.doAfterTextChange(crossinline afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

/**
 * 监听文本变化
 */
inline fun TextView.addTextChangedListener(init: TextWatcherAdapter.() -> Unit) =
    addTextChangedListener(TextWatcherAdapter().apply(init))

class TextWatcherAdapter : TextWatcher {

    private var beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var afterTextChanged: ((Editable?) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }

    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        beforeTextChanged = listener
    }

    fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        onTextChanged = listener
    }

    fun afterTextChanged(listener: (Editable?) -> Unit) {
        afterTextChanged = listener
    }

}

/**
 * 获取文本内容
 */
fun EditText.textString() = this.text.toString()

/**
 * 判断是否为空
 */
fun EditText.isEmpty() = this.text.isEmpty()

/**
 * 获取文本内容
 */
fun TextView.textString() = this.text.toString()

/**
 * 判断是否为空
 */
fun TextView.isEmpty() = this.text.isEmpty()

fun EditText.acceptMatch(regex: String) {
    this += RegexInputFilter(regex, true)
}

fun EditText.rejectMatch(regex: String) {
    this += RegexInputFilter(regex, false)
}

operator fun EditText.plusAssign(filter: InputFilter) {
    val filters = this.filters
    if (filters == null) {
        this.filters = arrayOf(filter)
        return
    }
    this.filters = filters.toMutableList()
        .also { it.add(filter) }
        .toTypedArray()
}


class RegexInputFilter(regex: String, private val acceptMatch: Boolean) : InputFilter {
    private val pattern = regex.toPattern()
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val match = pattern.matcher(source.toString()).matches()
        val accept = if (acceptMatch) {
            match
        } else {
            !match
        }

        return if (accept) {
            null //accept
        } else {
            ""//reject
        }
    }

}