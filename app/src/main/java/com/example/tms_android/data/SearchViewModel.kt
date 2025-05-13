package com.example.tms_android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _dataItems = MutableLiveData<List<String>>(listOf(
        "Что такое осень? Это небо",
        "Плачущее небо под ногами",
        "В лужах разлетаются птицы с облаками",
        "Осень, я давно с тобою не был"
    ))
    val dataItems: LiveData<List<String>> = _dataItems

    private val _searchResults = MutableLiveData<List<String>>(dataItems.value)
    val searchResults: LiveData<List<String>> = _searchResults

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private var searchJob: Job? = null
    private val searchQuery = MutableStateFlow("")

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    init {
        setupSearchObserver()
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchObserver() {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    fun performSearch(query: String) {
        searchJob?.cancel()

        if (query.isEmpty()) {
            _searchResults.value = dataItems.value
            _error.value = null
            return
        }

        _loading.value = true
        _error.value = null

        searchJob = viewModelScope.launch {
            try {
                delay(1000)

                if (query.equals("ошибка", ignoreCase = true)) {
                    throw RuntimeException("Ты чего наделал...")
                }

                val results = if (query.isNotBlank()) {
                    dataItems.value?.filter { it.contains(query, ignoreCase = true) }
                } else {
                    dataItems.value
                }

                _searchResults.value = results
                if (results.isNullOrEmpty()) {
                    _error.value = "Ничего не найдено"
                }
            } catch (e: Exception) {
                _error.value = "Ошибка поиска: ${e.message}"
                _searchResults.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}