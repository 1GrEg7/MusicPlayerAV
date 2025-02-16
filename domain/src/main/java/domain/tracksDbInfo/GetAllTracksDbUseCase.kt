package domain.tracksDbInfo

import core.recycleTrackList.Track

class GetAllTracksDbUseCase(private val trackDbRepo: TrackDbRepo) {

    suspend fun invoke(): List<Track> {
        return trackDbRepo.getAllTracks()
    }

}