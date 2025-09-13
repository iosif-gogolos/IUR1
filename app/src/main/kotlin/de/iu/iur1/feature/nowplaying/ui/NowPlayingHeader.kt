package de.iu.iur1.feature.nowplaying.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.iu.iur1.core.ui.components.OutlinedBox
import de.iu.iur1.nowplaying.data.NowPlayingClient

@Composable
fun NowPlayingHeader() {
    var ui by remember { mutableStateOf<Ui>(Ui.Loading) }
    var showRatingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        ui = when (val res = NowPlayingClient.fetch().getOrNull()) {
            null -> Ui.Error
            else -> Ui.Value(res.title, res.artist, false, 0, false)
        }
    }

    OutlinedBox(modifier = Modifier.clickable { showRatingDialog = true }) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Prominenter Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Music",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "NOW PLAYING",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            when (val s = ui) {
                Ui.Loading -> CircularProgressIndicator()
                Ui.Error -> Text("Keine Titelinformationen verfügbar")
                is Ui.Value -> {
                    // Song-Titel prominent
                    Text(
                        text = s.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.height(4.dp))

                    // Artist
                    Text(
                        text = s.artist,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(12.dp))

                    // Bewertungsanzeige
                    if (s.hasRated) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Deine Bewertung: ",
                                style = MaterialTheme.typography.bodySmall
                            )
                            repeat(5) { index ->
                                Icon(
                                    imageVector = if (index < s.rating) Icons.Filled.Star else Icons.Outlined.Star,
                                    contentDescription = "Star ${index + 1}",
                                    modifier = Modifier.size(16.dp),
                                    tint = if (index < s.rating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.3f
                                    )
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "Tippen zum Bewerten",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Favoriten-Button (bereits vorhanden)
                    TextButton(onClick = { ui = s.copy(isFavorite = !s.isFavorite) }) {
                        Text(if (s.isFavorite) "★ Favorit" else "☆ Zu Favoriten")
                    }
                }
            }
        }
    }

    // Bewertungs-Dialog
    if (showRatingDialog) {
        (ui as? Ui.Value)?.let { s ->
            SongRatingDialog(
                songTitle = s.title,
                artist = s.artist,
                currentRating = s.rating,
                onRatingChanged = { newRating ->
                    ui = s.copy(rating = newRating, hasRated = true)
                    showRatingDialog = false
                },
                onDismiss = { showRatingDialog = false }
            )
        }
    }

    if (showRatingDialog) {
        val s = ui
        if (s is Ui.Value) {
            SongRatingDialog(
                songTitle = s.title,
                artist = s.artist,
                currentRating = s.rating,
                onRatingChanged = { newRating ->
                    ui = s.copy(rating = newRating, hasRated = true)
                    showRatingDialog = false
                },
                onDismiss = { showRatingDialog = false }
            )
        }
    }
}

@Composable
private fun SongRatingDialog(
    songTitle: String,
    artist: String,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var tempRating by remember { mutableIntStateOf(currentRating) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Song bewerten",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$songTitle - $artist",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Wie gefällt dir dieser Song?",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Sterne-Bewertung
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(5) { index ->
                        IconButton(
                            onClick = { tempRating = index + 1 }
                        ) {
                            Icon(
                                imageVector = if (index < tempRating) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Stern ${index + 1}",
                                modifier = Modifier.size(32.dp),
                                tint = if (index < tempRating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            )
                        }
                    }
                }

                // Rating-Text
                Text(
                    text = when (tempRating) {
                        0 -> "Keine Bewertung"
                        1 -> "Sehr schlecht"
                        2 -> "Schlecht"
                        3 -> "Okay"
                        4 -> "Gut"
                        5 -> "Sehr gut"
                        else -> ""
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onRatingChanged(tempRating) },
                enabled = tempRating > 0
            ) {
                Text("Bewerten")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        }
    )
}

// Erweiterte UI State-Klasse
private sealed interface Ui {
    data object Loading : Ui
    data object Error : Ui
    data class Value(
        val title: String,
        val artist: String,
        val isFavorite: Boolean,
        val rating: Int = 0,
        val hasRated: Boolean = false
    ) : Ui
}