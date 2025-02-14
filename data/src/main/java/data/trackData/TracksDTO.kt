package data.trackData

import com.google.gson.annotations.SerializedName




data class TracksDTO(
    @SerializedName("data")
    val data: List<TrackDTO>,
    @SerializedName("prev")
    val prev: String?,
    @SerializedName("next")
    val next: String?
)