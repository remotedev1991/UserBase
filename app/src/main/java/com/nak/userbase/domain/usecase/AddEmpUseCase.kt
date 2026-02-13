package com.nak.userbase.domain.usecase

import com.nak.userbase.domain.model.Employee
import com.nak.userbase.domain.repository.EmployeeRepo
import javax.inject.Inject

class AddEmpUseCase @Inject constructor(private val employeeRepo: EmployeeRepo) {
    suspend operator fun invoke(employee: Employee) = employeeRepo.addEmployee(employee)
}