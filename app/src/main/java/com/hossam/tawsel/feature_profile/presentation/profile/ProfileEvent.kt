package com.hossam.tawsel.feature_profile.presentation.profile

import com.hossam.tawsel.feature_profile.domain.model.UpdatePassword
import com.hossam.tawsel.feature_profile.domain.model.UpdateProfile

sealed interface ProfileEvent {
    object GetProfile : ProfileEvent
    data class UpdateProfileInfo(val updateProfile: UpdateProfile): ProfileEvent
    data class UpdatePasswordInfo(val updatePassword: UpdatePassword): ProfileEvent
}