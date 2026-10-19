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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import indoor.invernadero.ui.theme.extension.TextFieldView

@Composable
fun NuevaProgramacion(
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("riego_prefs", Context.MODE_PRIVATE) }
    val gson = remember { Gson() }

    val nombreRuleState = remember { mutableStateOf("") }
    val horaState = remember { mutableStateOf("") }
    val duracionState = remember { mutableStateOf("") }

    val diasSeleccionados = remember { mutableStateListOf("L", "M", "X", "J", "V") }
    val diasSemana = listOf("L", "M", "X", "J", "V", "S", "D")

    fun guardarProgramacion() {
        val json = prefs.getString("programaciones_list", null)
        val type = object : TypeToken<MutableList<ProgramacionRiego>>() {}.type
        val currentList: MutableList<ProgramacionRiego> = if (!json.isNullOrEmpty()) {
            try {
                gson.fromJson(json, type) ?: mutableListOf()
            } catch (_: Exception) {
                mutableListOf()
            }
        } else {
            mutableListOf(
                ProgramacionRiego(1, "Riego matutino · 06:30", "Duración: 12 min · Frecuencia: Todos los días"),
                ProgramacionRiego(2, "Riego vespertino · 18:30", "Duración: 10 min · Frecuencia: Lun, Mié, Vie")
            )
        }

        val newId = (currentList.maxOfOrNull { it.id } ?: 0) + 1
        val horaTexto = if (horaState.value.isBlank()) "08:00" else horaState.value
        val tituloTexto = if (nombreRuleState.value.isBlank()) "Nuevo riego · $horaTexto" else "${nombreRuleState.value} · $horaTexto"
        val duracionTexto = if (duracionState.value.isBlank()) "10 min" else "${duracionState.value} min"
        val diasTexto = if (diasSeleccionados.isEmpty()) "Diario" else diasSeleccionados.joinToString(", ")
        val detalleTexto = "Duración: $duracionTexto · Frecuencia: $diasTexto"

        currentList.add(ProgramacionRiego(newId, tituloTexto, detalleTexto))
        prefs.edit { putString("programaciones_list", gson.toJson(currentList)) }
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
                    "Nueva programación",
                    color = Color(0xFF203823),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "Cancelar",
                    color = Color(0xFF687168),
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { onNavigateBack() }
                )
            }
            Text(
                "Define hora y duración de riego",
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
                    "Nombre de la regla",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                )
                TextFieldView(
                    placeholder = "Ej. Riego matutino",
                    value = nombreRuleState.value,
                    onValueChange = { newText -> nombreRuleState.value = newText },
                    textStyle = TextStyle(
                        color = Color(0xFF203823),
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDCE5D9),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 14.dp, horizontal = 12.dp)
                )
            }
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
                    "Hora de inicio y duración",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .weight(1f)
                    ) {
                        Text(
                            "Hora (24h)",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "06:30",
                            value = horaState.value,
                            onValueChange = { newText -> horaState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFDCE5D9),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clip(shape = RoundedCornerShape(10.dp))
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(vertical = 14.dp, horizontal = 12.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Duración (minutos)",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "12",
                            value = duracionState.value,
                            onValueChange = { newText -> duracionState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFDCE5D9),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clip(shape = RoundedCornerShape(10.dp))
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(vertical = 14.dp, horizontal = 12.dp)
                        )
                    }
                }
            }

            // Sección de Días de la Semana
            Column(
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
                    .padding(vertical = 19.dp)
            ) {
                Text(
                    "Días de la semana",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    diasSemana.forEach { dia ->
                        val isDaySelected = diasSeleccionados.contains(dia)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .width(38.dp)
                                .height(38.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(
                                    color = if (isDaySelected) Color(0xFF4A724B) else Color(0xFFE7EFE5),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    if (isDaySelected) {
                                        diasSeleccionados.remove(dia)
                                    } else {
                                        diasSeleccionados.add(dia)
                                    }
                                }
                        ) {
                            Text(
                                text = dia,
                                color = if (isDaySelected) Color(0xFFFFFFFF) else Color(0xFF687168),
                                fontSize = 13.sp,
                                fontWeight = if (isDaySelected) FontWeight.Bold else FontWeight.Normal
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
                        color = Color(0xFF4A724B),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        guardarProgramacion()
                        Toast.makeText(context, "Programación guardada", Toast.LENGTH_SHORT).show()
                        onNavigateBack()
                    }
                    .padding(vertical = 18.dp)
            ) {
                Text(
                    "Guardar programación",
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
