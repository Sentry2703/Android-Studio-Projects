package com.example.simpleserverapp.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ServerInfo(val name: String, val ip: String)

sealed interface FileUIState {
    data class TypeSuccess(val files: FileTypes): FileUIState
    data class ListSuccess(val typedList: ListResponse) : FileUIState
    data class FileSuccess(val fileName: String) : FileUIState
    data class Error(val error: String = "Something went wrong"): FileUIState
    object Loading : FileUIState
}

@Serializable
data class FileTypes(
    @SerialName(value = "types")
    val types: List<String>
)

@Serializable
data class TypedFile(
    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "preview_url")
    val previewURL: String,

    @SerialName(value = "url")
    val url: String,

    @SerialName(value = "size")
    val size: String
)

@Serializable
data class ListResponse(
    @SerialName(value = "category")
    val category: String,

    @SerialName(value = "files")
    val files: List<TypedFile>
)