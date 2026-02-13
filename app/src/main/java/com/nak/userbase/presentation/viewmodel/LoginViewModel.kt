package com.nak.userbase.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nak.userbase.data.local.UserPreferencesManager
import com.nak.userbase.domain.usecase.LoginUseCase
import com.nak.userbase.domain.usecase.LogoutUseCase
import com.nak.userbase.presentation.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<Boolean>(false)
    val logoutState = _logoutState.asStateFlow()

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            val result = loginUseCase(idToken)
            if (result.uid.isNotEmpty()) {
                val uid = FirebaseAuth
                    .getInstance()
                    .currentUser
                    ?.uid ?: ""

                viewModelScope.launch {
                    userPreferencesManager.saveUserId(uid)
                }

                Log.d("TAG", "loginWithGoogle: Success ${result.uid}")
                _loginState.value = AuthUiState.Success(result)
            } else {
                Log.d("TAG", "loginWithGoogle: Error")
                _loginState.value = AuthUiState.Error("Something went wrong")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase()
            if (result.isSuccess) {
                _logoutState.emit(true)
            } else {
                _logoutState.emit(false)
            }
        }
    }
}