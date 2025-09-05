package de.iu.iur1.feature.nowplaying.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.iu.iur1.OutlinedBox
import de.iu.iur1.core.ui.components.OutlinedBox
import de.iu.iur1.nowplaying.data.NowPlayingClient

@Composable
fun NowPlayingHeader() {
    var ui by remember { mutableStateOf<Ui>(Ui.Loading) }

    LaunchedEffect(Unit) {
        ui = when (val res = NowPlayingClient.fetch().getOrNull()) {
            null -> Ui.Error
            else -> Ui.Value(res.title, res.artist, false)
        }
    }

    OutlinedBox(heightFraction = 0.25f) {
        Column(Modifier.fillMaxWidth()) {
            Text("Now Playing", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            when (val s = ui) {
                Ui.Loading -> CircularProgressIndicator()
                Ui.Error -> Text("Keine Titelinformationen verfügbar")
                is Ui.Value -> {
                    Text(s.title)
                    Text(s.artist, style = MaterialTheme.typography.bodyMedium)
                    TextButton(onClick = { ui = s.copy(isFavorite = !s.isFavorite) }) {
                        Text(if (s.isFavorite) "★ Favorit" else "☆ Zu Favoriten")
                    }
                }
            }
        }
    }
}

private sealed interface Ui {
    data object Loading : Ui
    data object Error : Ui
    data class Value(val title: String, val artist: String, val isFavorite: Boolean) : Ui
}
