package data.trackData

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("cover")
    val cover: String
)