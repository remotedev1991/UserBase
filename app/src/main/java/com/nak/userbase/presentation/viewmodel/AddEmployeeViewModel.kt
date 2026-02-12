package com.nak.userbase.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nak.userbase.domain.model.Employee
import com.nak.userbase.domain.usecase.AddEmpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ApiResult{
    object Loading: ApiResult()
    object Success: ApiResult()
    data class Error(val message: String): ApiResult()
}

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(val addEmpUseCase: AddEmpUseCase) : ViewModel() {

    val firstName = mutableStateOf("") //recomposition
    val age = mutableStateOf("")
    val address = mutableStateOf("")

    val profilePictureUri = mutableStateOf<Uri?>(null)

    fun addEmployee() {
        val employee = Employee(
            name = firstName.value,
            age = age.value.toInt(),
            address = address.value,
            profilePictureUri = profilePictureUri.value.toString()
        )
        viewModelScope.launch {
            addEmpUseCase(employee)
        }
    }

    fun setProfilePictureUri(profilePictureUri: Uri) {
        this.profilePictureUri.value = profilePictureUri
    }
}


//UI -> ViewModel -> UseCase -> Repo -> DataSource

//Entities - Employee, Images
//Dao
//database

//storing the information
//setting up the firebase

