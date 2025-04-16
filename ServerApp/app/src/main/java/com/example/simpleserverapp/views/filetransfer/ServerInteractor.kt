package com.example.simpleserverapp.views.filetransfer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpleserverapp.core.model.FileViewModel

@Composable
fun ServerView(ip: String, viewModel: FileViewModel = FileViewModel(ip) ) {

    LaunchedEffect(ip) {
        viewModel.getFileTypes(ip)
    }

    Box (
        modifier = Modifier.padding()
    ) {
        Text(
            text = viewModel.fileUIState
        )
    }
}