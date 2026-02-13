package com.nak.userbase

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.nak.userbase.presentation.LocalUser
import com.nak.userbase.presentation.util.ServiceConnectionHelper
import com.nak.userbase.presentation.User
import com.nak.userbase.presentation.components.MediaPlayBackService
import com.nak.userbase.presentation.compose.LoginScreen
import com.nak.userbase.presentation.navigation.AppNavGraph
import com.nak.userbase.ui.theme.UserBaseTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserBaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", Modifier.padding(innerPadding)
                    ) {
                        val notificaiton = createNotification()
                        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                        manager.notify(10, notificaiton)
                    }
                }
            }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("TAG", "onReceive: ${intent?.getStringExtra("data")}")
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("intent_received")
        registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    fun createNotification(): Notification {

        val channelId = "media_playback_test"

        val channel = NotificationChannel(
            channelId,
            "media_test",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        //handle the click on the notification
        val pendingIntent = PendingIntent.getActivity(
            this@MainActivity,
            100,
            Intent(this, MainActivity::class.java), //this is the target activity
            PendingIntent.FLAG_IMMUTABLE
        )

        //Builder pattern
        return Notification.Builder(this, channelId)
            .setContentTitle("Testing")
            .setContentText("Testing Notification body")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            context.startForegroundService(Intent(context, MediaPlayBackService::class.java))
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK,
                Manifest.permission.POST_NOTIFICATIONS
            )
        )
    }

    Text(
        text = "Hello $name!",
        modifier = modifier.clickable {
            onButtonClick()
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserBaseTheme {
        Greeting("Android") {

        }
    }
}