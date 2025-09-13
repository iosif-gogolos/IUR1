package de.iu.iur1.feature.review.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object NotificationsClient {
    private const val STUB_MODE = true
    private const val FETCH_DELAY = 30_000L
    private const val BASE_URL = "http://10.0.2.2:30123/iur1"

    private val client = HttpClient(Android)

    fun subscribeModeratorRatings(moderatorId: String) = if(STUB_MODE) {
        simulateRatings()
    } else {
        fetchRating(moderatorId)
    }

    // Demo: Simuliert alle 30s eine neue Durchschnittsbewertung 1..5
    private fun simulateRatings(): Flow<Int> = flow {
        while (true) {
            delay(FETCH_DELAY)
            emit(Random.nextInt(1, 6))
        }
    }

    private fun fetchRating(moderatorId: String): Flow<Int> = flow {
        while (true) {
            delay(FETCH_DELAY)

            val response = client.get("${BASE_URL}/rating/moderator/${moderatorId}")
            try {
                emit(response.bodyAsText().toInt())
            } catch (_: NumberFormatException) {
                Log.e("NotificationsClient", "Could not convert rating response to int")
            }
        }
    }
}