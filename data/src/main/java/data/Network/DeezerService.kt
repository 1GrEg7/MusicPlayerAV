package data.Network


import data.trackData.DataChartResponseDTO
import data.trackData.TrackDTO
import data.trackData.TracksDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerService {
    @GET("chart")
    suspend fun getChart(): DataChartResponseDTO

    @GET("search")
    suspend fun search(@Query("q") query: String): TracksDTO

    @GET("track/{id}")
    suspend fun getTrackById(@Path("id") id: Long): TrackDTO
}

