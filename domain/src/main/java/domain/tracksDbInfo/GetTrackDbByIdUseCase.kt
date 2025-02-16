package domain.tracksDbInfo

import core.recycleTrackList.Track

class GetTrackDbByIdUseCase(private val trackDbRepo: TrackDbRepo) {

    suspend fun invoke(id: Long):Track{
        return trackDbRepo.getTrackById(id)
    }

}