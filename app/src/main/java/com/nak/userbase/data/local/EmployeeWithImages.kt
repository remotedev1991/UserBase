package com.nak.userbase.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class EmployeeWithImages(

    @Embedded
    val employee: EmployeeEntity,

    @Relation(
        parentColumn = "employeeId",
        entityColumn = "empId"
    ) val images: List<EmployeeImageEntity>
)