package indoor.invernadero.ui.theme.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AcercaDe(onBackClick: () -> Unit = {}) {
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
                .padding(top = 24.dp, bottom = 30.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "← Volver",
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onBackClick() }
                )
                Text(
                    "INDOOR IoT",
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    "INDOOR",
                    color = Color(0xFFFFFFFF),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Versión 1.0.0",
                    color = Color(0xFFDCE9D8),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .border(width = 1.dp, color = Color(0xFFDCE5D9), shape = RoundedCornerShape(16.dp))
                    .clip(shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(
                    "Sobre la aplicación",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "INDOOR es una solución inteligente de monitoreo y automatización para invernaderos basada en tecnología IoT con microcontroladores ESP32.",
                    color = Color(0xFF687168),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .border(width = 1.dp, color = Color(0xFFDCE5D9), shape = RoundedCornerShape(16.dp))
                    .clip(shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(
                    "Desarrollo y Soporte",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "Desarrollado para agricultura inteligente y control automatizado de riego, temperatura, humedad y pH del suelo.",
                    color = Color(0xFF687168),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
