package com.nak.userbase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.nak.userbase.domain.model.User
import com.nak.userbase.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override suspend fun googleSingIn(idToken: String): User {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
        return authResult.user.toDomain()
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
