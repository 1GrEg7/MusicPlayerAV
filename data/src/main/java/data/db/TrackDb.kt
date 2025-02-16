package data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    var cover: String = "", // URL обложки
    var title: String = "",
    var author: String = "",
    var albumName: String = "",
    var preview: String = ""
)
