package domain.tracksDbInfo

import core.recycleTrackList.Track

interface TrackDbRepo {

    suspend fun insertTrack(track: Track): Long

    suspend fun deleteTrack(track: Track)

    suspend fun getTrackById(id: Long): Track

    suspend fun getAllTracks(): List<Track>
}