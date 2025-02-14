package data.trackData

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)