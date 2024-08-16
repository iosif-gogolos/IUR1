package de.iu.iur1.playlist

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap

object PlaylistClient {
    private const val PLAYLIST_BASE_PATH = "http://10.0.2.2:30123/iur1/playlist/"
    private const val TODAY_CACHE_INTERVAL = 10

    private val client = HttpClient(Android)

    private val cache = ConcurrentHashMap<LocalDate, PlaylistDataState>()

    private var today: PlaylistDataState = PlaylistDataState.Error
    private var lastFetchOfToday: Long = Instant.now().toEpochMilli()

    //TODO switch to demonstration mode if backend health check is not successful

    suspend fun cachedOrFetch(date: LocalDate): PlaylistDataState {
        val dateNow = LocalDate.now()
        if (date == dateNow) {
            if (lastFetchWasOverNSecondsAgo(TODAY_CACHE_INTERVAL) || today is PlaylistDataState.Error) {
                today = fetch(dateNow)
                lastFetchOfToday = Instant.now().toEpochMilli()
            }
            return today
        }

        val cachedValue = cache[date]
        if (cachedValue != null) return cachedValue

        val data = fetch(date)
        if (data is PlaylistDataState.Value || data is PlaylistDataState.Empty) cache[date] = data

        return data
    }

    suspend fun fetch(date: LocalDate): PlaylistDataState {
        try {
            val url = "$PLAYLIST_BASE_PATH/${date.format(DateTimeFormatter.ISO_LOCAL_DATE)}"
            val response = client.get(url)
            return when (response.status) {
                HttpStatusCode.OK -> {
                    val data = PlaylistData.fromString(response.bodyAsText())
                        ?: return PlaylistDataState.Error
                    if (data.entries.isEmpty()) {
                        PlaylistDataState.Empty
                    } else {
                        PlaylistDataState.Value(data)
                    }
                }

                HttpStatusCode.NotFound -> PlaylistDataState.Empty
                else -> PlaylistDataState.Error
            }
        } catch (ioEx: IOException) {
            Log.i("PlaylistClient", ioEx.message ?: "IOException")
            return PlaylistDataState.Error
        }
    }

    fun clear() {
        cache.clear()
    }

    private fun lastFetchWasOverNSecondsAgo(n: Int) =
        (Instant.now().toEpochMilli() - lastFetchOfToday) > (n * 1000)

}
