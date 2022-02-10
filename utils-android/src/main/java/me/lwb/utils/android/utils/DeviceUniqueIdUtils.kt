package me.lwb.utils.android.utils

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import me.lwb.utils.android.UtilsContext
import me.lwb.utils.android.ext.firstNotNullOfOrNull
import me.lwb.utils.android.ext.hasPermissions
import me.lwb.utils.android.ext.telephonyManager
import me.lwb.utils.android.ext.wifiManager
import me.lwb.utils.ext.hex
import java.io.File
import java.net.NetworkInterface

/**
 * Created by luowenbin on 8/1/2022.
 * 优先级：imei>serial number>android_id>伪imei
 */
object DeviceUniqueIdUtils {
    private val EMPTY_UNIQUE_ID = null


    private fun String?.trimUniqueId(): String? {
        if (this == null || this == Build.UNKNOWN) {
            return EMPTY_UNIQUE_ID
        }
        if (all { it == '*' }) {
            return EMPTY_UNIQUE_ID
        }
        if (all { it == '0' }) {
            return EMPTY_UNIQUE_ID
        }
        return this
    }

    //----------------------

    //隐私

    //安卓9.0及以上版本默认使用了“随机MAC地址”
    val WIFI_MAC_FROM_NETWORK_INTERFACE: String?
        get() = kotlin.runCatching {
            NetworkInterface.getNetworkInterfaces()
                .asSequence()
                .filter { it.name == "wlan0" }
                .firstOrNull()?.hardwareAddress.hex()
        }.getOrDefault(EMPTY_UNIQUE_ID)

    //Android 6.0以后通过 WifiManager 获取到的mac将是固定的：02:00:00:00:00:00
    //Build.VERSION.SDK_INT < 23
    val WIFI_MAC_FROM_WIFI_MANAGER: String?
        @SuppressLint("HardwareIds")
        get() = kotlin.runCatching {
            UtilsContext.context.wifiManager().connectionInfo.macAddress
        }.getOrDefault(EMPTY_UNIQUE_ID)

    //Android 7.0之后读取 /sys/class/net/wlan0/address 获取不到
    val WIFI_MAC_FROM_FILE
        get() = kotlin.runCatching {
            sequenceOf("/sys/class/net/wlan0/address", "/sys/devices/virtual/net/wlan0/address")
                .map { File(it) }
                .filter { it.canRead() }
                .map { it.readText() }
                .firstNotNullOfOrNull {
                    it.takeIf { it.isNotBlank() }
                }
        }.getOrDefault(EMPTY_UNIQUE_ID)


    @Suppress("DEPRECATION")
    val SERIAL_NUMBER: String?
        @SuppressLint("HardwareIds")
        get() = Build.SERIAL.trimUniqueId()


    //9774d56d682e549c
    //0000000000000
    @Suppress("DEPRECATION")
    val ANDROID_ID: String?
        @SuppressLint("HardwareIds")
        get() = Settings.Secure.getString(
            UtilsContext.context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

    val BUILD_INFO: String?
        @SuppressLint("HardwareIds")
        @Suppress("Deprecation")
        get() = kotlin.runCatching {
            val sb = StringBuilder()
            sb.append(Build.BOARD) //获取设备基板名称
            sb.append(Build.BOOTLOADER) //获取设备引导程序版本号
            sb.append(Build.BRAND) //获取设备品牌
            sb.append(Build.CPU_ABI) //获取设备指令集名称（CPU的类型）
            sb.append(Build.CPU_ABI2) //获取第二个指令集名称
            sb.append(Build.DEVICE) //获取设备驱动名称
            sb.append(Build.DISPLAY) //获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
            sb.append(Build.FINGERPRINT) //设备的唯一标识。由设备的多个信息拼接合成。
            sb.append(Build.HARDWARE) //设备硬件名称,一般和基板名称一样（BOARD）
            sb.append(Build.ID) //设备版本号。
            sb.append(Build.MODEL) //获取手机的型号 设备名称。
            sb.append(Build.MANUFACTURER) //获取设备制造商
            sb.append(Build.PRODUCT) //整个产品的名称
            sb.append(Build.TAGS) //设备标签。如release-keys 或测试的 test-keys
            sb.append(Build.TYPE) //设备版本类型 主要为 "user" 或 "eng".
            sb.append(Build.USER) //设备用户名 基本上都为android -build
            sb.append(Build.VERSION.RELEASE) //获取系统版本字符串。如4.1.2 或2.2 或2.3等
            sb.append(Build.VERSION.CODENAME) //设备当前的系统开发代号，一般使用REL代替
            sb.append(Build.VERSION.INCREMENTAL) //系统源代码控制值，一个数字或者git hash值
            sb.append(Build.VERSION.SDK_INT) //系统的API级别 数字表示
            sb.append(Build.SERIAL)
            sb.toString().trim().toByteArray().hex()
        }.getOrDefault(EMPTY_UNIQUE_ID)

    /**
     * imei
     * sh359881030314356
     * 没有通话功能的设备是没有
     */
    val IMEI: String?
        @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
        @SuppressLint("MissingPermission","HardwareIds")
        @Suppress("Deprecation")
        get() {
            val context = UtilsContext.context
            if (!context.hasPermissions(Manifest.permission.READ_PHONE_STATE)) {
                return ""
            }
            return context.telephonyManager().deviceId
        }
}