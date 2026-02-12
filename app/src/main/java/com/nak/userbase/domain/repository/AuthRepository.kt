package com.nak.userbase.domain.repository

import com.nak.userbase.domain.model.User

interface AuthRepository {
    suspend fun googleSingIn(idToken: String): User
    //Google Sign In with Google and it will be verified with Firebase
}