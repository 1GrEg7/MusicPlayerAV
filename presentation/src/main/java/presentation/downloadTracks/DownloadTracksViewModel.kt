package presentation.downloadTracks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import core.recycleTrackList.Track
import domain.tracksDbInfo.DeleteTrackDbUseCase
import domain.tracksDbInfo.GetAllTracksDbUseCase
import domain.tracksDbInfo.InsertTrackDbUseCase
import domain.tracksDbInfo.TrackDbRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DownloadTracksViewModel(

    private val getAllTracksDbUseCase: GetAllTracksDbUseCase,
    private val insertTrackDbUseCase:InsertTrackDbUseCase,
    private val deleteTrackDbUseCase:DeleteTrackDbUseCase,

): ViewModel() {

//    var getAllTracksUseCase: GetAllTracksDbUseCase? = null
//    var insertTrackUseCase: InsertTrackDbUseCase? = null
//    var deleteTrackUseCase: DeleteTrackDbUseCase? = null
//    init {
//        getAllTracksUseCase = getAllTracksDbUseCase
//        insertTrackUseCase = insertTrackDbUseCase
//        deleteTrackUseCase = deleteTrackDbUseCase
//    }

    private val _allTracks = MutableStateFlow<List<Track>>(emptyList())

    val searchField = mutableStateOf("")

    //val trackRepo = trackDbRepo



    val filteredTracks: SnapshotStateList<Track> = _allTracks.value.toMutableStateList()

    fun filterItems(query: String) {
        filteredTracks.clear()
        viewModelScope.launch {
            _allTracks.value.forEach {
                if (it.title.contains(query, true) || it.author.contains(query, true)) filteredTracks.add(it)
            }

        }
    }

    fun addTrack(track: Track) {
        viewModelScope.launch {
           // trackRepo.insertTrack(track)
            insertTrackDbUseCase.invoke(track)
            fetchAllTracks() // Обновляем список после добавления
        }
    }
    fun removeTrack(track: Track) {
        viewModelScope.launch {
            //trackRepo.deleteTrack(track)
            deleteTrackDbUseCase?.invoke(track)
            fetchAllTracks() // Обновляем список после удаления
        }
    }
     fun fetchAllTracks() {
        viewModelScope.launch {
            _allTracks.value = getAllTracksDbUseCase!!.invoke() //trackRepo.getAllTracks()
            filterItems("")
        }
    }

    class DownloadTracksViewModelFactory(
        private val getAllTracksDbUseCase: GetAllTracksDbUseCase,
        private val insertTrackDbUseCase: InsertTrackDbUseCase,
        private val deleteTrackDbUseCase: DeleteTrackDbUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DownloadTracksViewModel::class.java)) {
                return DownloadTracksViewModel(
                    getAllTracksDbUseCase,
                    insertTrackDbUseCase,
                    deleteTrackDbUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }



}