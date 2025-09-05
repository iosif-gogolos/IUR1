package de.iu.iur1.nowplaying.data

// Stub

data class NowPlaying(val title: String, val artist: String)

object NowPlayingClient {
    suspend fun fetch(): Result<NowPlaying> = runCatching {
        // TODO HTTP; Demo-Daten:
        NowPlaying("IUR1 Morning Brief", "Lisa Musterfrau")
    }
}
