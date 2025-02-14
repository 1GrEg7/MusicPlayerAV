package core.recycleTrackList

data class Track(
    val cover: String, // URL обложки
    val title: String,
    val author: String,
    val albumName: String = ""
)

