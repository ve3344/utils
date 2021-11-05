package me.lwb.utils.android.utils

import android.graphics.MaskFilter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import java.util.*

/**
 * Created by luowenbin on 2021/9/30.
 */
@Suppress("UNUSED")
object SpannableUtils {
    private const val SPAN_MODE = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

    /**
     * 创建SpannableString
     * @param build 配置器
     */
    fun create(build: SpannableBuilder.() -> Unit): CharSequence {
        return SpannableStringBuilder().apply {
            SpannableBuilder(this).build()
        }
    }

    /**
     * 修饰器
     */
    class Modifier(
        private val start: Int,
        private val end: Int,
        private val builder: SpannableStringBuilder
    ) {
        companion object {
            /**
             * 默认的UPDATE_DRAW
             */
            val UPDATE_DRAW_DEFAULT: (ds: TextPaint) -> Unit = {}

            /**
             * 空的UPDATE_DRAW
             */
            val UPDATE_DRAW_NONE: (ds: TextPaint) -> Unit = {}
        }

        /**
         * 添加span
         */
        fun span(what: Any): Modifier {
            builder.setSpan(what, start, end, SPAN_MODE)
            return this
        }

        /**
         * 添加typeface span
         */
        fun typeface(typeface: String) = span(TypefaceSpan(typeface))

        /**
         * 添加 删除线
         */
        fun strikethrough() = span(StrikethroughSpan())

        /**
         * 添加 下划线
         */
        fun underline() = span(UnderlineSpan())

        /**
         * 添加 文本style
         */
        fun styleSpan(style: Int) = span(StyleSpan(style))

        /**
         * 添加 前景色
         */
        fun foregroundColor(@ColorInt colorInt: Int) = span(ForegroundColorSpan(colorInt))

        /**
         * 添加 背景色
         */
        fun backgroundColor(@ColorInt colorInt: Int) = span(BackgroundColorSpan(colorInt))

        /**
         * 添加 上标
         */
        fun subscript() = span(SubscriptSpan())

        /**
         * 添加 下标
         */
        fun superscript() = span(SuperscriptSpan())

        /**
         * 添加 MaskFilter
         */
        fun maskFilter(maskFilter: MaskFilter) = span(MaskFilterSpan(maskFilter))

        /**
         * 添加 圆点
         */
        fun bullet(@Px gapWidth: Int, @ColorInt colorInt: Int) =
            span(BulletSpan(gapWidth, colorInt))

        /**
         * 添加 圆点
         */
        fun bullet(@Px gapWidth: Int) =
            span(BulletSpan(gapWidth))

        /**
         * 文字大小（绝对值）
         */
        fun absoluteSize(@Px size: Int) =
            span(AbsoluteSizeSpan(size))

        /**
         * 文字大小（相对值）
         */
        fun relativeSize(size: Float) =
            span(RelativeSizeSpan(size))

        /**
         * 地区
         */
        fun locale(locale: Locale) =
            span(LocaleSpan(locale))

        /**
         * 横向缩放
         */
        fun scaleX(scale: Float) =
            span(ScaleXSpan(scale))

        /**
         * 点击
         */
        fun click(
            updateDraw: (ds: TextPaint) -> Unit = UPDATE_DRAW_DEFAULT,
            click: (widget: View) -> Unit
        ) = span(object : ClickableSpan() {
            override fun onClick(widget: View) {
                click(widget)
            }

            override fun updateDrawState(ds: TextPaint) {
                when (updateDraw) {
                    UPDATE_DRAW_DEFAULT -> super.updateDrawState(ds)
                    UPDATE_DRAW_NONE -> Unit
                    else -> updateDraw(ds)
                }
            }

        })

    }

    /**
     * 配置器
     */
    class SpannableBuilder(private val builder: SpannableStringBuilder) {
        fun text(text: CharSequence): Modifier {
            val start = builder.length
            val end = start + text.length
            builder.append(text)

            return Modifier(start, end, builder)
        }

    }
}