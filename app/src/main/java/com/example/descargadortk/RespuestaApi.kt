package com.example.descargadortk

data class RespuestaApi(
    val code: Int,
    val msg: String,
    val data: DatosVideo?
)

data class DatosVideo(
    val play: String,
    val title: String
)