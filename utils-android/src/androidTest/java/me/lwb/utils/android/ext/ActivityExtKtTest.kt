package me.lwb.utils.android.ext

import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity


/**
 * Created by luowenbin on 2021/9/28.
 */
class ActivityExtKtTest {

    fun AppCompatActivity.registerReceiverTest() {
        val wm=wifiManager
        val bm=bluetoothManager
        val am=audioManager

        registerReceiver(
            WifiManager.WIFI_STATE_CHANGED_ACTION,
            WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
        ) {
            it ?: return@registerReceiver
            val rssi = it.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -1000)
            toast("$rssi")
        }
        doOnDestroyOnce {
            toast("activity destroy")
        }
        startActivity<AppCompatActivity>(){
            withArgs {
                "a" to "b"

            }

        }
    }
}