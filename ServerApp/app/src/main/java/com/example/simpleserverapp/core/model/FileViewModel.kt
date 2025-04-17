package com.example.simpleserverapp.core.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.simpleserverapp.core.network.FileApiService
import kotlinx.coroutines.launch
import java.io.IOException

class FileViewModel(val fileApi: FileApiService): ViewModel() {

    companion object {
        fun provideFactory(fileApi: FileApiService): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(FileViewModel::class.java)) {
                        return FileViewModel(fileApi) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

    var fileUIState: FileUIState by mutableStateOf(FileUIState.Loading)
        private set

    fun getFileTypes() {
        viewModelScope.launch {
            try {
                val typeResult = fileApi.getFileTypes()
                fileUIState = FileUIState.TypeSuccess(typeResult)
            } catch (e: IOException) {
                fileUIState = FileUIState.Error()
            }
        }
    }

    fun getFilesOfType(type: String) {
        viewModelScope.launch {
            try {
                val listResult = fileApi.getFilesOfType(type)
                fileUIState = FileUIState.ListSuccess(listResult)
            } catch (e: IOException) {
                fileUIState = FileUIState.Error()
            } catch (e : Exception) {
                fileUIState = FileUIState.Error(error = e.message.toString())
            }
        }
    }

    fun getFile(type: String, name: String) {
        viewModelScope.launch {
            fileUIState = FileUIState.FileSuccess(name)
        }
    }


}
