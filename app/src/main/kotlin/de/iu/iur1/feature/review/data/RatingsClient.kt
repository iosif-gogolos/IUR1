package de.iu.iur1.feature.review.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object RatingsClient {
    private const val STUB_MODE = true
    private const val BASE_URL = "http://10.0.2.2:30123/iur1"

    private val client = HttpClient(Android)

    suspend fun rateModerator(moderatorId: String, value: Int) = if (STUB_MODE) {
        simulateRate(moderatorId, value)
    } else {
        rate(moderatorId, value)
    }

    private fun simulateRate(moderatorId: String, value: Int) {
        Log.i("RatingClient", "Rating $moderatorId -> $value")
    }

    private suspend fun rate(moderatorId: String, value: Int) = runCatching {
        client.post("${BASE_URL}/review/moderator/${moderatorId}/${value}")
    }
}