package domain.tracksDbInfo

import core.recycleTrackList.Track

class InsertTrackDbUseCase(private val trackDbRepo: TrackDbRepo) {

    suspend fun invoke(track: Track):Long{
        return trackDbRepo.insertTrack(track)
    }

}