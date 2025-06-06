package domain.tracksDbInfo

import core.recycleTrackList.Track

class DeleteTrackDbUseCase(private val trackDbRepo: TrackDbRepo) {

    suspend fun invoke(track:Track){
        return trackDbRepo.deleteTrack(track)
    }

}