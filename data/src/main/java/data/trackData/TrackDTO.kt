package data.trackData

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TrackDTO (
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("link")
    val link: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("preview")
    val preview: String,
    @SerialName("artist")
    val artist: ArtistDTO,
    @SerialName("album")
    val album: AlbumDTO

)