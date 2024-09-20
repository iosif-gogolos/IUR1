package de.iu.iur1.playlist

import androidx.compose.runtime.Composable
import de.iu.iur1.data.AppDatabase
import de.iu.iur1.data.Song
import de.iu.iur1.data.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun getAllPlaylists(): List<Playlist> {
        return withContext(Dispatchers.IO) {
            database.playlistDao().getAllPlaylists()
        }
    }

    suspend fun insertSamplePlaylists(playlists: List<Playlist>) {
        withContext(Dispatchers.IO) {
            database.playlistDao().insertAllPlaylists(playlists)
        }
    }

    fun insertSampleData() {

        // Study Focus Playlist
        val song1 = Song(id = 1, title ="Weightless", artist = "Marconi Union", duration = 480, year = 2011)
        val song2 = Song(id = 2, title  = "Clair de Lune", artist = "Claude Debussy", duration = 300, year = 1905)
        val song3 = Song(id = 3, title = "Nuvole Bianche", artist = "Ludovico Einaudi", duration = 360, year= 2004)
        val song4 = Song(id = 4, title = "Watermark", artist = "Enya", duration = 150, year= 1988)
        val song5 = Song(id = 5, title = "Kiss the Rain", artist = "Yiruma", duration = 240, year= 2003)
        val song6 = Song(id = 6, title = "Airstream", artist = "Tycho", duration = 300, year= 2014)
        val song7 = Song(id = 7, title = "An Ending (Ascent)", artist = "Brian Eno", duration = 240, year= 1983)
        val song8 = Song(id = 8, title = "Teardrop", artist = "Massive Attack", duration = 360, year= 1998)

        // Classical Music Playlist songs
        val song9 = Song(id = 9, title = "Für Elise", artist = "Ludwig van Beethoven", duration = 180, year= 1810)
        val song10 = Song(id = 10, title = "Die Moldau", artist = "Bedřich Smetana", duration = 720, year= 1874)
        val song11 = Song(id = 11, title = "Swan Lake Suite", artist = "Pyotr Ilyich Tchaikovsky", duration = 1200, year= 1876)
        val song12 = Song(id = 12, title = "The Four Seasons", artist = "Antonio Vivaldi", duration = 1200, year= 1725)
        val song13 = Song(id = 13, title = "Canon in D Major", artist = "Johann Pachelbel", duration = 300, year= 1680)
        val song14 = Song(id = 14, title = "Ave Maria", artist = "Franz Schubert", duration = 240, year= 1825)
        val song15 = Song(id = 15, title = "Moonlight Sonata", artist = "Ludwig van Beethoven", duration = 900, year= 1801)

        // Coffee-House Playlist songs
        val song16 = Song(id = 16, title = "Skinny Love", artist = "Bon Iver", duration = 240, year= 2007)
        val song17 = Song(id = 17, title = "The Scientist", artist = "Coldplay", duration = 300, year= 2002)
        val song18 = Song(id = 18, title = "Make You Feel My Love", artist = "Adele", duration = 240, year= 2008)
        val song19 = Song(id = 19, title = "Fast Car", artist = "Tracy Chapman", duration = 240, year= 1988)
        val song20 = Song(id = 20, title = "Heartbeats", artist = "José González", duration = 240, year= 2003)
        val song21 = Song(id = 21, title = "Such Great Heights", artist = "The Postal Service", duration = 240, year= 2003)
        val song22 = Song(id = 22, title = "Little Talks", artist = "Of Monsters and Men", duration = 270, year= 2011)
        val song23 = Song(id = 23, title = "Ho Hey", artist = "The Lumineers", duration = 165, year= 2012)

        val songs = listOf(song1, song2, song3, song4, song5, song6, song7, song8, song9, song10, song11, song12, song13, song14, song15)

        val playlists = listOf(Playlist(id = 1, name = "IU Study Focus"), Playlist(id = 2, name = "Classical Music"), Playlist(id = 3, name = "Coffee-House"))

        CoroutineScope(Dispatchers.IO).launch {
            database.songDao().insertAllSongs(songs)
            database.playlistDao().insertAllPlaylists(playlists)
            //database.playlistDao().createPlaylistWithSongs(playlists[0], songs.subList(0, 8)) // Example for the first playlist
        }

        suspend fun getAllPlaylists(): List<Playlist> {
            return database.playlistDao().getAllPlaylists()
        }
    }
}