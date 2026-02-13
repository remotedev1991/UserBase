package com.nak.userbase.presentation.components

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.nak.userbase.MainActivity
import com.nak.userbase.R

class MediaPlayBackService : Service() {

    override fun onCreate() {
        super.onCreate()

        startForeground(1, createNotification())
    }

    fun createNotification(): Notification {

        val channelId = "media_playback"

        val channel = NotificationChannel(
            channelId,
            "media",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(channel)


        //handle the click on the notification
        val pendingIntent = PendingIntent.getActivity(
            this,
            100,
            Intent(this, MainActivity::class.java), //this is the target activity
            PendingIntent.FLAG_IMMUTABLE
        )

        //Builder pattern
        return Notification.Builder(this, channelId)
            .setContentTitle("Media")
            .setContentText("Notification body")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()
    }

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): MediaPlayBackService {
            return this@MediaPlayBackService //exposing
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val TAG = "MediaPlayBackService"

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    fun testServiceFunction(): String {
        Log.d("TAG", "testServiceFunction: ")
        return "data from service"
    }
}