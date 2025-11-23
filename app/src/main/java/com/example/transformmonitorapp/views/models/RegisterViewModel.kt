package com.example.transformmonitorapp.views.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.transformmonitorapp.data.repository.interfaces.AuthRepository
import com.example.transformmonitorapp.domain.dto.request.RegisterRequest
import com.example.transformmonitorapp.domain.dto.response.MessageResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(
    application: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    private val _registerResponse = MutableLiveData<Response<MessageResponse>>()
    val registerResponse: LiveData<Response<MessageResponse>> get() = _registerResponse

    fun register(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.registerUser(
                    RegisterRequest(email, password, fullName)
                )
                _registerResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _registerResponse.value = null
            }
        }
    }
}