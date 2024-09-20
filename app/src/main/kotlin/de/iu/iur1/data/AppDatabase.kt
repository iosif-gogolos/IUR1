package de.iu.iur1.data

import androidx.room.Database
import androidx.room.RoomDatabase

import de.iu.iur1.data.Song

@Database(entities = [Song::class, Playlist::class, PlaylistSongCrossRef::class], version = 1)

abstract class AppDatabase : RoomDatabase (){
    abstract fun songDao(): SongDaoAbstract
    abstract fun playlistDao(): PlaylistDao
}