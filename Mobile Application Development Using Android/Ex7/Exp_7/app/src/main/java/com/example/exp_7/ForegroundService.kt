package com.example.exp_7

import android.app.*
import android.content.*
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class ForegroundService : Service() {

    private val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var downloadManager: DownloadManager
    private var downloadId: Long = 0

    private val FILE_URL = "https://drive.google.com/uc?export=download&id=1SD55mNItmrbyeADhojS1EjMlrCtruofY"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener {
            stopSelf()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        downloadFile()

        val videoIntent = Intent("PlayVideo")
        videoIntent.putExtra("video_url", "android.resource://${packageName}/raw/videoplayback")
        LocalBroadcastManager.getInstance(this).sendBroadcast(videoIntent)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Downloading file")
            .setSmallIcon(R.drawable.img)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        return START_NOT_STICKY
    }

    private fun downloadFile() {
        val uri = Uri.parse(FILE_URL)
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "largefile.zip")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManager.enqueue(request)

        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                Toast.makeText(this@ForegroundService, "Download Completed", Toast.LENGTH_SHORT).show()
                updateNotification("Download Complete")
            }
        }
    }

    private fun updateNotification(contentText: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.img)
            .build()
        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        unregisterReceiver(onDownloadComplete)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
