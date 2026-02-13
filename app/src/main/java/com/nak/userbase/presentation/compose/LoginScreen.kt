package com.nak.userbase.presentation.compose

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.nak.userbase.presentation.state.AuthUiState
import com.nak.userbase.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    googleSignInClient: GoogleSignInClient,
    onLoginSuccess: () -> Unit
) {

    val viewModel: LoginViewModel = hiltViewModel()

    val loginState = viewModel.loginState.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            handleSignInResult(result.data, viewModel)
        } else {
            Log.d("TAG", "LoginScreen: RESULT NOT OK")
        }
    }

    Card(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to UserBase",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            when (loginState.value) {
                is AuthUiState.Error -> {
                    val message = (loginState.value as AuthUiState.Error).message
                    Text(message, style = MaterialTheme.typography.titleLarge, color = Color.Red)

                    Spacer(Modifier.height(20.dp))

                    Button(onClick = {
                        launcher.launch(googleSignInClient.signInIntent)
                    }) {
                        Text("Login with google", Modifier.padding(12.dp))
                    }
                }

                AuthUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is AuthUiState.Success -> {
                    onLoginSuccess()
                }

                AuthUiState.Idle -> {

                    Spacer(Modifier.height(20.dp))

                    Button(onClick = {
                        launcher.launch(googleSignInClient.signInIntent)
                    }) {
                        Text("Login with google", Modifier.padding(12.dp))
                    }
                }
            }
        }
    }
}

fun handleSignInResult(data: Intent?, viewModel: LoginViewModel) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    try {
        val account = task.getResult(ApiException::class.java)
        val idToken = account.idToken
        idToken?.let { viewModel.loginWithGoogle(it) }
    } catch (e: ApiException) {
        Log.d("TAG", "handleSignInResult: $e")
    }
}

@PreviewScreenSizes()
@Composable
fun LoginScreenPreview() {

}