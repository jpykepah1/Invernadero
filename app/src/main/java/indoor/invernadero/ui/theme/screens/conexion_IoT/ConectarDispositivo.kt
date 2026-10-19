package indoor.invernadero.ui.theme.screens.conexion_iot

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import indoor.invernadero.ui.theme.extension.TextFieldView

@Composable
fun ConectarDispositivo(
    onVincularSuccess: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val deviceIdState = remember { mutableStateOf("ESP32-INDOOR-A1") }
    val tokenState = remember { mutableStateOf("") }
    val wifiState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF7F9F6))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF4A724B))
                .padding(top = 24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(bottom = 14.dp, start = 26.dp, end = 37.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "← Volver",
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { onNavigateBack() }
                )
                CoilImage(
                    imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/w0f9u7at_expires_30_days.png" },
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .width(38.dp)
                        .height(38.dp)
                )
            }
            Text(
                "Vincular hardware IoT.",
                color = Color(0xFFDCE9D8),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 30.dp, start = 24.dp)
            )
            Column(
                modifier = Modifier
                    .padding(bottom = 40.dp, start = 24.dp, end = 24.dp)
                    .clip(shape = RoundedCornerShape(24.dp))
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(top = 35.dp, bottom = 35.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    "Conectar ESP32",
                    color = Color(0xFF203823),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 18.dp)
                )
                Text(
                    "Ingresa los datos de tu dispositivo",
                    color = Color(0xFF687168),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 28.dp)
                )
                Text(
                    "ID o MAC del dispositivo",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 9.dp)
                )
                TextFieldView(
                    placeholder = "ESP32-INDOOR-A1",
                    value = deviceIdState.value,
                    onValueChange = { newText -> deviceIdState.value = newText },
                    textStyle = TextStyle(
                        color = Color(0xFF203823),
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier
                        .padding(bottom = 20.dp)
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
                        .padding(vertical = 17.dp, horizontal = 12.dp)
                )
                Text(
                    "Token de autenticación",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextFieldView(
                    placeholder = "••••••••••••",
                    value = tokenState.value,
                    onValueChange = { newText -> tokenState.value = newText },
                    textStyle = TextStyle(
                        color = Color(0xFF203823),
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier
                        .padding(bottom = 20.dp)
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
                        .padding(vertical = 17.dp, horizontal = 12.dp)
                )
                Text(
                    "Nombre de tu red Wi-Fi",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextFieldView(
                    placeholder = "MiRed_Invernadero",
                    value = wifiState.value,
                    onValueChange = { newText -> wifiState.value = newText },
                    textStyle = TextStyle(
                        color = Color(0xFF203823),
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier
                        .padding(bottom = 28.dp)
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
                        .padding(vertical = 17.dp, horizontal = 12.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 14.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF4A724B),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onVincularSuccess() }
                        .padding(top = 18.dp, bottom = 18.dp)
                ) {
                    Text(
                        "Vincular dispositivo",
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
