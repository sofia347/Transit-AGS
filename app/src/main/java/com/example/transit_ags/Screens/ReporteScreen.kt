package com.example.transit_ags.Screens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ReporteScreen(navController: NavController){
    var selectedTransport by remember { mutableStateOf("Selecciona") }
    var selectedIncident by remember { mutableStateOf("Selecciona") }
    var additionalComments by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        imageUris = uris
    }

    val incidentQuestions = mapOf(
        "Tiempo" to listOf("¿Cuánto tiempo esperaste?", "¿La unidad cumplió el horario?"),
        "Conducta" to listOf("¿El conductor fue amable?", "¿Hubo alguna falta de respeto?"),
        "Seguridad" to listOf("¿Te sentiste seguro en la unidad?", "¿Hubo algún comportamiento sospechoso?"),
        "Limpieza" to listOf("¿La unidad estaba limpia?", "¿Había mal olor?")
    )
    val selectedQuestions = incidentQuestions[selectedIncident] ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("GENERAR REPORTE", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard("¿En qué transporte sucedió el hecho?", listOf("Camión", "Combi", "Taxi")) { selectedTransport = it }
        ReportCard("¿Qué tipo de incidente ocurrió?", listOf("Tiempo", "Conducta", "Seguridad", "Limpieza")) { selectedIncident = it }

        selectedQuestions.forEach { question ->
            ReportCard(question, listOf("Sí", "No")) {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = additionalComments,
            onValueChange = { additionalComments = it },
            label = { Text("Comentarios adicionales", fontSize = 18.sp) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Subir Fotos o Videos", fontSize = 18.sp)
        }

        imageUris.forEachIndexed { index, uri ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
                Button(onClick = { imageUris = imageUris.filterIndexed { i, _ -> i != index } }) {
                    Text("Eliminar")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Enviar Reporte", fontSize = 20.sp)
        }

        // Mostrar el diálogo de confirmación cuando showDialog es verdadero
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        // Acción para enviar el reporte
                        showDialog = false
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("¿Está seguro de enviar el reporte?") },
                text = { Text("No se toleran reportes falsos.") }
            )
        }
    }
}

@Composable
fun ReportCard(title: String, options: List<String>, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecciona") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { expanded = true }
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(selectedOption, color = Color.Gray, fontSize = 18.sp)

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedOption = option
                        onOptionSelected(option)
                        expanded = false
                    }) {
                        Text(option, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
