package de.iu.iur1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM Playlist")
    suspend fun getAllPlaylists(): List<Playlist>

    @Insert
    suspend fun insertPlaylist(playlist: Playlist)

    @Insert
    suspend fun insertAllPlaylists(playlists: List<Playlist>)
}
