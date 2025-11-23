package com.example.transformmonitorapp.views.models

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.transformmonitorapp.data.repository.interfaces.AuthRepository
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.request.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.Response
import androidx.core.content.edit
import com.example.transformmonitorapp.domain.dto.response.JwtResponse

class LoginViewModel(
    application: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    private val _loginResponse = MutableLiveData<Response<JwtResponse>>()
    val loginResponse: LiveData<Response<JwtResponse>> get() = _loginResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(LoginRequest(email, password))
                _loginResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveUserData(token: String, user: UserDto) {
        val prefs = getApplication<Application>()
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        prefs.edit {
            putString("JWT_TOKEN", token)
            putLong("USER_ID", user.id)
            putString("USER_NAME", user.nameUKR)
            putString("USER_EMAIL", user.email)
            putString("USER_ROLE", user.role.name)
        }
    }
}