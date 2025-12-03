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
import com.example.transformmonitorapp.domain.dto.request.LogoutRequest
import com.example.transformmonitorapp.domain.model.Role
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    application: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserDto>()

    private val _logoutResponse = MutableLiveData<Response<*>>()

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

    fun loadUserFromPrefs(): UserDto? {
        val id = securePrefs.getLong("USER_ID", -1)
        val name = securePrefs.getString("USER_NAME", null)
        val email = securePrefs.getString("USER_EMAIL", null)
        val roleName = securePrefs.getString("USER_ROLE", null)

        val role = try { roleName?.let { Role.valueOf(it) } } catch (_: Exception) { null }

        return if (id != -1L && name != null && email != null && role != null) {
            val user = UserDto(id = id, nameUKR = name, email = email, role = role)
            _user.value = user
            user
        } else {
            null
        }
    }

    /** Logout */
    fun logout(token: String, function: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = authRepository.logout(LogoutRequest(token))
                _logoutResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                function()
            }
        }
    }
    fun loadToken(): String? = securePrefs.getString("JWT_TOKEN", null)

    fun saveUserToPrefs(user: UserDto) {
        securePrefs.edit {
            putLong("USER_ID", user.id)
            putString("USER_NAME", user.nameUKR)
            putString("USER_EMAIL", user.email)
            putString("USER_ROLE", user.role.name)
            apply()
        }
        _user.value = user
    }

}