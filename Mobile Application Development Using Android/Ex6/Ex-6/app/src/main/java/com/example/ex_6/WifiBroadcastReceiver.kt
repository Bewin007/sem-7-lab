package com.example.ex_6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

class WifiBroadcastReceiver(private val onWifiStateChanged: (Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            // Check if Wi-Fi is connected or disconnected
            val isWifiConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

            // Display a toast message and update the UI via the callback function
            if (isWifiConnected) {
                Toast.makeText(context, "Wi-Fi is connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Wi-Fi is disconnected", Toast.LENGTH_SHORT).show()
            }

            // Notify MainActivity about the Wi-Fi state change
            onWifiStateChanged(isWifiConnected)
        }
    }
}
