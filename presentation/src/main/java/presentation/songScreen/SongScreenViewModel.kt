package presentation.songScreen

import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import core.recycleTrackList.Track
import domain.tracksDbInfo.InsertTrackDbUseCase
import domain.tracksInfo.GetTrackByIdUseCase
import domain.tracksInfo.TracksRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.apiTracks.ApiTracksViewModel

class SongScreenViewModel(
    private val apiViewModel: ApiTracksViewModel,
    private val getTrackByIdUseCase: GetTrackByIdUseCase
): ViewModel() {

    private val _trackInfo =  MutableStateFlow<Track>(Track())

    val trackInfo =  _trackInfo.asStateFlow()
    fun updateTrackInfo(newTrack: Track){
        _trackInfo.update { newTrack }
    }

    //val apiViewModel = apiTracksViewModel

    //val trackRepo: TracksRepo = tracksRepoImpl








    fun getTrackInfoById(trackId:Long){
        viewModelScope.launch {
            val track = getTrackByIdUseCase.invoke(trackId)
            _trackInfo.update { track }
        }
    }

    val trackIndex = mutableStateOf(0)

    fun getNextTrack():Track{
        if (trackIndex.value+1<apiViewModel.filteredTracks.size){
            val nextTrack = apiViewModel.filteredTracks[trackIndex.value+1]
            return nextTrack
        }else{
            val currentTrack =  apiViewModel.filteredTracks[trackIndex.value]
            return currentTrack
        }
    }

    fun getPreviousTrack(): Track{
        if (trackIndex.value-1>=0){
            val prevTrack = apiViewModel.filteredTracks[trackIndex.value-1]
            return prevTrack
        }else{
            val currentTrack =  apiViewModel.filteredTracks[trackIndex.value]
            return currentTrack
        }
    }


    val currentPreview = mutableStateOf("")

    val endTimeTrack = mutableStateOf("00:00")


    class SongScreenViewModelFactory(
        private val apiViewModel: ApiTracksViewModel,
        private val getTrackByIdUseCase: GetTrackByIdUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SongScreenViewModel::class.java)) {
                return SongScreenViewModel(apiViewModel, getTrackByIdUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}