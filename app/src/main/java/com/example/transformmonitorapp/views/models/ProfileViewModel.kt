package com.example.transformmonitorapp.views.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.transformmonitorapp.data.repository.interfaces.ProfileRepository
import com.example.transformmonitorapp.domain.dto.request.ChangeEmailRequest
import com.example.transformmonitorapp.domain.dto.request.ChangeNameRequest
import com.example.transformmonitorapp.domain.dto.request.ChangePasswordRequest
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application,
    private val repository: ProfileRepository
) : AndroidViewModel(application) {

    fun changeName(userId: Long, newName: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val request = ChangeNameRequest(userId, newName)
                repository.changeName(request)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                onComplete()
            }
        }
    }

    fun changeEmail(userId: Long, newEmail: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val request = ChangeEmailRequest(userId, newEmail)
                repository.changeEmail(request)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                onComplete()
            }
        }
    }

    fun changePassword(userId: Long, oldPassword: String, newPassword: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val request = ChangePasswordRequest(
                    userId = userId,
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )
                repository.changePassword(request)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                onComplete()
            }
        }
    }
}