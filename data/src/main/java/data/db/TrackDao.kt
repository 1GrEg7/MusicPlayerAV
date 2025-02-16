package data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackDb: TrackDb): Long

    @Delete
    suspend fun delete(trackDb: TrackDb)

    @Query("SELECT * FROM tracks WHERE id = :id")
    suspend fun getTrackById(id: Long): TrackDb

    @Query("SELECT * FROM tracks")
    suspend fun getAllTracks(): List<TrackDb>
}