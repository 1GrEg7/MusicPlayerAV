package domain.tracksInfo

import core.recycleTrackList.Track

interface TracksRepo {

    suspend fun fetchTrack(id:Int): Track

    suspend fun fetchChartTracks(): List<Track>

    suspend fun searchTracks(textSearch:String): List<Track>

}