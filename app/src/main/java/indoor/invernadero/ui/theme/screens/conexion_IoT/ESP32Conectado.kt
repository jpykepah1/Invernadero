package indoor.invernadero.ui.theme.screens.conexion_iot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ESP32Conectado(
    onContinuarClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF7F9F6))
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(24.dp))
                .padding(32.dp)
        ) {
            Text(
                "¡ESP32 Conectado!",
                color = Color(0xFF203823),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                "Tu dispositivo IoT está vinculado exitosamente y transmitiendo datos al broker.",
                color = Color(0xFF687168),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 28.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .background(color = Color(0xFF4A724B), shape = RoundedCornerShape(12.dp))
                    .clickable { onContinuarClick() }
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    "Volver al Dashboard",
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
