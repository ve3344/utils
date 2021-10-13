package me.lwb.utils.android.ext

import android.media.MediaPlayer
import me.lwb.utils.android.utils.ContextUtils

/**
 * 设置Asset 播放源
 * @param path asset 路径
 */
fun MediaPlayer.setDataSourceAsset(path: String) {
    val fd = ContextUtils.assets.openFd(path)
    if (fd.declaredLength < 0) {
        setDataSource(fd.fileDescriptor)
    }
    setDataSource(fd.fileDescriptor, fd.startOffset, fd.declaredLength)
}