package indoor.invernadero.ui.theme.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import indoor.invernadero.viewmodel.InvernaderoViewModel

@Composable
fun Dashboard(
    invernaderoViewModel: InvernaderoViewModel = viewModel(),
    onNavigateToCamara: () -> Unit = {},
    onNavigateToAlertas: () -> Unit = {},
    onNavigateToConectarDispositivo: () -> Unit = {},
    onRefreshData: () -> Unit = {}
) {
    val context = LocalContext.current
    val humedadVal by invernaderoViewModel.humedad.collectAsState()
    val temperaturaVal by invernaderoViewModel.temperatura.collectAsState()
    val phVal by invernaderoViewModel.ph.collectAsState()
    val isConnectedVal by invernaderoViewModel.isConnected.collectAsState()
    val isRiegoActivo by invernaderoViewModel.isRiegoActivo.collectAsState()

    val tempFloat = temperaturaVal.toFloatOrNull()
    val isTempAlert = tempFloat != null && tempFloat >= 30f
    val tempStatusText = if (isTempAlert) "Alerta" else "Óptimo"
    val tempStatusColor = if (isTempAlert) Color(0xFFA13225) else Color(0xFF4A724B)

    val statusText = if (isConnectedVal) "● ESP32 conectado · en vivo" else "● ESP32 desconectado (Toca para conectar)"
    val riegoStatusText = if (isRiegoActivo) "Válvula abierta · riego en curso" else "Válvula cerrada · sin riego en curso"

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
                    .padding(bottom = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Mi Invernadero",
                    color = Color(0xFF203823),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(
                            color = Color(0xFFE7EFE5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onNavigateToAlertas() }
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                ) {
                    Text(
                        "Alertas",
                        color = Color(0xFF4A724B),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Text(
                "Tomate · Sector A",
                color = Color(0xFF687168),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 22.dp, start = 24.dp)
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .background(color = Color(0xFFE7EFE5), shape = RoundedCornerShape(12.dp))
                    .clickable(enabled = !isConnectedVal) {
                        onNavigateToConectarDispositivo()
                    }
                    .padding(vertical = 16.dp, horizontal = 15.dp)
            ) {
                Text(
                    text = statusText,
                    color = if (isConnectedVal) Color(0xFF4A724B) else Color(0xFF9A5100),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                // Humedad del suelo
                Column(
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDCE5D9),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .weight(1f)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 15.dp)
                ) {
                    CoilImage(
                        imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/kyoag5j7_expires_30_days.png" },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                        modifier = Modifier
                            .padding(bottom = 7.dp, start = 10.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .width(19.dp)
                            .height(19.dp)
                    )
                    Text(
                        "Humedad del suelo",
                        color = Color(0xFF687168),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp, start = 10.dp)
                    )
                    Text(
                        if (humedadVal == "--") "-- %" else "$humedadVal %",
                        color = Color(0xFF203823),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 11.dp, start = 10.dp)
                    )
                    Text(
                        if (humedadVal == "--") "--" else "Óptimo",
                        color = Color(0xFF4A724B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                // Temperatura
                Column(
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDCE5D9),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .weight(1f)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(top = 11.dp, bottom = 15.dp)
                ) {
                    CoilImage(
                        imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/7xq4vmg8_expires_30_days.png" },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                        modifier = Modifier
                            .padding(bottom = 7.dp, start = 10.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .width(19.dp)
                            .height(19.dp)
                    )
                    Text(
                        "Temperatura",
                        color = Color(0xFF687168),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp, start = 10.dp)
                    )
                    Text(
                        if (temperaturaVal == "--") "-- °C" else "$temperaturaVal °C",
                        color = Color(0xFF203823),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 13.dp, start = 10.dp)
                    )
                    Text(
                        if (temperaturaVal == "--") "--" else tempStatusText,
                        color = tempStatusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                // pH
                Column(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDCE5D9),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .weight(1f)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(top = 15.dp, bottom = 15.dp, start = 10.dp)
                ) {
                    CoilImage(
                        imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/823t8xkj_expires_30_days.png" },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                        modifier = Modifier
                            .padding(bottom = 7.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .width(19.dp)
                            .height(19.dp)
                    )
                    Text(
                        "pH",
                        color = Color(0xFF687168),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        phVal,
                        color = Color(0xFF203823),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 11.dp)
                    )
                    Text(
                        if (phVal == "--") "--" else "Óptimo",
                        color = Color(0xFF4A724B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
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
                    "Riego",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 11.dp, start = 17.dp)
                )
                Text(
                    riegoStatusText,
                    color = Color(0xFF4A724B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                )
                Text(
                    "Próximo riego: mañana, 06:30 · 12 min",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Box {
                CoilImage(
                    imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/63p9vft4_expires_30_days.png" },
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                    modifier = Modifier
                        .padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .matchParentSize()
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .padding(vertical = 13.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 65.dp, start = 14.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(
                                color = Color(0xFFE7EFE5),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(top = 6.dp, bottom = 6.dp, start = 7.dp, end = 21.dp)
                    ) {
                        Text(
                            "SIMULADA",
                            color = Color(0xFF4A724B),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(end = 14.dp)
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(
                                    color = Color(0xFF4A724B),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { onNavigateToCamara() }
                                .padding(top = 12.dp, bottom = 12.dp, start = 14.dp, end = 48.dp)
                        ) {
                            Text(
                                "Ampliar cámara",
                                color = Color(0xFFFFFFFF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(bottom = 14.dp, start = 24.dp, end = 24.dp)
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
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    "Últimas alertas",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 11.dp, start = 16.dp)
                )
                Text(
                    if (isTempAlert) "Temperatura alta · $temperaturaVal °C" else "Sin alertas críticas",
                    color = if (isTempAlert) Color(0xFF9A5100) else Color(0xFF4A724B),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                )
                Text(
                    "Último riego completado · hoy, 06:42",
                    color = Color(0xFF687168),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF4A724B),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        if (!isConnectedVal) {
                            Toast.makeText(context, "Dispositivo desconectado. Ve a Perfil para conectarte", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
                            onRefreshData()
                        }
                    }
                    .padding(vertical = 15.dp)
            ) {
                Text(
                    "Actualizar datos",
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
