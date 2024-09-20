package de.iu.iur1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = true) val
    id: Int = 0,
    val name: String

) {
    fun insertAllPlaylists(listOf: List<Playlist>) {

    }

    fun createPlaylistWithSongs(playlist1: Playlist, subList: List<Song>) {

    }
}
