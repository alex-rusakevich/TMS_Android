package com.example.tms_android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class ViewModel : ViewModel() {

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data class Success(val data: String) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableLiveData<UiState>(UiState.Idle)
    val state: LiveData<UiState> = _state

    fun loadData() {
        _state.value = UiState.Loading

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    delay(3000)

                    if (Random.nextFloat() < 0.3f) {
                        throw Exception("Ошибка загрузки данных")
                    }

                    "Успешная загрузка!"
                }
                _state.postValue(UiState.Success(result))
            } catch (e: Exception) {
                _state.postValue(UiState.Error(e.message ?: "Неизвестная ошибка"))
            }
        }
    }
}