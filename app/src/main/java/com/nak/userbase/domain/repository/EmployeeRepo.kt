package com.nak.userbase.domain.repository

import com.nak.userbase.domain.model.Employee

interface EmployeeRepo {
    suspend fun addEmployee(employee: Employee)
}