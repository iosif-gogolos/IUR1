package de.iu.iur1.review.data

// Stub "sofortige" Info

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object NotificationsClient {
    // Demo: Simuliert alle 5s eine neue Durchschnittsbewertung 1..5
    fun subscribeModeratorRatings(moderatorId: String): Flow<Int> = flow {
        while (true) {
            delay(5_000)
            emit(Random.nextInt(1, 6))
        }
    }
}