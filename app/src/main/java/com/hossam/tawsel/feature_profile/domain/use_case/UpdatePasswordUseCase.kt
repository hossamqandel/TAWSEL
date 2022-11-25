package com.hossam.tawsel.feature_profile.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.SharedPref
import com.hossam.tawsel.feature_profile.domain.model.UpdatePassword
import com.hossam.tawsel.feature_profile.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePasswordUseCase (
    private val repo: IProfileRepository
) {

    operator fun invoke(updatePassword: UpdatePassword): Flow<Resource<Any>> {
        val password = updatePassword.password
        val passwordConfirmation = updatePassword.password_confirmation

        return if (password != passwordConfirmation){
            flow { emit(Resource.Error("The password confirmation does not match")) }
        } else if (password == passwordConfirmation && password.length < 9){
            flow { emit(Resource.Error("Password should be 9 chars at least")) }
        } else if (passwordConfirmation.isBlank()){
            flow { emit(Resource.Error("password Confirmation can't be empty")) }
        }
        else if (password.isBlank() || passwordConfirmation.isBlank()){
            flow { emit(Resource.Error("You must fill all inputs")) }
        } else {
            SharedPref.setUserPassword(password)
            repo.updatePassword(updatePassword)
        }
    }
}