package com.example.transit_ags.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.transit_ags.R

@Composable
fun CuentaScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Avatar del usuario",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Sharon José\nMisorlava De Lira Campos",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Card para Mis Viajes
        CardOption(
            iconRes = R.drawable.misviajes,
            text = "Mis Viajes",
            onClick = { navController.navigate("misviajes") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Card para Recargar Viajes
        CardOption(
            iconRes = R.drawable.recargarviajes,
            text = "Recargar viajes",
            onClick = { navController.navigate("saldo") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Card para Puntos de Recarga
        CardOption(
            iconRes = R.drawable.puntosderecarga,
            text = "Puntos de recarga",
            onClick = { navController.navigate("recargamap") }
        )
    }
}

// Composable para una Card con imagen y texto
@Composable
fun CardOption(iconRes: Int, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = text,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
