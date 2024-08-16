package de.iu.iur1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    PlaylistView()
}
