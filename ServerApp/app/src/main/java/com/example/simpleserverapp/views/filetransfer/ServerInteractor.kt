package com.example.simpleserverapp.views.filetransfer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpleserverapp.core.model.FileUIState
import com.example.simpleserverapp.core.model.FileViewModel
import com.example.simpleserverapp.core.network.createRetrofit
import com.example.simpleserverapp.views.start.ButtonList
import com.example.simpleserverapp.views.start.ErrorScreen
import com.example.simpleserverapp.views.start.LoadingScreen

@Composable
fun ServerView(ip: String) {

    val baseUrl = ip
    val fileApi = remember(baseUrl) { createRetrofit(baseUrl) }
    val viewModel: FileViewModel = viewModel(
        factory = FileViewModel.provideFactory(fileApi)
    )

    LaunchedEffect(ip) {
        viewModel.getFileTypes()
    }

    Box (
        modifier = Modifier.padding()
    ) {
        when (viewModel.fileUIState) {
            is FileUIState.TypeSuccess -> {
                Box (
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        ButtonList(
                            (viewModel.fileUIState as FileUIState.TypeSuccess).files.types,
                        ) { type ->
                            viewModel.getFilesOfType(type)
                        }
                    }

                    Row(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 2.dp)
                    ) {
                        FloatingActionButton(
                            onClick = { viewModel.getFileTypes() },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    }
                }
            }
            is FileUIState.ListSuccess -> {
                Box (
                    modifier = Modifier.fillMaxSize()
                ) {
                    val fileCategory = (viewModel.fileUIState as FileUIState.ListSuccess).typedList.category
                    val fileList =
                        (viewModel.fileUIState as FileUIState.ListSuccess).typedList.files.map { it.name }
                    Column(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        ButtonList(fileList) { file ->
                            viewModel.getFile(type = fileCategory, name = file)
                        }
                    }

                    Row(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 2.dp)
                    ) {
                        FloatingActionButton(
                            onClick = { viewModel.getFilesOfType(fileCategory) },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    }
                }
            }
            is FileUIState.FileSuccess -> {
                Text(
                    text = "${(viewModel.fileUIState as FileUIState.FileSuccess).fileName} was gotten successfully"
                )
            }
            is FileUIState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            is FileUIState.Error -> {
                ErrorScreen(errorText = (viewModel.fileUIState as FileUIState.Error).error, modifier = Modifier.fillMaxSize())
            }
        }
    }
}