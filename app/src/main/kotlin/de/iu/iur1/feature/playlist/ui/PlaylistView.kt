package de.iu.iur1.feature.playlist.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import de.iu.iur1.OutlinedBox
import de.iu.iur1.playlist.PlaylistClient
import de.iu.iur1.feature.playlist.data.PlaylistDataState
import de.iu.iur1.feature.playlist.data.PlaylistEntry
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class PlaylistState { LOADING, LOADED, EMPTY, ERROR }

@Composable
fun PlaylistView() {
    var playlistState by remember { mutableStateOf(PlaylistState.LOADING) }
    val playlistEntries = remember { mutableStateListOf<PlaylistEntry>() }
    var playlistRating by remember { mutableIntStateOf(0) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(selectedDate) {
        playlistState = PlaylistState.LOADING
        playlistEntries.clear()
        playlistState = when (val value = PlaylistClient.cachedOrFetch(selectedDate)) {
            PlaylistDataState.Empty -> PlaylistState.EMPTY
            PlaylistDataState.Error -> PlaylistState.ERROR
            is PlaylistDataState.Value -> {
                playlistEntries.addAll(value.playlistData.entries)
                playlistRating = value.playlistData.rating
                PlaylistState.LOADED
            }
        }
    }

    OutlinedBox(heightFraction = 0.89f) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier.fillMaxSize().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (playlistState) {
                    PlaylistState.LOADING -> CircularProgressIndicator()
                    PlaylistState.LOADED -> Playlist(playlistEntries.toList(), playlistRating, selectedDate)
                    PlaylistState.EMPTY -> Text(text = "Keine Playlist für das ausgewählte Datum")
                    PlaylistState.ERROR -> Text(text = "Die Playlist konnte nicht geladen werden")
                }
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)) {
                FilterButtons(onSelectFilter = { selectedDate = it })
            }
        }
    }
}

@Composable
fun Playlist(entries: List<PlaylistEntry>, rating: Int, date: LocalDate) {
    var playlistRateDialogOpen by remember { mutableStateOf(false) }
    val playlistState = rememberLazyListState()
    var userRating by remember { mutableIntStateOf(1) }

    if (playlistRateDialogOpen) PlaylistRateDialog(
        date = date,
        onDismissRequest = { playlistRateDialogOpen = false },
        userRating,
        onRatingChange = { userRating = it }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Playlist")
        Text(text = "Bewertung: ${rating}/5")
        IconButton(onClick = { playlistRateDialogOpen = true }) {
            Icon(
                imageVector = Icons.Default.RateReview,
                contentDescription = "Rate",
                modifier = Modifier.size(16.dp),
            )
        }
    }
    Divider(modifier = Modifier.padding(0.dp, 5.dp))
    LazyColumn(modifier = Modifier.fillMaxSize(), state = playlistState) {
        items(entries) { entry ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = entry.title)
                Text(text = entry.time)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun PlaylistRateDialog(
    date: LocalDate,
    onDismissRequest: () -> Unit,
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val errorToast = Toast.makeText(context, "Playlist bewertung fehlgeschlagen", Toast.LENGTH_LONG)

    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Playlist bewerten", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(5) { index ->
                    IconButton(
                        onClick = { onRatingChange.invoke(index + 1) }
                    ) {
                        Icon(
                            imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Stern ${index + 1}",
                            modifier = Modifier.size(32.dp),
                            tint = if (index < rating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 10.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text("Abbrechen")
                }
                Button(onClick = {
                   scope.launch {
                       if (PlaylistClient.rate(date, rating)) {
                           onDismissRequest.invoke()
                       } else {
                           errorToast.show()
                       }
                   }
                }) {
                    Text("Senden")
                }
            }
            Spacer(Modifier.height(10.dp))
        }
        }
    }


