package me.lwb.utils.android.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by luowenbin on 2021/9/27.
 */
/**
 * 根据tag获取Fragment，获取不到就创建并添加到FragmentManager
 */
@Suppress("UNCHECKED_CAST")
fun <F : Fragment> FragmentManager.getOrPut(tag: String, creator: () -> F): F {
    findFragmentByTag(tag)?.let { return it as F }//found
    val createdFragment = creator()
    beginTransaction().add(createdFragment, tag).commitNow()
    return createdFragment
}