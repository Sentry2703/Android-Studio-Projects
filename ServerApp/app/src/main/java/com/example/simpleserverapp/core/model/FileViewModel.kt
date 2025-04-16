package com.example.simpleserverapp.core.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleserverapp.core.network.createRetrofit
import kotlinx.coroutines.launch

class FileViewModel(baseURL: String): ViewModel() {

    var fileUIState: String by mutableStateOf("")
        private set

    init {
        getFileTypes(baseURL)
    }

    fun getFileTypes(baseURL: String) {
        viewModelScope.launch {
            val typeResult = createRetrofit(baseURL).getFileTypes()
            fileUIState = typeResult
        }
    }
}