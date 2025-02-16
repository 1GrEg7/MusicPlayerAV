package data.db

import android.util.Log
import domain.tracksDbInfo.TrackDbRepo
import core.recycleTrackList.Track
import data.mappers.toDomain
import data.mappers.toTrackDb

class TrackDbRepoImpl(private val trackDao: TrackDao):TrackDbRepo {

    override suspend fun insertTrack(track: Track): Long {
        return trackDao.insert(track.toTrackDb())
    }

    override suspend fun deleteTrack(track: Track) {
        trackDao.delete(track.toTrackDb())
    }


    override suspend fun getTrackById(id: Long): Track {
        return trackDao.getTrackById(id).toDomain()
    }

    override suspend fun getAllTracks(): List<Track> {
        Log.d("wwwwww",trackDao.getAllTracks().map { it.toDomain() }.toString())
        return trackDao.getAllTracks().map { it.toDomain() }

    }

}