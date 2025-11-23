package com.example.transformmonitorapp.views.models

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.transformmonitorapp.data.repository.interfaces.AuthRepository
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.request.LogoutRequest
import com.example.transformmonitorapp.domain.model.Role
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    application: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto> get() = _user

    private val _logoutResponse = MutableLiveData<Response<*>>()
    val logoutResponse: LiveData<Response<*>> get() = _logoutResponse


    fun loadUserFromPrefs() {
        val prefs = getApplication<Application>()
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//        val token = prefs.getString("JWT_TOKEN", null)
        val id = prefs.getLong("USER_ID", -1)
        val name = prefs.getString("USER_NAME", null)
        val email = prefs.getString("USER_EMAIL", null)
        val role = prefs.getString("USER_ROLE", null)

        if (id != -1L && name != null && email != null && role != null) {
            _user.value = UserDto(
                id = id,
                nameUKR = name,
                email = email,
                role = Role.valueOf(role)
            )
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.logout(LogoutRequest(token))
                _logoutResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}