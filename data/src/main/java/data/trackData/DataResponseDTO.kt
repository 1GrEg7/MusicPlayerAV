package data.trackData

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class DataChartResponseDTO(
    @SerializedName("tracks")
    val tracks: TracksDTO
)