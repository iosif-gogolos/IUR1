package de.iu.iur1.feature.nowplaying.data

import kotlinx.coroutines.delay
import kotlin.random.Random

data class NowPlaying(val title: String, val artist: String)

object NowPlayingClient {
    private const val USE_STUB_MODE = true

    private val stubSongs = listOf(
        NowPlaying("Anti-Hero", "Taylor Swift"),
        NowPlaying("Flowers", "Miley Cyrus"),
        NowPlaying("Unholy", "Sam Smith ft. Kim Petras"),
        NowPlaying("As It Was", "Harry Styles"),
        NowPlaying("Heat Waves", "Glass Animals"),
        NowPlaying("Shivers", "Ed Sheeran"),
        NowPlaying("Stay", "The Kid LAROI & Justin Bieber"),
        NowPlaying("Good 4 U", "Olivia Rodrigo"),
        NowPlaying("Levitating", "Dua Lipa"),
        NowPlaying("Blinding Lights", "The Weeknd")
    )

    suspend fun fetch(): Result<NowPlaying> = runCatching {
        // Simuliere Netzwerk-Delay
        delay(500)

        if (USE_STUB_MODE) {
            // Wähle einen zufälligen Stub-Song
            val randomSong = stubSongs[Random.nextInt(stubSongs.size)]

            // Für jetzt: prominente Demo-Daten
            randomSong
        } else {
            // TODO: Echte HTTP-Anfrage implementieren
            NowPlaying("", "")
        }
    }
}