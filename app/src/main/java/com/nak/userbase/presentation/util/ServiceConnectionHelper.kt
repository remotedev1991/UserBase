package com.nak.userbase.presentation.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.nak.userbase.presentation.components.MediaPlayBackService

class ServiceConnectionHelper(val context: Context) {

    var mediaPlayBackService: MediaPlayBackService? = null

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            Log.d("TAG", "onServiceConnected: ")
            val localBinder = service as MediaPlayBackService.LocalBinder
            mediaPlayBackService = localBinder.getService()
            val data = mediaPlayBackService?.testServiceFunction()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mediaPlayBackService = null
        }
    }

    fun bindService() {
        Log.d(TAG, "bindService: ")
        context.bindService(
            Intent(context, MediaPlayBackService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun unBindService() {
        Log.d(TAG, "unBindService: ")
        context.unbindService(serviceConnection)
    }

    companion object {
        private const val TAG = "ServiceConnectionHelper"
    }

}