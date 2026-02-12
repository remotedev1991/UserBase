package com.nak.userbase.domain.usecase

import com.nak.userbase.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(idToken: String) = authRepository.googleSingIn(idToken)
}