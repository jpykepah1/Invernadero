package indoor.invernadero.ui.theme.screens.alertas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

data class Alerta(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val tiempo: String,
    val tipo: String, // "ADVERTENCIA", "PELIGRO", "INFO"
    val leida: Boolean
)

private data class AlertStyle(
    val titleColor: Color,
    val cardBgColor: Color,
    val borderColor: Color,
    val iconUrl: String
)

@Composable
fun Alertas() {
    val iniciales = remember {
        listOf(
            Alerta(
                id = 1,
                titulo = "Temperatura alta",
                descripcion = "La temperatura superó el umbral recomendado (29 °C)",
                tiempo = "Hace 15 min · Sector A",
                tipo = "ADVERTENCIA",
                leida = false
            ),
            Alerta(
                id = 2,
                titulo = "Nivel de agua bajo",
                descripcion = "El estanque principal requiere recarga urgente",
                tiempo = "Hace 1 hora · Estanque 1",
                tipo = "PELIGRO",
                leida = false
            ),
            Alerta(
                id = 3,
                titulo = "Riego completado",
                descripcion = "Ciclo de riego de 12 minutos finalizado con éxito",
                tiempo = "Hoy, 06:42 · Programa mañana",
                tipo = "INFO",
                leida = true
            ),
            Alerta(
                id = 4,
                titulo = "Conexión restablecida",
                descripcion = "El módulo ESP32 volvió a transmitir datos",
                tiempo = "Ayer, 22:15 · Red Local",
                tipo = "INFO",
                leida = true
            )
        )
    }

    var alertasState by remember { mutableStateOf(iniciales) }
    var filtroSeleccionado by remember { mutableStateOf("Todas") }

    val countTodas = alertasState.size
    val countNoLeidas = alertasState.count { !it.leida }
    val countLeidas = alertasState.count { it.leida }

    val alertasFiltradas = when (filtroSeleccionado) {
        "No leídas" -> alertasState.filter { !it.leida }
        "Leídas" -> alertasState.filter { it.leida }
        else -> alertasState
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF7F9F6))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Alertas",
                    color = Color(0xFF203823),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "Marcar leídas",
                    color = Color(0xFF4A724B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        alertasState = alertasState.map { it.copy(leida = true) }
                    }
                )
            }
            Text(
                "Notificaciones del sistema y cultivo",
                color = Color(0xFF687168),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 22.dp, start = 24.dp)
            )

            // Pestañas de Filtros
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                // Todas
                val isTodasSelected = filtroSeleccionado == "Todas"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .weight(1f)
                        .background(
                            color = if (isTodasSelected) Color(0xFF4A724B) else Color(0xFFE7EFE5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { filtroSeleccionado = "Todas" }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        "Todas ($countTodas)",
                        color = if (isTodasSelected) Color(0xFFFFFFFF) else Color(0xFF687168),
                        fontSize = 13.sp,
                        fontWeight = if (isTodasSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                }

                // No leídas
                val isNoLeidasSelected = filtroSeleccionado == "No leídas"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .weight(1f)
                        .background(
                            color = if (isNoLeidasSelected) Color(0xFF4A724B) else Color(0xFFE7EFE5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { filtroSeleccionado = "No leídas" }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        "No leídas ($countNoLeidas)",
                        color = if (isNoLeidasSelected) Color(0xFFFFFFFF) else Color(0xFF687168),
                        fontSize = 13.sp,
                        fontWeight = if (isNoLeidasSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                }

                // Leídas
                val isLeidasSelected = filtroSeleccionado == "Leídas"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .weight(1f)
                        .background(
                            color = if (isLeidasSelected) Color(0xFF4A724B) else Color(0xFFE7EFE5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { filtroSeleccionado = "Leídas" }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        "Leídas ($countLeidas)",
                        color = if (isLeidasSelected) Color(0xFFFFFFFF) else Color(0xFF687168),
                        fontSize = 13.sp,
                        fontWeight = if (isLeidasSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }

            // Lista filtrada de Alertas
            if (alertasFiltradas.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 40.dp, start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "No hay alertas disponibles",
                        color = Color(0xFF687168),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                alertasFiltradas.forEach { alerta ->
                    val style = when (alerta.tipo) {
                        "PELIGRO" -> AlertStyle(
                            titleColor = Color(0xFFA13225),
                            cardBgColor = Color(0xFFFFFFFF),
                            borderColor = Color(0xFFA13225),
                            iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/0g5a4ijr_expires_30_days.png"
                        )
                        "ADVERTENCIA" -> AlertStyle(
                            titleColor = Color(0xFF9A5100),
                            cardBgColor = Color(0xFFFFF4E8),
                            borderColor = Color(0xFFDCE5D9),
                            iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/crtp0vfy_expires_30_days.png"
                        )
                        else -> AlertStyle(
                            titleColor = Color(0xFF4A724B),
                            cardBgColor = Color(0xFFFFFFFF),
                            borderColor = Color(0xFFDCE5D9),
                            iconUrl = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/qezhpy53_expires_30_days.png"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(bottom = 12.dp, start = 24.dp, end = 24.dp)
                            .border(
                                width = 1.dp,
                                color = style.borderColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clip(shape = RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                            .background(
                                color = style.cardBgColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                alertasState = alertasState.map {
                                    if (it.id == alerta.id) it.copy(leida = true) else it
                                }
                            }
                            .padding(vertical = 17.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CoilImage(
                                    imageModel = { style.iconUrl },
                                    imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .clip(shape = RoundedCornerShape(24.dp))
                                        .width(18.dp)
                                        .height(18.dp)
                                )
                                Text(
                                    alerta.titulo,
                                    color = style.titleColor,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            if (!alerta.leida) {
                                Column(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(24.dp))
                                        .width(8.dp)
                                        .height(8.dp)
                                        .background(
                                            color = Color(0xFF4A724B),
                                            shape = RoundedCornerShape(24.dp)
                                        )
                                ) {}
                            }
                        }
                        Text(
                            alerta.descripcion,
                            color = Color(0xFF203823),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        )
                        Text(
                            alerta.tiempo,
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            // Botón Limpiar Historial
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 28.dp, start = 24.dp, end = 24.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFE7EFE5),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        alertasState = emptyList()
                    }
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    "Limpiar historial de alertas",
                    color = Color(0xFF687168),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
