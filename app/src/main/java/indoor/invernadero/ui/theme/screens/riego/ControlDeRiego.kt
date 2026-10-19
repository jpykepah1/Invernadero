package indoor.invernadero.ui.theme.screens.riego

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import indoor.invernadero.viewmodel.InvernaderoViewModel

data class ProgramacionRiego(
    val id: Int,
    val titulo: String,
    val detalle: String
)

@Composable
fun ControlDeRiego(
    invernaderoViewModel: InvernaderoViewModel = viewModel(),
    onNavigateToNuevaProgramacion: () -> Unit = {}
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("riego_prefs", Context.MODE_PRIVATE) }
    val gson = remember { Gson() }

    fun loadProgramaciones(): List<ProgramacionRiego> {
        val json = prefs.getString("programaciones_list", null)
        return if (!json.isNullOrEmpty()) {
            try {
                val type = object : TypeToken<List<ProgramacionRiego>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            } catch (_: Exception) {
                emptyList()
            }
        } else {
            val initialList = listOf(
                ProgramacionRiego(1, "Riego matutino · 06:30", "Duración: 12 min · Frecuencia: Todos los días"),
                ProgramacionRiego(2, "Riego vespertino · 18:30", "Duración: 10 min · Frecuencia: Lun, Mié, Vie")
            )
            prefs.edit { putString("programaciones_list", gson.toJson(initialList)) }
            initialList
        }
    }

    var programacionesState by remember { mutableStateOf(loadProgramaciones()) }

    DisposableEffect(Unit) {
        programacionesState = loadProgramaciones()
        onDispose {}
    }

    val isConnected by invernaderoViewModel.isConnected.collectAsState()
    val isRiegoActivo by invernaderoViewModel.isRiegoActivo.collectAsState()

    val statusText = if (isRiegoActivo) "Válvula abierta · riego en curso" else "Válvula cerrada · sin riego en curso"
    val statusColor = if (isRiegoActivo) Color(0xFF4A724B) else Color(0xFF687168)
    val buttonText = if (isRiegoActivo) "Detener riego manual" else "Activar riego manual (5 min)"
    val buttonBgColor = if (isRiegoActivo) Color(0xFFA13225) else Color(0xFF4A724B)

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
                    "Control de Riego",
                    color = Color(0xFF203823),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "+ Nueva",
                    color = Color(0xFF4A724B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToNuevaProgramacion() }
                )
            }
            Text(
                "Programaciones automáticas y riego manual",
                color = Color(0xFF687168),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 22.dp, start = 24.dp)
            )
            Column(
                modifier = Modifier
                    .padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFDCE5D9),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 19.dp)
            ) {
                Text(
                    "Estado de válvula",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 11.dp, start = 17.dp)
                )
                Text(
                    statusText,
                    color = statusColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 14.dp, start = 16.dp, end = 16.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .background(
                            color = buttonBgColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            if (!isConnected) {
                                Toast.makeText(context, "Conecta el dispositivo para activar el riego", Toast.LENGTH_SHORT).show()
                            } else {
                                val nuevoMensaje = if (isRiegoActivo) "OFF" else "ON"
                                invernaderoViewModel.publicarMensaje("invernadero/actuadores/riego", nuevoMensaje)
                            }
                        }
                        .padding(top = 15.dp, bottom = 15.dp, start = 14.dp, end = 14.dp)
                ) {
                    Text(
                        buttonText,
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    "Próximo riego automático: mañana, 06:30 · 12 min",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 12.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Programaciones",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "${programacionesState.size} activas",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                )
            }

            if (programacionesState.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp, start = 24.dp, end = 24.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDCE5D9),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 24.dp)
                ) {
                    Text(
                        "No hay programaciones activas",
                        color = Color(0xFF687168),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                programacionesState.forEach { prog ->
                    Column(
                        modifier = Modifier
                            .padding(bottom = 12.dp, start = 24.dp, end = 24.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFDCE5D9),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clip(shape = RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(vertical = 17.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                prog.titulo,
                                color = Color(0xFF203823),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                "● Activo",
                                color = Color(0xFF4A724B),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            prog.detalle,
                            color = Color(0xFF687168),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(bottom = 12.dp, start = 16.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(
                                "Editar",
                                color = Color(0xFF4A724B),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(end = 24.dp)
                                    .clickable { onNavigateToNuevaProgramacion() }
                            )
                            Text(
                                "Eliminar",
                                color = Color(0xFFA13225),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    val newList = programacionesState.filter { item -> item.id != prog.id }
                                    programacionesState = newList
                                    prefs.edit { putString("programaciones_list", gson.toJson(newList)) }
                                }
                            )
                        }
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 28.dp, start = 24.dp, end = 24.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFE7EFE5),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onNavigateToNuevaProgramacion() }
                    .padding(vertical = 17.dp)
            ) {
                Text(
                    "+ Agregar nueva programación",
                    color = Color(0xFF4A724B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
