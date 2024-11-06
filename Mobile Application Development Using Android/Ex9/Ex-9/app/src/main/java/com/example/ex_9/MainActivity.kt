package com.example.ex_9

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var btnDiscover: Button
    private lateinit var listViewDevices: ListView
    private lateinit var deviceListAdapter: ArrayAdapter<String>
    private val discoveredDevices = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDiscover = findViewById(R.id.btnDiscover)
        listViewDevices = findViewById(R.id.listViewDevices)

        // Initialize BluetoothAdapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            return
        }

        // Set up the ListView adapter
        deviceListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, discoveredDevices)
        listViewDevices.adapter = deviceListAdapter

        // Set button click listener to show connected devices and start discovery
        btnDiscover.setOnClickListener {
            showConnectedDevices() // Show already paired devices
            startBluetoothDiscovery() // Start discovering nearby devices
        }

        // Register for broadcasts when a device is discovered and when discovery is finished
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)

        val filterDiscoveryFinished = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(receiver, filterDiscoveryFinished)
    }

    private fun showConnectedDevices() {
        discoveredDevices.clear() // Clear the list to refresh with newly connected or discovered devices
        val pairedDevices = bluetoothAdapter.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name ?: "Unknown Device"
            val deviceAddress = device.address
            val deviceInfo = "$deviceName - $deviceAddress (Connected)"
            discoveredDevices.add(deviceInfo)
        }
        deviceListAdapter.notifyDataSetChanged()
    }

    private fun startBluetoothDiscovery() {
        // Check Bluetooth permissions for Android 12 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT),
                    1
                )
                return
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                return
            }
        }

        // Enable Bluetooth if it's not enabled
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        } else {
            // Start discovery
            if (bluetoothAdapter.isDiscovering) {
                bluetoothAdapter.cancelDiscovery()
            }
            bluetoothAdapter.startDiscovery()
            Toast.makeText(this, "Starting discovery...", Toast.LENGTH_SHORT).show()
        }
    }

    // BroadcastReceiver for ACTION_FOUND and ACTION_DISCOVERY_FINISHED
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        val deviceName = it.name ?: "Unknown Device"
                        val deviceAddress = it.address
                        val deviceInfo = "$deviceName - $deviceAddress"
                        if (!discoveredDevices.contains(deviceInfo)) {
                            discoveredDevices.add(deviceInfo)
                            deviceListAdapter.notifyDataSetChanged()
                        }
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Toast.makeText(context, "Discovery finished", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        bluetoothAdapter.cancelDiscovery()
    }
}
