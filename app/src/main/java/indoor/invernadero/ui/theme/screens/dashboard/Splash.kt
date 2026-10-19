package indoor.invernadero.ui.theme.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay

@Composable
fun Splash(onNavigateToLogin: () -> Unit = {}) {
	LaunchedEffect(Unit) {
		delay(2500)
		onNavigateToLogin()
	}
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Color(0xFF4A724B))
			.verticalScroll(rememberScrollState()),
		horizontalAlignment = Alignment.CenterHorizontally
	){
		Spacer(modifier = Modifier.height(100.dp))
		CoilImage(
			imageModel = {"https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/wuk7la3i_expires_30_days.png"},
			imageOptions = ImageOptions(contentScale = ContentScale.Crop),
			modifier = Modifier
				.clip(shape = RoundedCornerShape(24.dp))
				.width(140.dp)
				.height(140.dp)
		)
		Spacer(modifier = Modifier.height(26.dp))
		Text(
			"INDOOR",
			color = Color(0xFFFFFFFF),
			fontSize = 52.sp,
			fontWeight = FontWeight.Bold,
		)
		Spacer(modifier = Modifier.height(12.dp))
		Text(
			"Tu invernadero en tus manos",
			color = Color(0xFFDCE9D8),
			fontSize = 18.sp,
		)
		Spacer(modifier = Modifier.height(60.dp))
		Text(
			"Preparando tu cultivo…",
			color = Color(0xFFFFFFFF),
			fontSize = 14.sp,
			modifier = Modifier.padding(bottom = 16.dp)
		)
		CircularProgressIndicator(
			color = Color(0xFFDCE9D8),
			modifier = Modifier.size(28.dp),
			strokeWidth = 3.dp
		)
		Spacer(modifier = Modifier.weight(1f))
		Text(
			"Invernadero IoT · ESP32",
			color = Color(0xFFDCE9D8),
			fontSize = 14.sp,
			modifier = Modifier.padding(bottom = 50.dp)
		)
	}
}
