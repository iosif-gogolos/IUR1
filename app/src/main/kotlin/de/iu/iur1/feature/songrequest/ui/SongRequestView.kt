package de.iu.iur1.feature.songrequest.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import de.iu.iur1.feature.songrequest.data.SongRequestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SongRequest() {
    val scope = rememberCoroutineScope()
    var showSongRequestDialog by remember { mutableStateOf(false) }

    if (showSongRequestDialog) SongRequestDialog(scope, onDismissRequests = {
        showSongRequestDialog = false
    })

    Column(
        modifier = Modifier.fillMaxWidth().height(68.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledTonalButton(onClick = { showSongRequestDialog = true }) { Text("Songwunsch") }
    }
}

@Composable
fun SongRequestDialog(scope: CoroutineScope, onDismissRequests: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var album by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }

    val context = LocalContext.current
    val errorToast = Toast.makeText(context, "Songwunsch fehlgeschlagen", Toast.LENGTH_LONG)

    Dialog(onDismissRequest = onDismissRequests) {
        Card(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Songwunsch", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Titel*") },
                    singleLine = true,
                    isError = titleError
                )
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(
                    value = album,
                    onValueChange = { album = it },
                    label = { Text("Album") },
                    singleLine = true,
                )
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(
                    value = artist,
                    onValueChange = { artist = it },
                    label = { Text("Künstler") },
                    singleLine = true,
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 10.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(onClick = onDismissRequests) {
                    Text("Abbrechen")
                }
                Button(onClick = {
                    if (title.isBlank()) {
                        titleError = true
                    } else {
                        titleError = false
                        scope.launch {
                            if (SongRequestClient.request(title, album, artist)) {
                                onDismissRequests.invoke()
                            } else {
                                errorToast.show()
                            }
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