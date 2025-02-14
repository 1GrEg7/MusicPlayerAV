package domain.tracksInfo

import core.recycleTrackList.Track

class GetChartTracksUseCase(private val tracksRepo: TracksRepo) {

    suspend fun invoke(): List<Track> {
        return tracksRepo.fetchChartTracks()
    }
}