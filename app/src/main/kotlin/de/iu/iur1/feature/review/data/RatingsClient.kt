package de.iu.iur1.review.data

// Stub


import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object RatingsClient {
    private val client = HttpClient(Android)

    data class ModeratorRating(val moderatorId: String, val value: Int)

    suspend fun rateModerator(moderatorId: String, value: Int) = runCatching {
        // TODO: echte URL; vorerst Demo/No-Op:
        // client.post("http://10.0.2.2:30123/iur1/review/moderator") { setBody(ModeratorRating(moderatorId, value)) }
    }
}