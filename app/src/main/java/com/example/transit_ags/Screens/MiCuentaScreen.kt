package com.example.transit_ags.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun MiCuentaScreen(navController: NavController){
    Column{
        val context = LocalContext.current

        Text(text = "ESTE VA HACER LA VENTANA MI CUENTA")

        Button(onClick = {Toast.makeText(context, "MODIFICAR INFO...", Toast.LENGTH_SHORT).show()}){ Text(text = "MODIFICAR INFORMACION") }
        Button(onClick = {navController.navigate("registro")}){ Text(text = "CERRAR SESION") }

        Button(onClick = {navController.navigate("cuenta")}){ Text(text = "REGRESAR") }

    }

}