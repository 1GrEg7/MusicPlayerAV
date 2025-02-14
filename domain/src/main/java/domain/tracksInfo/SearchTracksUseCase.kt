package domain.tracksInfo

import core.recycleTrackList.Track

class SearchTracksUseCase(private val tracksRepo: TracksRepo) {

    suspend fun invoke(textSearch:String): List<Track> {
        return tracksRepo.searchTracks(textSearch)
    }

}