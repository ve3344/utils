@file:Suppress("UNUSED", "DEPRECATION")
package me.lwb.utils.android.ext

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


private val dangerousPermissions = arrayOf(
    Manifest.permission.WRITE_CONTACTS,
    Manifest.permission.GET_ACCOUNTS,
    Manifest.permission.READ_CONTACTS,
    Manifest.permission.READ_CALL_LOG,
    Manifest.permission.READ_PHONE_STATE,
    Manifest.permission.CALL_PHONE,
    Manifest.permission.WRITE_CALL_LOG,
    Manifest.permission.USE_SIP,
    Manifest.permission.PROCESS_OUTGOING_CALLS,
    Manifest.permission.ADD_VOICEMAIL,
    Manifest.permission.READ_CALENDAR,
    Manifest.permission.WRITE_CALENDAR,
    Manifest.permission.CAMERA,
    Manifest.permission.BODY_SENSORS,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.READ_SMS,
    Manifest.permission.RECEIVE_WAP_PUSH,
    Manifest.permission.RECEIVE_MMS,
    Manifest.permission.RECEIVE_SMS,
    Manifest.permission.SEND_SMS
)

object PermissionUtils {
    /**
     * 是否为危险权限
     * @param permission 权限
     */
    fun isDangerousPermission(permission: String) = permission in dangerousPermissions
}
/**
 * 获取拒绝的权限
 * @param permissions 权限集
 * @return 拒绝权限集
 */
fun Context.filterRationalePermissions(vararg permissions: String): List<String> {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return emptyList()
    }
    return permissions.filter {
        ContextCompat.checkSelfPermission(this, it) != PERMISSION_GRANTED
    }
}
/**
 * 获取不在询问的权限
 * @param permissions 权限集
 * @return 不在询问权限集
 */
fun Activity.filterDeniedPermissions(vararg permissions: String): List<String> {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return emptyList()
    }
    return permissions.filter {
        !ActivityCompat.shouldShowRequestPermissionRationale(this, it)
    }
}

fun Context.hasPermissions(vararg permissions: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return true
    }
    return permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PERMISSION_GRANTED
    }
}

