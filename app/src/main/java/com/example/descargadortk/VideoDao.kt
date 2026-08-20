package com.example.descargadortk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideoDao {
    @Insert
    suspend fun guardarVideo(video: VideoGuardado)

    @Query("SELECT * FROM tabla_historial ORDER BY fecha DESC")
    suspend fun obtenerHistorial(): List<VideoGuardado>
}