package com.nak.userbase.presentation.state

import com.nak.userbase.domain.model.User

sealed class AuthUiState {
    object Loading : AuthUiState()
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}