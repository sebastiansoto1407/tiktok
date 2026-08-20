package com.example.descargadortk

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_historial")
data class VideoGuardado(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val urlLocal: String,
    val fecha: Long = System.currentTimeMillis()
)