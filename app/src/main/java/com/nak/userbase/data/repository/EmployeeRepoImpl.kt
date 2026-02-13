package com.nak.userbase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.nak.userbase.domain.model.Employee
import com.nak.userbase.domain.repository.EmployeeRepo
import javax.inject.Inject

class EmployeeRepoImpl @Inject constructor(
    private val auth: FirebaseAuth
): EmployeeRepo {
    override suspend fun addEmployee(employee: Employee) {

    }
}