package com.nak.userbase.domain.usecase

import com.nak.userbase.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.logout()
}