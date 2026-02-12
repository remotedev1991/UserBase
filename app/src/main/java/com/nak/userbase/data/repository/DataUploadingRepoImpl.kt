package com.nak.userbase.data.repository

import com.nak.userbase.data.remote.FirebaseRemoteDataSource
import com.nak.userbase.domain.model.Employee
import com.nak.userbase.domain.repository.DataUploaderRepo
import javax.inject.Inject

class DataUploadingRepoImpl @Inject constructor(
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource //it will be provided by hilt
) : DataUploaderRepo {

    override suspend fun uploadEmployeeData(employee: Employee) {
         firebaseRemoteDataSource.uploadEmployeeData(employee)
    }

    override suspend fun uploadProfileImage(
        profileImagePath: String,
        employeeId: String
    ) {
        firebaseRemoteDataSource.uploadingProfileImage(profileImagePath, employeeId)
    }

    override suspend fun uploadImage(
        imagePath: String,
        employeeId: String,
        imageId: String
    ) {
        firebaseRemoteDataSource.uploadingImage(imagePath, employeeId, imageId)
    }
}