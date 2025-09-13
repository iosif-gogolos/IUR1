package de.iu.iur1.feature.review.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalTime
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

object NotificationsClient {
    private const val STUB_MODE = true
    private const val FETCH_DELAY = 30_000L
    private const val BASE_URL = "http://10.0.2.2:30123/iur1"

    private data class CacheValue(val lastFetch: LocalTime, val value: Int)

    private val client = HttpClient(Android)
    private val cache: ConcurrentHashMap<String, CacheValue> = ConcurrentHashMap()

    fun subscribeModeratorRatings(moderatorId: String) = if(STUB_MODE) {
        simulateRatings(moderatorId)
    } else {
        fetchRating(moderatorId)
    }

    // Demo: Simuliert alle 30s eine neue Durchschnittsbewertung 1..5 für jeden moderator
    private fun simulateRatings(moderatorId: String): Flow<Int> = flow {
        while (true) {
            val rating = fromCacheOrCompute(moderatorId) { Random.nextInt(1, 6) }
            emit(rating)
            delay(FETCH_DELAY)
        }
    }

    private fun fetchRating(moderatorId: String): Flow<Int> = flow {
        while (true) {
            val rating = fromCacheOrCompute(moderatorId) {
                val response = client.get("${BASE_URL}/rating/moderator/${moderatorId}")
                try {
                    response.bodyAsText().toInt()
                } catch (_: NumberFormatException) {
                    Log.e("NotificationsClient", "Could not convert rating response to int")
                    -1
                }
            }
            emit(rating)
            delay(FETCH_DELAY)
        }
    }

    private suspend fun fromCacheOrCompute(moderatorId: String, compute: suspend () -> Int): Int {
        val now = LocalTime.now()
        val cachedValue: CacheValue? = cache.get(moderatorId)
        if (cachedValue == null || now.isAfter(cachedValue.lastFetch.plusSeconds(30))) {
            val value = compute.invoke()
            cache.put(moderatorId, CacheValue(now, value))
            return value
        } else {
            return cachedValue.value
        }
    }
}