package com.example.tms_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.net.wifi.WifiManager
import android.widget.Toast

class WifiBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        fun notify(msg: String) {
            Toast.makeText(context, msg as CharSequence, Toast.LENGTH_SHORT).show()
            Log.d("MYLOG", msg)
        }

        val wifiState = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
        when (wifiState) {
            WifiManager.WIFI_STATE_ENABLED -> notify("Wi-Fi is enabled")
            WifiManager.WIFI_STATE_DISABLED -> notify("Wi-Fi is disabled")
        }
    }
}