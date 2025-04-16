package com.example.simpleserverapp.views.filetransfer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpleserverapp.core.model.ServerInfo

@Composable
fun FileTransferScreen(
    server: ServerInfo
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            MiddleContent {
                Text(
                    text = "File Screen",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            MiddleContent {
                Text(
                    text = server.name,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }

            MiddleContent {
                Text(
                    text = server.ip,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            ServerView(
                ip = server.ip
            )
        }
    }
}

@Composable
fun MiddleContent(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        content()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PrevFileScreen() {
    FileTransferScreen(
        server = ServerInfo(
            name = "Preview Screen",
            ip = "Fake IP"
        )
    )
}

