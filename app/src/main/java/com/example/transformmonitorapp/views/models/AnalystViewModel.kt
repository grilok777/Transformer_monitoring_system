package com.example.transformmonitorapp.views.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.transformmonitorapp.data.repository.interfaces.AnalyticRepository
import com.example.transformmonitorapp.domain.dto.AlertDto
import com.example.transformmonitorapp.domain.dto.TransformerDto
import kotlinx.coroutines.launch

class AnalystViewModel(
    application: Application,
    private val analyticRepository: AnalyticRepository
) : AndroidViewModel(application) {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _transformerResult = MutableLiveData<TransformerDto?>()
    val transformerResult: LiveData<TransformerDto?> = _transformerResult

    private val _transformerList = MutableLiveData<List<TransformerDto>>()
    val transformerList: LiveData<List<TransformerDto>> = _transformerList

    private val _textResult = MutableLiveData<String>()
    val textResult: LiveData<String> = _textResult

    val transformer = MutableLiveData<TransformerDto>()
    val alerts = MutableLiveData<List<AlertDto>>()


    fun exportTransformer(id: Long) {
        viewModelScope.launch {
            try {
                val resp = analyticRepository.exportTransformer(id)
                if (resp.isSuccessful) {
                    _transformerResult.postValue(resp.body())
                    //_status.postValue("Експорт успішний")
                } else {
                    _status.postValue("Помилка експорту: ${resp.code()}")
                }
            } catch (e: Exception) {
                _status.postValue("Exception: ${e.message}")
            }
        }
    }

    fun exportTransformersRange(from: Long, to: Long) {
        viewModelScope.launch {
            try {
                val resp = analyticRepository.exportTransformerRange(from, to)
                if (resp.isSuccessful) {
                    _transformerList.postValue(resp.body() ?: emptyList())
                    //_status.postValue("Експорт діапазону успішний")
                } else {
                    _status.postValue("Помилка експорту діапазону: ${resp.code()}")
                }
            } catch (e: Exception) {
                _status.postValue("Exception: ${e.message}")
            }
        }
    }

    fun exportAllTransformers() {
        viewModelScope.launch {
            try {
                val resp = analyticRepository.exportAllTransformers()
                if (resp.isSuccessful) {
                    _transformerList.postValue(resp.body() ?: emptyList())
                    //_status.postValue("Експорт всіх успішний")
                } else {
                    _status.postValue("Помилка експорту всіх: ${resp.code()}")
                }
            } catch (e: Exception) {
                _status.postValue("Exception: ${e.message}")
            }
        }
    }

    fun getAllAlerts() {
        viewModelScope.launch {
            try {
                val resp = analyticRepository.getAllAlerts()
                if (resp.isSuccessful) {
                    val data = resp.body() ?: emptyList()

                    // 1. Оновлюємо список для RecyclerView (ВАЖЛИВО!)
                    alerts.postValue(data)

                    // 2. Оновлюємо текстовий результат (якщо треба для налагодження)
                    _textResult.postValue(data.joinToString("\n"))

                    // 3. Статус: пишемо "успішно", а не "помилки", щоб не плутати
                    //_status.postValue("Завантажено попереджень: ${data.size}")
                } else {
                    _status.postValue("Помилка отримання даних: ${resp.code()}")
                }
            } catch (e: Exception) {
                _status.postValue("Exception: ${e.message}")
            }
        }
    }

    fun getCriticalAlerts() {
        viewModelScope.launch {
            try {
                val resp = analyticRepository.getCriticalAlerts()
                if (resp.isSuccessful) {
                    val data = resp.body() ?: emptyList()

                    // 1. Оновлюємо список для RecyclerView (ВАЖЛИВО!)
                    alerts.postValue(data)

                    // 2. Оновлюємо текстовий результат
                    _textResult.postValue(data.joinToString("\n"))

                    // 3. Статус
                    //_status.postValue("Завантажено критичних: ${data.size}")
                } else {
                    _status.postValue("Помилка отримання критичних: ${resp.code()}")
                }
            } catch (e: Exception) {
                _status.postValue("Exception: ${e.message}")
            }
        }
    }

    fun exportLogs(id: Long) {
        viewModelScope.launch {
            try {
                val resp = analyticRepository.exportLogs(id)
                if (resp.isSuccessful) {
                    _textResult.postValue(resp.body()?.joinToString("\n") ?: "Немає логів")
                    //_status.postValue("Експорт логів успішний")
                } else {
                    _status.postValue("Помилка експорту логів: ${resp.code()}")
                }
            } catch (e: Exception) {
                _status.postValue("Exception: ${e.message}")
            }
        }
    }
    fun clearState() {
        _status.value = "" // або null, якщо зміните тип на String?
        _transformerResult.value = null
        _transformerList.value = emptyList()
        _textResult.value = ""
        alerts.value = emptyList()
    }
}