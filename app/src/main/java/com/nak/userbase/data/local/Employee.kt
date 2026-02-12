package com.nak.userbase.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey
    val employeeId: String = UUID.randomUUID().toString(),

    val name: String,
    val address: String,
    val profileImagePath: String,
    val age: Int,

    val isSynced: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
)
