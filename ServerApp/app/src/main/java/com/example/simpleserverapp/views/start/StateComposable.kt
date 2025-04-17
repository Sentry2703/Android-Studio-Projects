package com.example.simpleserverapp.views.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.example.simpleserverapp.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorText: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_connection_error),
                contentDescription = "Error"
        )

        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(5.dp)
        )

        Text(
            text = errorText,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun ButtonList(cards: List<String>, onClick: (String) -> Unit) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            cards.forEach { card ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = { onClick(card) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = card,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
}


@Preview(showBackground = true)
@Composable
fun ButtonListPreview() {
    val cards = mutableListOf<String>("images", "text", "video")
    ButtonList(cards) {}
}