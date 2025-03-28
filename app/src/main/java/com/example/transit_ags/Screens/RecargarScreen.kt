package com.example.transit_ags.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun RecargarScreen(navController: NavController){
    Column{
        Text(text = "ESTE VA HACER LA VENTANA DE RECARGAR AHORA")

        Button(onClick = {navController.navigate("cuenta")}){ Text(text = "REGRESAR") }

    }

}