package com.example.transit_ags.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun SaldoScreen(navController: NavController){
    Column{
        val context = LocalContext.current

        Text(text = "ESTE VA HACER LA VENTANA DE CONSULTAR EL SALDO DE YO VOY")

        Button(onClick = {navController.navigate("recargamap")}){ Text(text = "PUNTOS DE RECARGA") }
        Button(onClick = {navController.navigate("recargarahora")}){ Text(text = "RECAGAR AHORA EN LINEA") }

        Button(onClick = {navController.navigate("cuenta")}){ Text(text = "REGRESAR") }

    }

}