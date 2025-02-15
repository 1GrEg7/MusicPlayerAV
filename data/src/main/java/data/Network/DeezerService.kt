package data.Network

import android.telecom.Call
import data.trackData.DataChartResponseDTO
import data.trackData.TracksDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerService {
    @GET("chart")
    suspend fun getChart(): DataChartResponseDTO

    @GET("search")
    suspend fun search(@Query("q") query: String): TracksDTO
}

