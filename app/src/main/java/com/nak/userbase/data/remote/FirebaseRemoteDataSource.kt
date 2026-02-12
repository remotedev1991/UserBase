package com.nak.userbase.data.remote

import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.nak.userbase.domain.model.Employee
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
class FirebaseRemoteDataSource @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun uploadEmployeeData(employee: Employee) {
        val employeeRef = firebaseDatabase.reference.child("employees").child(getLoggedInUserId())
        employeeRef.setValue(employee).await()
    }

    private fun getLoggedInUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
    }

    suspend fun uploadingProfileImage(profileImagePath: String, employeeId: String) {

        val file = File(profileImagePath) //convert it into a real file in local storage

        val imageRef = firebaseStorage.reference.child("profile_image/${employeeId}.jpg") //box address

        imageRef.putFile(file.toUri()).await() //here we convert file into bytes then upload

        imageRef.downloadUrl.await().toString()
    }

    suspend fun uploadingImage(imagePath: String, employeeId: String, imageId: String) {

        val file = File(imagePath) //convert it into a real file in local storage

        val imageRef = firebaseStorage.reference.child("employee_images/${employeeId + imageId}.jpg") //box address

        imageRef.putFile(file.toUri()).await() //here we convert file into bytes then upload

        imageRef.downloadUrl.await().toString()
    }



}