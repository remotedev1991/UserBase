package com.nak.userbase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.nak.userbase.data.local.UserPreferencesManager
import com.nak.userbase.domain.model.User
import com.nak.userbase.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userPreferencesManager: UserPreferencesManager
) : AuthRepository {

    override suspend fun googleSingIn(idToken: String): User {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
        return authResult.user.toDomain()
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            userPreferencesManager.saveUserId("")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


private fun FirebaseUser?.toDomain(): User {
    return User(
        name = this?.displayName ?: "",
        uid = this?.uid ?: "",
        email = this?.email ?: "",
        photoUrl = this?.photoUrl.toString()
    )
}
