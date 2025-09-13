package de.iu.iur1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.iu.iur1.feature.playlist.ui.PlaylistView


@Composable
fun RadioView() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        de.iu.iur1.feature.nowplaying.ui.NowPlayingHeader()
        Spacer(Modifier.height(16.dp))
        PlaylistView()
    }
}
