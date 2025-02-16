package presentation.apiTracks

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.recycleTrackList.Track
import domain.tracksInfo.GetChartTracksUseCase
import domain.tracksInfo.SearchTracksUseCase
import domain.tracksInfo.TracksRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ApiTracksViewModel(tracksRepoImpl:TracksRepo): ViewModel() {

    private val _trackList =  MutableStateFlow<List<Track>>(emptyList())

    val filteredTracks: SnapshotStateList<Track> =  _trackList.value.toMutableStateList()

    val chartsTrack: SnapshotStateList<Track> = _trackList.value.toMutableStateList()

    val searchField = mutableStateOf("")



    val trackRepo: TracksRepo = tracksRepoImpl

    fun getChartTracks(){
        val getChartTracks = GetChartTracksUseCase(trackRepo)
        viewModelScope.launch {
            val list = getChartTracks.invoke()
            _trackList.update { list }
            filterItems("")
        }
    }

    fun getSearchedTracks(searchText:String){
        val getSearchedTracks = SearchTracksUseCase(trackRepo)
        viewModelScope.launch {
            val list = getSearchedTracks.invoke(searchText)
            _trackList.update { list }
            filterItems("")
        }
    }

    fun filterItems(query: String) {
        filteredTracks.clear()
        viewModelScope.launch {
            if (_trackList.value.size == 0){
                _trackList.update { chartsTrack.toList() }
            }
            _trackList.value.forEach {
                if (it.title.contains(query, true) || it.author.contains(query, true)) filteredTracks.add(it)
            }
        }
    }

}