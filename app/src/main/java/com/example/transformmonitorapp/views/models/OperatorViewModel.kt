package com.example.transformmonitorapp.views.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.transformmonitorapp.data.repository.interfaces.OperatorRepository
import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import kotlinx.coroutines.launch

class OperatorViewModel(
    application: Application,
    private val repository: OperatorRepository
) : AndroidViewModel(application) {

    val transformers = MutableLiveData<List<TransformerDto>>()
    val transformer = MutableLiveData<TransformerDto>()
    val alerts = MutableLiveData<List<AlertDto>>()
    val message = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    fun getAllTransformers() {
        println("=== VM: getAllTransformers() ===")
        viewModelScope.launch {
            try {
                val list = repository.getAllTransformers()
                println("=== VM: transformers loaded: ${list.size} ===")
                transformers.value = list
            } catch (e: Exception) {
                println("=== VM ERROR: ${e.message} ===")
                error.value = e.message
            }
        }
    }
    /*fun getAllTransformers() {
        viewModelScope.launch {
            try {
                transformers.value = repository.getAllTransformers()
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }*/

    fun getTransformerById(id: Long) {
        viewModelScope.launch {
            try {
                transformer.value = repository.getTransformerById(id)
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    fun getTransformerAlerts(id: Long) {
        viewModelScope.launch {
            try {
                alerts.value = repository.getTransformerAlerts(id)
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    fun processError(id: Long, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.processTransformerError(id)
                message.value = "Error processed for $id"
                onComplete()
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}
