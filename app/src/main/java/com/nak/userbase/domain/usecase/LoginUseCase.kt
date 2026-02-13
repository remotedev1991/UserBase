package com.nak.userbase.domain.usecase

import com.nak.userbase.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(idToken: String) = authRepository.googleSingIn(idToken)
}