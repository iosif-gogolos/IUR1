package de.iu.iur1.playlist

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@Serializable
data class PlaylistEntry(val title: String, val time: String)

@Serializable
data class PlaylistData(val rating: Int, val entries: List<PlaylistEntry>) {
    companion object {
        fun fromString(value: String): PlaylistData? = try {
            Json.decodeFromString<PlaylistData>(value)
        } catch (sEx: SerializationException) {
            null
        } catch (iaEx: IllegalArgumentException) {
            null
        }
    }
}

sealed interface PlaylistDataState {
    data class Value(val playlistData: PlaylistData) : PlaylistDataState
    data object Empty : PlaylistDataState
    data object Error : PlaylistDataState
}
