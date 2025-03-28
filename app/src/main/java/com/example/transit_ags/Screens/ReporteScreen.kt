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
fun ReporteScreen(navController: NavController) {
    var selectedTransport by remember { mutableStateOf("Selecciona") }
    var selectedIncident by remember { mutableStateOf("Selecciona") }
    var additionalComments by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        imageUris = uris
    }
    var rutaTaxi by remember { mutableStateOf("")}

    val incidentQuestions = mapOf(
        "Choque" to listOf(
            "¿Qué tipo de accidente ocurrió?",
            "¿El accidente involucró solo al transporte público o a otros vehículos?",
            "¿Hubo heridos?",
            "¿El transporte continuó su ruta después del accidente? "
        ),
        "Pelea" to listOf(
            "¿Quiénes estuvieron involucrados? ",
            "¿Se usó violencia física? ",
            "¿Intervino la autoridad o seguridad del transporte? "
        ),
        "Robo" to listOf(
            "¿Qué tipo de robo ocurrió?",
            "¿El robo fue con violencia?",
            "¿Se alertó a las autoridades?",
            "¿El transporte se detuvo por el incidente? "
        ),
        "Acoso" to listOf(
            "¿Qué tipo de acoso ocurrió?",
            "¿El conductor tomó alguna acción? ",
            "¿Se reportó a alguna autoridad o seguridad del transporte?"
        ),
        "Falla mecánica" to listOf(
            "¿Qué falló en el transporte?",
            "¿El transporte pudo seguir su ruta?",
            "¿Se llamó a asistencia mecánica?"
        ),
        "Tiempo" to listOf(
            "¿Cuánto tiempo estuvo esperando el transporte?",
            "¿Se recibió algún aviso sobre el retraso?",
            "¿El retraso afectó su llegada a destino?"
        ),
        "Conducta" to listOf(
            "¿Cómo describiría la actitud del operador? ",
            "¿El operador siguió las normas de tránsito? ",
            "¿El operador tuvo algún comportamiento inadecuado?"
        ),
        "Seguridad" to listOf(
            "¿Cómo percibió la seguridad en el transporte?",
            "¿Hubo personas sospechosas o comportamientos inusuales? ",
            "¿Se reportó la situación a las autoridades o personal del transporte?"
        ),
        "Limpieza" to listOf(
            "¿Cuál es el problema de limpieza? ",
            "¿La suciedad o el mal olor afectan la comodidad del transporte?",
            "¿Se ha reportado este problema anteriormente?"
        )
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
        ReportCard(
            "¿Qué tipo de incidente ocurrió?",
            listOf("Choque", "Pelea", "Robo", "Acoso", "Falla mecánica", "Tiempo", "Conducta", "Seguridad", "Limpieza")
        ) { selectedIncident = it }

        if (selectedTransport == "Camión" || selectedTransport == "Combi" ) {
            OutlinedTextField(
                value = rutaTaxi,
                onValueChange = { rutaTaxi = it },
                label = { Text("Número de ruta (opcional)", fontSize = 18.sp) },
                modifier = Modifier.fillMaxWidth()
            )
        }else if (selectedTransport == "Taxi"){
            OutlinedTextField(
                value = rutaTaxi,
                onValueChange = { rutaTaxi = it },
                label = { Text("Número de Taxi (obligatorio)", fontSize = 18.sp) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        selectedQuestions.forEach { question ->
            if (question.contains("Comentarios adicionales")) {
                var localComments by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = localComments,
                    onValueChange = { localComments = it },
                    label = { Text(question, fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                val options = when {
                    question.contains("¿Cuánto tiempo estuvo esperando el transporte?") ->
                        listOf("Menos de 10 minutos", "Entre 10 y 20 minutos", "Entre 20 y 30 minutos", "Más de 30 minutos")
                    question.contains("¿Cómo describiría la actitud del operador?") ->
                        listOf("Amable y respetuoso", "Indiferente o poco atento", "Grosero o irrespetuoso")
                    question.contains("¿Cómo percibió la seguridad en el transporte?") ->
                        listOf("Segura, sin incidentes", "Insegura, hubo situaciones preocupantes", "Muy insegura, hubo un incidente grave")
                    question.contains("¿Cuál es el problema de limpieza?") ->
                        listOf("Suciedad visible (basura, derrames, polvo)", "Mal olor (drenaje, comida en descomposición, etc.)", "Ambas (suciedad y mal olor)")
                    question.contains("¿Qué tipo de robo ocurrió?") ->
                        listOf("Cartera o celular", "Robo de pertenencias grandes")
                    question.contains("¿Qué tipo de accidente ocurrió?") ->
                        listOf("Choque leve", "Choque fuerte", "Atropellamiento", "Volcadura")
                    question.contains("¿Quiénes estuvieron involucrados?") ->
                        listOf("Pasajeros", "Conductor y pasajero")
                    question.contains("¿Qué falló en el transporte?") ->
                        listOf("Motor", "Frenos", "Luces")
                    question.contains("¿Qué tipo de acoso ocurrió?") ->
                        listOf("Verbal","Físico","Otro")
                    question.contains("¿El accidente involucró solo al transporte público o a otros vehículos?") ->
                        listOf("Solo el transporte","Varios vehículos")
                    question.contains("¿El operador siguió las normas de tránsito?") ->
                        listOf("Sí, condujo de manera segura","No, manejó de forma imprudente","No estoy seguro/a")
                    question.contains("¿El operador tuvo algún comportamiento inadecuado?") ->
                        listOf("Sí, uso de celular mientras conducía","Sí, exceso de velocidad o frenazos bruscos","Sí, no respetó paradas o ignoró pasajeros","No, todo estuvo bien")
                    question.contains("¿Hubo personas sospechosas o comportamientos inusuales?") ->
                        listOf("No, todo normal","Sí, personas con actitud sospechosa","Sí, hubo discusiones o altercados")
                    else -> listOf("Sí", "No")
                }
                ReportCard(question, options) {}
            }
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

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
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
fun ReportCard(title: String, options: List<String>, onOptionSelected: (String) -> Unit = {}) {
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