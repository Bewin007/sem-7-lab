package com.example.exp_7

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.exp_7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startServiceButton.setOnClickListener {
            val intent = Intent(this, ForegroundService::class.java)
            startService(intent)
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(videoReceiver, IntentFilter("PlayVideo"))
    }

    private val videoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val videoUrl = intent?.getStringExtra("video_url")
            videoUrl?.let {
                binding.videoView.setVideoURI(Uri.parse(it))
                binding.videoView.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(videoReceiver)
    }
}
