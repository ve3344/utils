@file:Suppress("UNUSED")
package me.lwb.utils.ext

import java.util.regex.Pattern
/**
 * Created by luowenbin on 2021/10/8.
 */
/**
 * 正则匹配判断
 */
operator fun Regex.contains(string: CharSequence) = this.matches(string)

/**
 * 正则匹配判断
 */
operator fun Pattern.contains(string: CharSequence) = this.matcher(string).matches()

//https://regexr.com/

object CommonRegexes {
    val PHONE="0?(13|14|15|16|17|18|19)[0-9]{9}".toRegex()
    val IP="[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))".toRegex()
    val EMAIL="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$".toRegex()
    val ACCOUNT="^[a-zA-Z][a-zA-Z0-9_]{4,15}\$".toRegex()
    val PASSWORD="^[a-zA-Z]\\w{5,17}\$".toRegex()//以字母开头，长度在6~18之间，只能包含字母、数字和下划线
    val COMPLEX_PASSWORD="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}\$".toRegex()//必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间
}
