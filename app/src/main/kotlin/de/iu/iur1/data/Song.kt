package de.iu.iur1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val artist: String,
    val duration: Int,
    val year: Int
)
