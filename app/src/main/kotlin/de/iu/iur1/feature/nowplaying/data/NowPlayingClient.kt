package de.iu.iur1.nowplaying.data

import kotlinx.coroutines.delay
import kotlin.random.Random

data class NowPlaying(val title: String, val artist: String)

object NowPlayingClient {

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

        // Wähle einen zufälligen Stub-Song
        val randomSong = stubSongs[Random.nextInt(stubSongs.size)]

        // TODO: Echte HTTP-Anfrage implementieren
        // Für jetzt: prominente Demo-Daten
        randomSong
    }

    // Zusätzliche Methode für spezifische Stub-Songs
    fun getCurrentStubSong(): NowPlaying = stubSongs.first()

    // Methode um zwischen verschiedenen "Sendern" zu wechseln
    fun getStubSongForStation(stationId: String): NowPlaying = when (stationId) {
        "morning" -> NowPlaying("Good Morning Sunshine", "DJ Lisa")
        "evening" -> NowPlaying("Evening Vibes", "Max Musik")
        "rock" -> NowPlaying("Bohemian Rhapsody", "Queen")
        else -> stubSongs[Random.nextInt(stubSongs.size)]
    }
}