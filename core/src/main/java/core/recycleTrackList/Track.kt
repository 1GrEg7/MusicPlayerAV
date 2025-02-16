package core.recycleTrackList

data class Track(
    val id: Long = -1,
    val cover: String = "", // URL обложки
    val title: String = "",
    val author: String = "",
    val albumName: String = "",
    val preview: String = ""
)

