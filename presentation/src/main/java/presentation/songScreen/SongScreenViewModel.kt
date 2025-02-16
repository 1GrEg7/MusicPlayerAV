package presentation.songScreen

import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.recycleTrackList.Track
import domain.tracksInfo.GetChartTracksUseCase
import domain.tracksInfo.GetTrackByIdUseCase
import domain.tracksInfo.TracksRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SongScreenViewModel(tracksRepoImpl:TracksRepo): ViewModel() {

    private val _trackInfo =  MutableStateFlow<Track>(Track())

    val trackInfo =  _trackInfo.asStateFlow() //mutableStateListOf<Track>()'



    val trackRepo: TracksRepo = tracksRepoImpl


    fun getTrackInfoById(trackId:Long){
        Log.d("00000", trackId.toString())
        val getTrackById =  GetTrackByIdUseCase(trackRepo)
        viewModelScope.launch {
            val track = getTrackById.invoke(trackId)
            Log.d("00000", track.toString())
            _trackInfo.update { track }
        }
    }

    fun trackUpdate(songName:String,singerName:String,albumName:String,preview:String){
        val newTrack = Track(
            title = songName,
            author = singerName,
            albumName= albumName,
            preview = preview
        )
        _trackInfo.update { newTrack }
    }

    val currentPreview = mutableStateOf("")


    val endTimeTrack = mutableStateOf("00:00")

    fun getMp3Duration(url: String) { // нашли время кусочка трека
        viewModelScope.launch(Dispatchers.IO){
            val mmr = MediaMetadataRetriever()
            try {
                mmr.setDataSource(url, HashMap())
                // Получаем длительность в миллисекундах
                val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                val minutes = durationStr?.toLong()?.div(1000)?.div(60)
                val seconds = durationStr?.toLong()?.div(1000)?.mod(60)
                if (minutes != null) {
                    var textMinutes = ""
                    var textSeconds = ""
                    if (minutes<10){
                        textMinutes = "0${minutes}"
                    }else{
                        textMinutes = minutes.toString()
                    }
                    if (seconds != null) {
                        if (seconds<10){
                            textSeconds = "0${minutes}"
                        }else{
                            textSeconds = seconds.toString()
                        }
                    }else{
                        endTimeTrack.value =  "00:00"
                    }

                    endTimeTrack.value =  "${textMinutes}:${textSeconds}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mmr.release()
            }

        }

    }





}