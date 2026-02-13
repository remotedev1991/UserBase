package com.nak.userbase.presentation

import androidx.compose.runtime.compositionLocalOf

data class User(val name: String)

val LocalUser = compositionLocalOf<User> {
    error("No User provided")
}



