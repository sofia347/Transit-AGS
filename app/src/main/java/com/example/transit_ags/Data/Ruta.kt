package com.example.transit_ags.Data

data class Ruta(
    val idRuta: Int,
    val nombre: String,
    val sentido: String,
    val noPunto: Int,
    val latitud: Double,
    val longitud: Double
)