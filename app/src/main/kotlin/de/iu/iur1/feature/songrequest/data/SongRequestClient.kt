package de.iu.iur1.feature.songrequest.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

object SongRequestClient {
    private const val STUB_MODE = true
    private const val BASE_URL = "http://10.0.2.2:30123/iur1"

    private data class SongRequestBody(val title: String, val album: String?, val artist: String?)

    private val client = HttpClient(Android)

    suspend fun request(title: String, album: String, artist: String): Boolean = if (STUB_MODE) {
        simulate(title, album, artist)
    } else {
        requestSong(title, album, artist)
    }

    private fun simulate(title: String, album: String, artist: String): Boolean {
        Log.i("SongRequestClient", "Request: $title - $album - $artist")
        return true
    }

    private suspend fun requestSong(title: String, album: String, artist: String): Boolean {
        val response = client.post("${BASE_URL}/request") {
            setBody(SongRequestBody(title, album, artist))
        }
        return response.status == HttpStatusCode.OK
    }
}