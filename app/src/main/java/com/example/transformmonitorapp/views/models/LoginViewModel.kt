package com.example.transformmonitorapp.views.models

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.transformmonitorapp.data.repository.interfaces.AuthRepository
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.dto.request.LoginRequest
import com.example.transformmonitorapp.domain.dto.response.JwtResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    application: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    private val _loginResponse = MutableLiveData<Response<JwtResponse>>()
    val loginResponse: LiveData<Response<JwtResponse>> get() = _loginResponse

    private val securePrefs: SharedPreferences = initEncryptedPrefs(application)

    private fun initEncryptedPrefs(app: Application): SharedPreferences {
        val masterKey = MasterKey.Builder(app.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            app.applicationContext,
            "secure_app_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

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

    /** Зберегти токен і дані користувача в EncryptedSharedPreferences */
    fun saveUserData(access: String, refresh: String, user: UserDto) {
        securePrefs.edit {
            putString("ACCESS_TOKEN", access)
            putString("REFRESH_TOKEN", refresh)

            putLong("USER_ID", user.id)
            putString("USER_NAME", user.nameUKR)
            putString("USER_EMAIL", user.email)
            putString("USER_ROLE", user.role.name)
        }
    }
}