package com.example.descargadortk

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiTikTok {
    @GET("api/")
    suspend fun obtenerVideoLimpio(
        @Query("url") enlace: String
    ): Response<RespuestaApi>
}