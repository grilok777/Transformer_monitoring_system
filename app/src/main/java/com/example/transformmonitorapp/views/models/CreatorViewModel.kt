package com.example.transformmonitorapp.views.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.transformmonitorapp.data.repository.interfaces.CreatorRepository
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.model.Role
import kotlinx.coroutines.launch

class CreatorViewModel(
    application: Application,
    private val creatorRepository: CreatorRepository
) : AndroidViewModel(application) {

    private val _users = MutableLiveData<List<UserDto>>()
    val users: LiveData<List<UserDto>> = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadUsers() {
        viewModelScope.launch {
            try {
                val list = creatorRepository.getUsers()
                _users.value = list
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }

    fun changeRole(id: Long, newRole: String) {
        viewModelScope.launch {
            try {
                creatorRepository.changeUserRole(id, Role.valueOf(newRole))
                loadUsers()
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }
}