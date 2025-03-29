package com.example.transit_ags.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.transit_ags.DataBaseLocal.DatabaseHelper
import com.example.transit_ags.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextDecoration
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val inicioRuta = LatLng(21.882609, -102.293106)

    val camaraPosicion = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(inicioRuta, 16f)
    }

    val paradas = remember { mutableStateOf<List<LatLng>>(emptyList()) }

    val rutaLatLng = remember { mutableStateOf<List<LatLng>>(emptyList()) }
    val showSosDialog = remember { mutableStateOf(false) }
    val showDialogTaxi = remember { mutableStateOf(false) }
    val paradasActive = remember { mutableStateOf(false) }
    val miUbicacionActive = remember { mutableStateOf(false) }
    val miUbicacion = remember { mutableStateOf<LatLng?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = camaraPosicion
        ) {
            paradas.value.forEach { location ->
                Marker(state = MarkerState(position = location), title = "Parada de autobús")
            }

            Polyline(points = rutaLatLng.value, color = Color.Blue, width = 5f)

            miUbicacion.value?.let { location ->
                Marker(
                    state = MarkerState(position = location),
                    title = "Mi Ubicación",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                CamionCombiButtons(dbHelper, rutaLatLng)
            }
            Column(modifier = Modifier.align(Alignment.BottomEnd)) {
                CircularButton(
                    iconResId = R.drawable.ic_location,
                    onClick = {
                        miUbicacionActive.value = !miUbicacionActive.value
                        if (miUbicacionActive.value) {
                            miUbicacion.value = LatLng(21.8825, -102.2827) // Simulación de ubicación
                            Toast.makeText(context, "Ubicación activada", Toast.LENGTH_SHORT).show()
                        } else {
                            miUbicacion.value = null
                            Toast.makeText(context, "Ubicación desactivada", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                CircularButton(
                    iconResId = R.drawable.ic_bus_stop,
                    onClick = {
                        paradasActive.value = !paradasActive.value
                        if (paradasActive.value) {
                            paradas.value = dbHelper.getParadas()
                            Toast.makeText(context, "Se activó la vista de paradas", Toast.LENGTH_SHORT).show()
                        } else {
                            paradas.value = emptyList()
                            Toast.makeText(context, "Se desactivó la vista de paradas", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                CircularButton(
                    iconResId = R.drawable.ic_taxi,
                    onClick = { showDialogTaxi.value = true }
                )

                CircularButton(
                    iconResId = R.drawable.ic_sos,
                    onClick = { showSosDialog.value = true }
                )
            }
        }
    }

    if (showSosDialog.value) {
        AlertDialog(
            onDismissRequest = { showSosDialog.value = false },
            title = { Text("Emergencia 911") },
            text = { Text("Estás a punto de contactar con una operadora del 911. ¿Estás seguro de que es una emergencia?") },
            confirmButton = {
                Button(onClick = {
                    showSosDialog.value = false
                    Toast.makeText(context, "Llamando a emergencias...", Toast.LENGTH_LONG).show()
                }) {
                    Text("Sí, llamar")
                }
            },
            dismissButton = {
                Button(onClick = { showSosDialog.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showDialogTaxi.value) {
        AlertDialog(
            onDismissRequest = { showSosDialog.value = false },
            title = { Text("AVISO TAXIS") },
            text = { Text("Estás a punto de marcar tu ubicacion como zona en busqueda de Taxi. ¿Estás seguro de marcar tu ubicación?") },
            confirmButton = {
                Button(onClick = {
                    showDialogTaxi.value = false
                    navController.navigate("taxistas")
                    Toast.makeText(context, "Mandando señal a los Taxis cercanos...", Toast.LENGTH_LONG).show()
                }) {
                    Text("Sí, enviar señal")
                }
            },
            dismissButton = {
                Button(onClick = { showDialogTaxi.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun CircularButton(iconResId: Int, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .size(56.dp)
            .shadow(8.dp, shape = RoundedCornerShape(75)),
        containerColor = Color.White,
        shape = RoundedCornerShape(50)
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(56.dp)
        )
    }
}

@Composable
fun CamionCombiButtons(dbHelper: DatabaseHelper, rutaLatLng: MutableState<List<LatLng>>) {
    val rutasCamion = listOf("RUTA 20 NORTE", "RUTA 20 SUR", "RUTA 40 NORTE", "RUTA 41 ALAMEDA")
    val rutasCombi = listOf("PALO ALTO - ITA", "MONTOYA - AGS", "LOS CUERVOS - AGS", "ITA - PALO ALTO - CONOS")
    var showCamionDropdown by remember { mutableStateOf(false) }
    var showCombiDropdown by remember { mutableStateOf(false) }
    var rutaSeleccionada by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box {
                Button(
                    onClick = { showCamionDropdown = !showCamionDropdown },
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(24.dp))
                        .size(140.dp, 56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_truck),
                            tint = Color.Unspecified,
                            contentDescription = "Camión",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Camión", color = Color.Black)
                    }
                }
                DropdownMenu(
                    expanded = showCamionDropdown,
                    onDismissRequest = { showCamionDropdown = false },
                    modifier = Modifier.offset(y = 8.dp) // Desplazar hacia abajo
                ) {
                    rutasCamion.forEach { ruta ->
                        DropdownMenuItem(text = { Text(ruta) }, onClick = {
                            rutaSeleccionada = ruta
                            showCamionDropdown = false
                            val puntosRuta = dbHelper.getPuntosDeRuta(ruta)
                            rutaLatLng.value = puntosRuta.map { LatLng(it.latitud, it.longitud) }
                        })
                    }
                }
            }

            Box {
                Button(
                    onClick = { showCombiDropdown = !showCombiDropdown },
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(24.dp))
                        .size(140.dp, 56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minibus),
                            tint = Color.Unspecified,
                            contentDescription = "Combi",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Combi", color = Color.Black)
                    }
                }
                DropdownMenu(
                    expanded = showCombiDropdown,
                    onDismissRequest = { showCombiDropdown = false },
                    modifier = Modifier.offset(y = 6.dp) // Desplazar hacia abajo
                ) {
                    rutasCombi.forEach { ruta ->
                        DropdownMenuItem(text = { Text(ruta) }, onClick = {
                            rutaSeleccionada = ruta
                            showCombiDropdown = false
                            val puntosRuta = dbHelper.getPuntosDeRuta(ruta)
                            rutaLatLng.value = puntosRuta.map { LatLng(it.latitud, it.longitud) }
                        })
                    }
                }
            }
        }

        rutaSeleccionada?.let { ruta ->
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, end = 8.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(Color(0xFFD3D3D3))
                    .padding(8.dp)
                    .align(Alignment.End)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Dejar de ver la ruta $ruta",
                        textDecoration = TextDecoration.Underline,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "✖",
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clickable {
                                rutaSeleccionada = null
                                rutaLatLng.value = emptyList()
                            }
                    )
                }
            }
        }
    }
}
