package com.nak.userbase.domain.model

data class Employee(
    val empId: String = "",
    val name: String,
    val age: Int,
    val address: String,
    val profilePictureUri: String,
)
