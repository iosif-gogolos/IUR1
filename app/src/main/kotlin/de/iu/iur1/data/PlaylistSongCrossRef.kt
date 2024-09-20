package de.iu.iur1.data

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "songId"])
data class PlaylistSongCrossRef(
    val playlistId: Int,
    val songId: Int
)
