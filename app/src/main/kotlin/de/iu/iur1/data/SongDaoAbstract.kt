package de.iu.iur1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

abstract class SongDaoAbstract {

    @Query("SELECT * FROM Song")
    abstract fun getAllSongs(): Flow<List<Song>>

    @Insert
    abstract suspend fun insertSong(song: Song): Long

    @Insert
    abstract suspend fun insertAllSongs(songs: List<Song>)

    @Update
    abstract suspend fun updateSong(song: Song)

    @Delete
    abstract suspend fun deleteSong(song: Song)
}