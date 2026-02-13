package com.nak.userbase

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
                    CompositionLocalProvider(
                        LocalUser provides User("Vik")
                    ) {
                        LoginScreen(
                            googleSignInClient = googleSignInClient,
                            modifier = Modifier.padding(innerPadding),
                            onLoginSuccess = {}, onLogoutSuccess = {},
                            serviceConnectionHelper = ServiceConnectionHelper(this)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserBaseTheme {
        Greeting("Android")
    }
}