package com.nak.userbase.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "employee_images",
    foreignKeys = [
        ForeignKey(
           entity = Employee::class, //origin of the foreign key
           parentColumns = ["employeeId"], //primary key of the parent
           childColumns = ["empId"],//foreign key of the child
           onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EmployeeImageEntity(
    @PrimaryKey
    val imageId: String = UUID.randomUUID().toString(),
    val imagePath: String,
    val empId: String,
    val isUploaded: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
