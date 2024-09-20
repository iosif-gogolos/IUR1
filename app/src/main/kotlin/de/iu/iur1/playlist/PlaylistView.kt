package de.iu.iur1.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.iu.iur1.data.Playlist

enum class PlaylistState { LOADING, LOADED, EMPTY, ERROR }

@Composable
fun PlaylistView(viewModel: PlaylistViewModel = hiltViewModel()) {
    // Collect the playlist state and playlists from the ViewModel
    val playlistState by viewModel.playlistState.collectAsState()
    val playlists by viewModel.playlists.collectAsState()

    // Trigger data loading when the composable is first launched
    LaunchedEffect(Unit) {
        viewModel.loadPlaylists() // Load the playlists from the ViewModel
    }

    // Display the UI based on the state of playlist data
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (playlistState) {
            PlaylistState.LOADING -> CircularProgressIndicator()
            PlaylistState.LOADED -> PlaylistScreen(playlists) // Show the playlist screen
            PlaylistState.EMPTY -> Text(text = "No Playlists Available")
            PlaylistState.ERROR -> Text(text = "Failed to Load Playlists")
        }
    }
}

@Composable
fun PlaylistScreen(playlists: List<Playlist>) {
    LazyColumn {
        items(playlists) { playlist ->
            PlaylistRow(playlist = playlist)
        }
    }
}

@Composable
fun PlaylistRow(playlist: Playlist) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(text = playlist.name)
    }
}
