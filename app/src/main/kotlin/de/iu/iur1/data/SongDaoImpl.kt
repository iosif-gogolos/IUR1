package de.iu.iur1.data

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
class SongDaoImpl : SongDaoAbstract() {
    override fun getAllSongs(): Flow<List<Song>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSong(song: Song): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllSongs(songs: List<Song>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSong(song: Song) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSong(song: Song) {
        TODO("Not yet implemented")
    }
}