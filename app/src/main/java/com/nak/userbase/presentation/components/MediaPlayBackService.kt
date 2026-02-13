package com.nak.userbase.presentation.components

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MediaPlayBackService : Service() {

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): MediaPlayBackService {
            return this@MediaPlayBackService //exposing
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun testServiceFunction(): String {
        Log.d("TAG", "testServiceFunction: ")
        return "data from service"
    }
}