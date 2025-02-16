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

    override suspend fun fetchTrack(id: Long): Track {
        return withContext(Dispatchers.IO){
            try {
                val trackResponse = service.getTrackById(id)
                // Преобразуем список TrackDTO в Track из Domain
                val track = trackResponse.toDomain()

                track

            } catch (e: Exception) {
                Track()
            }
        }
    }

    override suspend fun fetchChartTracks(): List<Track> {
        return withContext(Dispatchers.IO){
            try {
                val chartResponse = service.getChart()
                // Преобразуем список TrackDTO в Track из Domain
                val tracks = chartResponse.tracks.data.map { it.toDomain() }

                tracks
            } catch (e: Exception) {
                listOf() // Возвращаем пустой список в случае ошибки
            }
        }
    }

    override suspend fun searchTracks(textSearch: String): List<Track> {
        return withContext(Dispatchers.IO){
            try {
                val searchResponse = service.search(textSearch)
                // Преобразуем список TrackDTO в Track из Domain
                val tracks = searchResponse.data.map { it.toDomain() }


                tracks
            } catch (e: Exception) {

                listOf() // Возвращаем пустой список в случае ошибки
            }
        }
    }

}

