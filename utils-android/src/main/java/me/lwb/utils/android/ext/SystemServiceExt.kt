@file:Suppress("UNUSED")

package me.lwb.utils.android.ext

/**
 * Created by luowenbin on 2021/10/13.
 * 系统服务
 */
import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.storage.StorageManager
import android.print.PrintManager
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import androidx.core.content.ContextCompat

private inline fun <reified T> Context.getSystemService(): T =
    ContextCompat.getSystemService(this, T::class.java)
        ?: throw IllegalArgumentException("System service[${T::class.java.simpleName}] not found")

fun Context.wifiP2pManager() = getSystemService<WifiP2pManager>()
fun Context.wallpaperManager() = getSystemService<WallpaperManager>()
fun Context.usbManager() = getSystemService<UsbManager>()
fun Context.textServicesManager() = getSystemService<TextServicesManager>()
fun Context.devicePolicyManager() = getSystemService<DevicePolicyManager>()
fun Context.nfcManager() = getSystemService<NfcManager>()
fun Context.dropBoxManager() = getSystemService<DropBoxManager>()
fun Context.accountManager() = getSystemService<AccountManager>()
fun Context.nsdManager() = getSystemService<NsdManager>()
fun Context.userManager() = getSystemService<UserManager>()
fun Context.bluetoothManager() = getSystemService<BluetoothManager>()
fun Context.printManager() = getSystemService<PrintManager>()
fun Context.consumerIrManager() = getSystemService<ConsumerIrManager>()
fun Context.captioningManager() = getSystemService<CaptioningManager>()
fun Context.appOpsManager() = getSystemService<AppOpsManager>()
fun Context.tvInputManager() = getSystemService<TvInputManager>()
fun Context.mediaProjectionManager() = getSystemService<MediaProjectionManager>()
fun Context.telecomManager() = getSystemService<TelecomManager>()
fun Context.restrictionsManager() = getSystemService<RestrictionsManager>()
fun Context.mediaSessionManager() = getSystemService<MediaSessionManager>()
fun Context.launcherApps() = getSystemService<LauncherApps>()
fun Context.appWidgetManager() = getSystemService<AppWidgetManager>()
fun Context.usageStatsManager() = getSystemService<UsageStatsManager>()
fun Context.windowManager() = getSystemService<WindowManager>()
fun Context.clipboardManager() = getSystemService<ClipboardManager>()
fun Context.layoutInflater() = getSystemService<LayoutInflater>()
fun Context.activityManager() = getSystemService<ActivityManager>()
fun Context.powerManager() = getSystemService<PowerManager>()
fun Context.alarmManager() = getSystemService<AlarmManager>()
fun Context.notificationManager() = getSystemService<NotificationManager>()
fun Context.keyguardManager() = getSystemService<KeyguardManager>()
fun Context.locationManager() = getSystemService<LocationManager>()
fun Context.searchManager() = getSystemService<SearchManager>()
fun Context.storageManager() = getSystemService<StorageManager>()
fun Context.vibrator() = getSystemService<Vibrator>()
fun Context.connectivityManager() = getSystemService<ConnectivityManager>()
fun Context.wifiManager() = getSystemService<WifiManager>()
fun Context.audioManager() = getSystemService<AudioManager>()
fun Context.mediaRouter() = getSystemService<MediaRouter>()
fun Context.telephonyManager() = getSystemService<TelephonyManager>()
fun Context.sensorManager() = getSystemService<SensorManager>()
fun Context.subscriptionManager() = getSystemService<SubscriptionManager>()
fun Context.carrierConfigManager() = getSystemService<CarrierConfigManager>()
fun Context.cameraManager() = getSystemService<CameraManager>()
fun Context.inputMethodManager() = getSystemService<InputMethodManager>()
fun Context.inputManager() = getSystemService<InputManager>()
fun Context.uiModeManager() = getSystemService<UiModeManager>()
fun Context.downloadManager() = getSystemService<DownloadManager>()
fun Context.batteryManager() = getSystemService<BatteryManager>()
fun Context.jobScheduler() = getSystemService<JobScheduler>()
fun Context.accessibilityManager() = getSystemService<AccessibilityManager>()
fun Context.displayManager() = getSystemService<DisplayManager>()

