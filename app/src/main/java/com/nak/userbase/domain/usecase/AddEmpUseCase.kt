package com.nak.userbase.domain.usecase

import com.nak.userbase.domain.model.Employee
import com.nak.userbase.domain.repository.EmployeeRepo

class AddEmpUseCase(private val employeeRepo: EmployeeRepo) {
    suspend operator fun invoke(employee: Employee) = employeeRepo.addEmployee(employee)
}