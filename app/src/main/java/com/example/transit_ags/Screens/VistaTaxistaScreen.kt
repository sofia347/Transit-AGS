package com.example.transit_ags.Screens

import android.content.Context
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
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.material3.*
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.TileOverlayOptions
import kotlinx.coroutines.launch
import com.google.maps.android.heatmaps.HeatmapTileProvider

@Composable
fun VistaTaxistaScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    AndroidView(factory = { MapView(context).apply { onCreate(null); onResume() } }) { mapView ->
        mapView.getMapAsync { googleMap ->
            coroutineScope.launch {
                val heatMapProvider = HeatmapTileProvider.Builder()
                    .data(getHeatMapData()) // Lista de coordenadas
                    .radius(50) // Ajusta el radio de dispersión del calor
                    .build()

                googleMap.addTileOverlay(TileOverlayOptions().tileProvider(heatMapProvider))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(21.8853, -102.2916), 12f)) // Aguascalientes, México
            }
        }
    }
}

// Función para obtener los puntos de calor
fun getHeatMapData(): List<LatLng> {
    return listOf(
        LatLng(21.8853, -102.2916), // Aguascalientes Centro
        LatLng(21.8818, -102.2952),
        LatLng(21.8900, -102.3000),
        LatLng(21.8700, -102.2800),
        LatLng(21.9200, -102.3150), // Zona norte
        LatLng(21.8500, -102.2900), // Zona sur
        LatLng(21.9300, -102.2500), // Zona este
        LatLng(21.8700, -102.3500), // Zona oeste
        LatLng(21.8000, -102.4000), // Lugar lejano al suroeste
        LatLng(21.9500, -102.4500), // Lugar lejano al noroeste
        LatLng(21.7000, -102.5000), // Lugar lejano al sur
        LatLng(22.0000, -102.2000), // Lugar lejano al noreste
    )
}
