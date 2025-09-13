package de.iu.iur1.feature.review.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.iu.iur1.feature.review.data.NotificationsClient
import de.iu.iur1.feature.review.data.RatingsClient
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class Moderator(val id: String, val name: String, val showName: String, val avgRating: Int)

@Composable
fun ReviewView() {
    val scope = rememberCoroutineScope()
    // Demo-Daten – später aus Backend laden
    var mods by remember {
        mutableStateOf(
            listOf(
                Moderator("mod-1", "Lisa Musterfrau", "IUR1 Morning Brief", 4),
                Moderator("mod-2", "Max Musik-Mustermann", "Late Summer Vibes", 3),
                Moderator("mod-3", "Pat Radio", "DayNight", 5)
            )
        )
    }

    // „Sofortige“ Benachrichtigungen (Stub: Polling/Randomwerte)
    LaunchedEffect(Unit) {
        mods.forEachIndexed { idx, m ->
            launch {
                NotificationsClient.subscribeModeratorRatings(m.id).collectLatest { newAvg ->
                    mods = mods.toMutableList().also { list ->
                        list[idx] = list[idx].copy(avgRating = newAvg)
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(mods) { mod ->
            Card {
                Column(Modifier.fillMaxWidth().padding(12.dp)) {
                    Text(text = mod.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = mod.showName, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(text = "Ø-Bewertung: ${mod.avgRating}/5")
                    Spacer(Modifier.height(8.dp))
                    var myRating by remember { mutableIntStateOf(0) }
                    StarRating(value = myRating) { new ->
                        myRating = new
                        scope.launch { RatingsClient.rateModerator(mod.id, new) }
                    }
                }
            }
        }
    }
}

@Composable
private fun StarRating(
    value: Int,
    max: Int = 5,
    onChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        (1..max).forEach { i ->
            TextButton(onClick = { onChange(i) }) {
                Text(if (i <= value) "★" else "☆")
            }
        }
    }
}
