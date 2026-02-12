package com.nak.userbase.presentation.navigation

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.nak.userbase.presentation.compose.AddEmployeeScreen
import com.nak.userbase.presentation.compose.LoginScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    googleSignInClient: GoogleSignInClient
) {

    NavHost(navController = navHostController, startDestination = "home") {

        composable("login") {
            LoginScreen(googleSignInClient = googleSignInClient)
        }

        composable("home") {
            HomeScreen() {
                navHostController.navigate("add_employee_screen")
            }
        }

        composable("add_employee_screen") {
            AddEmployeeScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAddClick: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Users List")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.White,
                    containerColor = Color.Blue
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddClick()
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {

        }
    }
}

private fun getBitmapFromUri(
    context: Context,
    item: @JvmSuppressWildcards Uri
): Bitmap {
    val inputStream = context.contentResolver.openInputStream(item)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()
    return bitmap
}