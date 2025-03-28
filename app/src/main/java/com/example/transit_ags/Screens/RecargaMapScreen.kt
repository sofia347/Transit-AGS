package com.example.transit_ags.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun RecargaMapScreen(navController: NavController){
    Column{
        Text(text = "ESTE VA HACER LA VENTANA CON EL MAPA CON LOS PUNTOS DE RECARGA")

        Button(onClick = {navController.navigate("cuenta")}){ Text(text = "REGRESAR") }

    }

}