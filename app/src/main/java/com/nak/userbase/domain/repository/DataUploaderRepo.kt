package com.nak.userbase.domain.repository

import com.nak.userbase.domain.model.Employee

interface DataUploaderRepo {

    suspend fun uploadEmployeeData(employee: Employee)

    suspend fun uploadProfileImage(profileImagePath: String, employeeId: String)

    suspend fun uploadImage(imagePath: String, employeeId: String, imageId: String)

}