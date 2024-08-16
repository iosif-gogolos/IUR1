package de.iu.iur1.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.iu.iur1.OutlinedBox
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

    OutlinedBox {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (playlistState) {
                PlaylistState.LOADING -> CircularProgressIndicator()
                PlaylistState.LOADED -> Playlist(playlistEntries.toList(), playlistRating)
                PlaylistState.EMPTY -> Text(text = "Keine Playlist für das ausgewählte Datum")
                PlaylistState.ERROR -> Text(text = "Die Playlist konnte nicht geladen werden")
            }
        }
        FilterButtons(onSelectFilter = { selectedDate = it })
    }
}

@Composable
fun Playlist(entries: List<PlaylistEntry>, rating: Int) {
    val playlistState = rememberLazyListState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.05f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Playlist")
        Text(text = "Bewertung: ${rating}/5")
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
