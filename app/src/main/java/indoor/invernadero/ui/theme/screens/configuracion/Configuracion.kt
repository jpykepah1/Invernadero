package indoor.invernadero.ui.theme.screens.configuracion

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import indoor.invernadero.ui.theme.extension.TextFieldView

@Composable
fun CropButton(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(
                color = if (isSelected) Color(0xFF4A724B) else Color(0xFFE7EFE5),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 6.dp)
    ) {
        Text(
            text = name,
            color = if (isSelected) Color(0xFFFFFFFF) else Color(0xFF687168),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Composable
fun Configuracion() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("crop_config", Context.MODE_PRIVATE) }

    var cultivoSeleccionado by remember { mutableStateOf(prefs.getString("cultivo", "Tomate") ?: "Tomate") }

    val humedadMinState = remember { mutableStateOf(prefs.getString("humedadMin", "60") ?: "60") }
    val humedadMaxState = remember { mutableStateOf(prefs.getString("humedadMax", "80") ?: "80") }
    val tempMinState = remember { mutableStateOf(prefs.getString("tempMin", "18") ?: "18") }
    val tempMaxState = remember { mutableStateOf(prefs.getString("tempMax", "27") ?: "27") }
    val phMinState = remember { mutableStateOf(prefs.getString("phMin", "6.0") ?: "6.0") }
    val phMaxState = remember { mutableStateOf(prefs.getString("phMax", "6.8") ?: "6.8") }

    fun selectCrop(crop: String) {
        cultivoSeleccionado = crop
        when (crop) {
            "Tomate" -> {
                humedadMinState.value = "60"
                humedadMaxState.value = "80"
                tempMinState.value = "18"
                tempMaxState.value = "27"
                phMinState.value = "6.0"
                phMaxState.value = "6.8"
            }
            "Lechuga" -> {
                humedadMinState.value = "70"
                humedadMaxState.value = "85"
                tempMinState.value = "15"
                tempMaxState.value = "22"
                phMinState.value = "6.0"
                phMaxState.value = "7.0"
            }
            "Albahaca" -> {
                humedadMinState.value = "65"
                humedadMaxState.value = "80"
                tempMinState.value = "20"
                tempMaxState.value = "28"
                phMinState.value = "6.0"
                phMaxState.value = "7.5"
            }
            "Ají" -> {
                humedadMinState.value = "55"
                humedadMaxState.value = "75"
                tempMinState.value = "20"
                tempMaxState.value = "30"
                phMinState.value = "5.8"
                phMaxState.value = "6.8"
            }
            "Zanahoria" -> {
                humedadMinState.value = "65"
                humedadMaxState.value = "80"
                tempMinState.value = "16"
                tempMaxState.value = "24"
                phMinState.value = "6.0"
                phMaxState.value = "6.8"
            }
        }
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
                    "Configuración",
                    color = Color(0xFF203823),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                "Ajusta los parámetros ideales de tu cultivo",
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
                    "Tipo de cultivo activo",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    CropButton(
                        name = "Tomate",
                        isSelected = cultivoSeleccionado == "Tomate",
                        onClick = { selectCrop("Tomate") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    )
                    CropButton(
                        name = "Lechuga",
                        isSelected = cultivoSeleccionado == "Lechuga",
                        onClick = { selectCrop("Lechuga") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    )
                    CropButton(
                        name = "Albahaca",
                        isSelected = cultivoSeleccionado == "Albahaca",
                        onClick = { selectCrop("Albahaca") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    CropButton(
                        name = "Ají",
                        isSelected = cultivoSeleccionado == "Ají",
                        onClick = { selectCrop("Ají") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    )
                    CropButton(
                        name = "Zanahoria",
                        isSelected = cultivoSeleccionado == "Zanahoria",
                        onClick = { selectCrop("Zanahoria") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    )
                    CropButton(
                        name = "Personalizado",
                        isSelected = cultivoSeleccionado == "Personalizado",
                        onClick = { cultivoSeleccionado = "Personalizado" },
                        modifier = Modifier.weight(1f)
                    )
                }
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
                    "Humedad del suelo ideal",
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
                            "Mínima (%)",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "60",
                            value = humedadMinState.value,
                            onValueChange = { newText -> humedadMinState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            "Máxima (%)",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "80",
                            value = humedadMaxState.value,
                            onValueChange = { newText -> humedadMaxState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    "Temperatura ambiental ideal",
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
                            "Mínima (°C)",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "18",
                            value = tempMinState.value,
                            onValueChange = { newText -> tempMinState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            "Máxima (°C)",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "27",
                            value = tempMaxState.value,
                            onValueChange = { newText -> tempMaxState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    "Rango pH del suelo",
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
                            "Mínimo pH",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "6.0",
                            value = phMinState.value,
                            onValueChange = { newText -> phMinState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            "Máximo pH",
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextFieldView(
                            placeholder = "6.8",
                            value = phMaxState.value,
                            onValueChange = { newText -> phMaxState.value = newText },
                            textStyle = TextStyle(
                                color = Color(0xFF203823),
                                fontSize = 14.sp,
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        prefs.edit {
                            putString("cultivo", cultivoSeleccionado)
                            putString("humedadMin", humedadMinState.value)
                            putString("humedadMax", humedadMaxState.value)
                            putString("tempMin", tempMinState.value)
                            putString("tempMax", tempMaxState.value)
                            putString("phMin", phMinState.value)
                            putString("phMax", phMaxState.value)
                        }
                        Toast.makeText(context, "Parámetros guardados", Toast.LENGTH_SHORT).show()
                    }
                    .padding(vertical = 18.dp)
            ) {
                Text(
                    "Guardar cambios",
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
