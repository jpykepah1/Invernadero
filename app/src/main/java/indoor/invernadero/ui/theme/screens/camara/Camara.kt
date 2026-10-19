package indoor.invernadero.ui.theme.screens.camara

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun Camara() {
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
					"Cámara",
					color = Color(0xFF203823),
					fontSize = 26.sp,
					fontWeight = FontWeight.Bold,
				)
				Text(
					"HD · 1080p",
					color = Color(0xFF4A724B),
					fontSize = 13.sp,
					fontWeight = FontWeight.Bold,
				)
			}
			Text(
				"Monitoreo en tiempo real del cultivo",
				color = Color(0xFF687168),
				fontSize = 14.sp,
				modifier = Modifier.padding(bottom = 22.dp, start = 24.dp)
			)
			Box(
				modifier = Modifier
					.padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
					.clip(shape = RoundedCornerShape(16.dp))
					.fillMaxWidth()
					.height(220.dp)
					.background(color = Color(0xFF203823), shape = RoundedCornerShape(16.dp))
			) {
				Text(
					text = "Vista previa de la cámara",
					color = Color(0xFFDCE9D8),
					fontSize = 15.sp,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.align(Alignment.Center)
				)
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(vertical = 13.dp)
				) {
					Row(
						horizontalArrangement = Arrangement.SpaceBetween,
						modifier = Modifier
							.padding(bottom = 110.dp, start = 14.dp, end = 14.dp)
							.fillMaxWidth()
					) {
						Column(
							modifier = Modifier
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
							modifier = Modifier
								.clip(shape = RoundedCornerShape(10.dp))
								.background(
									color = Color(0xFF4A724B),
									shape = RoundedCornerShape(10.dp)
								)
								.padding(top = 6.dp, bottom = 6.dp, start = 7.dp, end = 17.dp)
						) {
							Text(
								"CAM-01",
								color = Color(0xFFFFFFFF),
								fontSize = 10.sp,
								fontWeight = FontWeight.Bold,
							)
						}
					}
					Row(
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically,
						modifier = Modifier
							.padding(start = 14.dp, end = 14.dp)
							.fillMaxWidth()
					) {
						Column(
							modifier = Modifier
								.clip(shape = RoundedCornerShape(12.dp))
								.background(
									color = Color(0xFF4A724B),
									shape = RoundedCornerShape(12.dp)
								)
								.clickable { /* Capturar foto */ }
								.padding(top = 12.dp, bottom = 12.dp, start = 14.dp, end = 36.dp)
						) {
							Text(
								"Capturar foto",
								color = Color(0xFFFFFFFF),
								fontSize = 14.sp,
								fontWeight = FontWeight.Bold,
							)
						}
						Column(
							modifier = Modifier
								.clip(shape = RoundedCornerShape(12.dp))
								.background(
									color = Color(0xFFE7EFE5),
									shape = RoundedCornerShape(12.dp)
								)
								.clickable { /* Recargar cámara */ }
								.padding(vertical = 12.dp, horizontal = 14.dp)
						) {
							Text(
								"Recargar",
								color = Color(0xFF4A724B),
								fontSize = 14.sp,
								fontWeight = FontWeight.Bold,
							)
						}
					}
				}
			}
			Row(
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
					.fillMaxWidth()
			) {
				Text(
					"Galería de capturas",
					color = Color(0xFF203823),
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold,
				)
				Text(
					"Ver todas (6)",
					color = Color(0xFF4A724B),
					fontSize = 12.sp,
					fontWeight = FontWeight.Bold,
				)
			}
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.padding(bottom = 17.dp, start = 24.dp, end = 24.dp)
					.fillMaxWidth()
			) {
				CoilImage(
					imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/y7mffuip_expires_30_days.png" },
					imageOptions = ImageOptions(contentScale = ContentScale.Crop),
					modifier = Modifier
						.padding(end = 12.dp)
						.clip(shape = RoundedCornerShape(12.dp))
						.width(96.dp)
						.height(96.dp)
				)
				CoilImage(
					imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/e6ef0g58_expires_30_days.png" },
					imageOptions = ImageOptions(contentScale = ContentScale.Crop),
					modifier = Modifier
						.padding(end = 12.dp)
						.clip(shape = RoundedCornerShape(12.dp))
						.width(96.dp)
						.height(96.dp)
				)
				CoilImage(
					imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/y6yfg3es_expires_30_days.png" },
					imageOptions = ImageOptions(contentScale = ContentScale.Crop),
					modifier = Modifier
						.clip(shape = RoundedCornerShape(12.dp))
						.width(96.dp)
						.height(96.dp)
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
					"Detección IA de hojas",
					color = Color(0xFF203823),
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.padding(bottom = 11.dp, start = 17.dp)
				)
				Text(
					"Estado de follaje: saludable · sin plagas visibles",
					color = Color(0xFF4A724B),
					fontSize = 14.sp,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
				)
				Text(
					"Último análisis automático: hoy, 09:00",
					color = Color(0xFF687168),
					fontSize = 12.sp,
					modifier = Modifier.padding(start = 16.dp)
				)
			}
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier
					.padding(bottom = 16.dp, start = 24.dp, end = 24.dp)
					.clip(shape = RoundedCornerShape(12.dp))
					.fillMaxWidth()
					.background(
						color = Color(0xFF4A724B),
						shape = RoundedCornerShape(12.dp)
					)
					.clickable { /* Analizar cultivo con IA */ }
					.padding(vertical = 17.dp)
			) {
				Text(
					"Analizar cultivo con IA",
					color = Color(0xFFFFFFFF),
					fontSize = 14.sp,
					fontWeight = FontWeight.Bold,
				)
			}
			Spacer(modifier = Modifier.height(110.dp))
		}
	}
}
