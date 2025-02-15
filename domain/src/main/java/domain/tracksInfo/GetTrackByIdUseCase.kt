package domain.tracksInfo

import core.recycleTrackList.Track


class GetTrackByIdUseCase(private val tracksRepo: TracksRepo) {

    suspend fun invoke(id:Int): Track {
        return tracksRepo.fetchTrack(id)
    }
}