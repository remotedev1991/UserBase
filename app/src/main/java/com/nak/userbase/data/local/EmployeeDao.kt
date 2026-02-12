package com.nak.userbase.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: EmployeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<EmployeeImageEntity>)

    @Query("SELECT * FROM employees WHERE employeeId = :id")
    suspend fun getEmployeesWithImages(id: String): List<EmployeeWithImages>

    @Query("SELECT * FROM employees WHERE employeeId = :id")
    suspend fun getEmployeeById(id: String): EmployeeEntity

    @Query("SELECT * FROM employee_images WHERE empId = :id")
    suspend fun getImagesByEmployeeById(id: String): List<EmployeeImageEntity>
}