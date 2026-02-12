package com.nak.userbase.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nak.userbase.domain.usecase.LoginUseCase
import com.nak.userbase.presentation.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Loading)
    val loginState = _loginState.asStateFlow()

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            val result = loginUseCase(idToken)
            if (result.uid.isNotEmpty()) {
                Log.d("TAG", "loginWithGoogle: Success ${result.uid}")
                _loginState.value = AuthUiState.Success(result)
            } else {
                Log.d("TAG", "loginWithGoogle: Error")
                _loginState.value = AuthUiState.Error("Something went wrong")
            }
        }
    }
}