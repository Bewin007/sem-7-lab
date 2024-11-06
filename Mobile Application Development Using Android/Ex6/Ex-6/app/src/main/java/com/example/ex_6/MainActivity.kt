package com.example.ex_6

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var wifiBroadcastReceiver: WifiBroadcastReceiver
    private lateinit var wifiStatusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the TextView to display Wi-Fi status
        wifiStatusTextView = findViewById(R.id.wifiStatusTextView)

        // Initialize and register the broadcast receiver
        wifiBroadcastReceiver = WifiBroadcastReceiver { isWifiConnected ->
            updateWifiStatus(isWifiConnected)
        }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(wifiBroadcastReceiver, filter)
    }

    private fun updateWifiStatus(isWifiConnected: Boolean) {
        // Update the TextView based on Wi-Fi connectivity status
        wifiStatusTextView.text = if (isWifiConnected) {
            "Wi-Fi is connected"
        } else {
            "Wi-Fi is disconnected"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver
        unregisterReceiver(wifiBroadcastReceiver)
    }
}
