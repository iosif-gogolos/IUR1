package de.iu.iur1.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.iu.iur1.data.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    private val _playlistState = MutableStateFlow<PlaylistState>(PlaylistState.LOADING)
    val playlistState: StateFlow<PlaylistState> = _playlistState

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists

    fun loadPlaylists() {
        viewModelScope.launch {
            try {
                // Fetch playlists from the repository
                _playlists.value = playlistRepository.getAllPlaylists()

                // Set state based on the result
                _playlistState.value = if (_playlists.value.isEmpty()) {
                    PlaylistState.EMPTY
                } else {
                    PlaylistState.LOADED
                }
            } catch (e: Exception) {
                // Handle error state
                _playlistState.value = PlaylistState.ERROR
            }
        }
    }
}
