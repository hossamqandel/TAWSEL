package com.hossam.tawsel.feature_profile.domain.use_case

data class ProfileUseCases(
    val getProfileUseCase: GetProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase
)
