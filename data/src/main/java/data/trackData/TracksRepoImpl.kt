package data.trackData

import android.util.Log
import core.recycleTrackList.Track
import data.Network.createRetrofitService
import data.mappers.toDomain
import domain.tracksInfo.TracksRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TracksRepoImpl: TracksRepo {

    private val service = createRetrofitService()

    override suspend fun fetchTrack(id: Int): Track {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChartTracks(): List<Track> {

        return withContext(Dispatchers.IO){
            try {
                val chartResponse = service.getChart()
                Log.d("222222chartResponse",chartResponse.toString())
                // Преобразуем список TrackDTO в Track из Domain
                val tracks = chartResponse.tracks.data.map { it.toDomain() }

                Log.d("222222tracks",tracks.toString())
                tracks
            } catch (e: Exception) {
                Log.d("222222",e.stackTrace.contentToString())
                listOf() // Возвращаем пустой список в случае ошибки
            }
        }
    }

    override suspend fun searchTracks(textSearch: String): List<Track> {
        return withContext(Dispatchers.IO){
            try {
                val searchResponse = service.search(textSearch)
                Log.d("222222SearchResponse",searchResponse.toString())
                // Преобразуем список TrackDTO в Track из Domain
                val tracks = searchResponse.data.map { it.toDomain() }

                Log.d("222222Searchtracks",tracks.toString())
                tracks
            } catch (e: Exception) {
                Log.d("222222Search",e.stackTrace.contentToString())
                listOf() // Возвращаем пустой список в случае ошибки
            }
        }
    }

}

