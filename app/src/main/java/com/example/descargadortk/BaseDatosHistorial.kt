package com.example.descargadortk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoGuardado::class], version = 1, exportSchema = false)
abstract class BaseDatosHistorial : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {
        @Volatile
        private var INSTANCIA: BaseDatosHistorial? = null

        fun obtenerBaseDatos(contexto: Context): BaseDatosHistorial {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDatosHistorial::class.java,
                    "base_datos_videos"
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}