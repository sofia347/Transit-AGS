package com.example.transit_ags.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CuentaScreen(navController: NavController){
    Column{
        Text(text = "ESTE VA HACER LA VENTANA DE CUENTA")
        Button(onClick = {navController.navigate("micuenta")}){ Text(text = "MI CUENTA") }
        Button(onClick = {navController.navigate("saldo")}){ Text(text = "MI SALDO") }
        Button(onClick = {navController.navigate("recargarahora")}){ Text(text = "RECARGAR AHORA") }
        Button(onClick = {navController.navigate("recargamap")}){ Text(text = "PUNTOS DE RECARGA") }

    }

}