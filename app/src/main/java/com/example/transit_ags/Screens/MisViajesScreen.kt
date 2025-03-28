package com.example.transit_ags.Screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.transit_ags.R
import com.example.transit_ags.Utils.generateQRCode // Asegúrate de crear esta función para generar el QR

@Composable
fun MisViajesScreen(navController: NavController) {
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showQR by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icono de regresar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back), // Icono de regreso
                contentDescription = "Regresar",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { navController.popBackStack() }
            )
        }

        // Avatar y Nombre del Usuario
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar), // Avatar del usuario
                contentDescription = "Avatar del usuario",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Sharon José\nMisorlava De Lira Campos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Viajes disponibles
        Text(
            text = "Tienes 5 viajes disponibles",
            fontSize = 20.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Pagar Viaje
        Button(
            onClick = {
                // Generar QR aleatorio al hacer clic
                qrBitmap = generateQRCode("Pago-${(1000..9999).random()}") // QR aleatorio
                showQR = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Pagar viaje",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Instrucciones
        Text(
            text = "Escanea el código QR en el lector del camión",
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mostrar QR si se presiona el botón
        if (showQR && qrBitmap != null) {
            QRCodeCard(qrBitmap!!)
        }
    }
}

// Composable para mostrar el QR dinámico
@Composable
fun QRCodeCard(qrBitmap: Bitmap) {
    Card(
        modifier = Modifier
            .size(180.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Image(
            bitmap = qrBitmap.asImageBitmap(),
            contentDescription = "Código QR generado",
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentScale = ContentScale.Fit
        )
    }
}
