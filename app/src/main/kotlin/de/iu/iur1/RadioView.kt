package de.iu.iur1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.iu.iur1.playlist.PlaylistViewModel
import de.iu.iur1.playlist.PlaylistView

@Composable
fun RadioView() = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
) {
    OutlinedBox(heightFraction = 0.25f) {
        Text(text = "Now Playing")
    }
    Spacer(modifier = Modifier.height(10.dp))

    // Call the PlaylistView which will now use PlaylistViewModel to fetch and display playlists
    PlaylistView()
}

@Composable
fun PlaylistView(viewModel: PlaylistViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // Collect the playlists from the ViewModel's StateFlow
    val playlists by viewModel.playlists.collectAsState()

    if (playlists.isEmpty()) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp)) // Show loading indicator if empty
    } else {
        Column {
            Text(text = "Playlists:", modifier = Modifier.padding(8.dp))
            playlists.forEach { playlist ->
                Text(text = playlist.name, modifier = Modifier.padding(8.dp)) // Display each playlist's name
            }
        }
    }
}
