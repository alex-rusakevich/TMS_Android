package com.example.tms_android.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatsViewModel : ViewModel() {
    private val _stringsState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val stringsState: StateFlow<UiState<List<String>>> = _stringsState
    private val repository: CatsRepository = CatsRepository()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _stringsState.value = UiState.Loading
            repository.fetchStrings()
                .onSuccess { strings ->
                    _stringsState.value = UiState.Success(strings)
                }
                .onFailure { error ->
                    _stringsState.value = UiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}