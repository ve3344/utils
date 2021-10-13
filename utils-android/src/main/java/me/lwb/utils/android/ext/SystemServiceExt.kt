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

val Context.wifiP2pManager get() = getSystemService<WifiP2pManager>()
val Context.wallpaperManager get() = getSystemService<WallpaperManager>()
val Context.usbManager get() = getSystemService<UsbManager>()
val Context.textServicesManager get() = getSystemService<TextServicesManager>()
val Context.devicePolicyManager get() = getSystemService<DevicePolicyManager>()
val Context.nfcManager get() = getSystemService<NfcManager>()
val Context.dropBoxManager get() = getSystemService<DropBoxManager>()
val Context.accountManager get() = getSystemService<AccountManager>()
val Context.nsdManager get() = getSystemService<NsdManager>()
val Context.userManager get() = getSystemService<UserManager>()
val Context.bluetoothManager get() = getSystemService<BluetoothManager>()
val Context.printManager get() = getSystemService<PrintManager>()
val Context.consumerIrManager get() = getSystemService<ConsumerIrManager>()
val Context.captioningManager get() = getSystemService<CaptioningManager>()
val Context.appOpsManager get() = getSystemService<AppOpsManager>()
val Context.tvInputManager get() = getSystemService<TvInputManager>()
val Context.mediaProjectionManager get() = getSystemService<MediaProjectionManager>()
val Context.telecomManager get() = getSystemService<TelecomManager>()
val Context.restrictionsManager get() = getSystemService<RestrictionsManager>()
val Context.mediaSessionManager get() = getSystemService<MediaSessionManager>()
val Context.launcherApps get() = getSystemService<LauncherApps>()
val Context.appWidgetManager get() = getSystemService<AppWidgetManager>()
val Context.usageStatsManager get() = getSystemService<UsageStatsManager>()
val Context.windowManager get() = getSystemService<WindowManager>()
val Context.clipboardManager get() = getSystemService<ClipboardManager>()
val Context.layoutInflater get() = getSystemService<LayoutInflater>()
val Context.activityManager get() = getSystemService<ActivityManager>()
val Context.powerManager get() = getSystemService<PowerManager>()
val Context.alarmManager get() = getSystemService<AlarmManager>()
val Context.notificationManager get() = getSystemService<NotificationManager>()
val Context.keyguardManager get() = getSystemService<KeyguardManager>()
val Context.locationManager get() = getSystemService<LocationManager>()
val Context.searchManager get() = getSystemService<SearchManager>()
val Context.storageManager get() = getSystemService<StorageManager>()
val Context.vibrator get() = getSystemService<Vibrator>()
val Context.connectivityManager get() = getSystemService<ConnectivityManager>()
val Context.wifiManager get() = getSystemService<WifiManager>()
val Context.audioManager get() = getSystemService<AudioManager>()
val Context.mediaRouter get() = getSystemService<MediaRouter>()
val Context.telephonyManager get() = getSystemService<TelephonyManager>()
val Context.sensorManager get() = getSystemService<SensorManager>()
val Context.subscriptionManager get() = getSystemService<SubscriptionManager>()
val Context.carrierConfigManager get() = getSystemService<CarrierConfigManager>()
val Context.cameraManager get() = getSystemService<CameraManager>()
val Context.inputMethodManager get() = getSystemService<InputMethodManager>()
val Context.inputManager get() = getSystemService<InputManager>()
val Context.uiModeManager get() = getSystemService<UiModeManager>()
val Context.downloadManager get() = getSystemService<DownloadManager>()
val Context.batteryManager get() = getSystemService<BatteryManager>()
val Context.jobScheduler get() = getSystemService<JobScheduler>()
val Context.accessibilityManager get() = getSystemService<AccessibilityManager>()
val Context.displayManager get() = getSystemService<DisplayManager>()

