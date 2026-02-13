package com.nak.userbase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nak.userbase.presentation.components.MediaPlayBackService
import com.nak.userbase.presentation.util.ACTION_STOP_SERVICE

class StopServiceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        if (intent?.action == ACTION_STOP_SERVICE) {
            val serviceIntent = Intent(context, MediaPlayBackService::class.java)
            serviceIntent.action = ACTION_STOP_SERVICE
            context.startService(serviceIntent)
        }
    }
}
