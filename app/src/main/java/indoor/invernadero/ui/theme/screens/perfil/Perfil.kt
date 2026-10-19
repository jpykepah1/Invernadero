package indoor.invernadero.ui.theme.screens.perfil

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import indoor.invernadero.ui.theme.extension.TextFieldView
import indoor.invernadero.viewmodel.InvernaderoViewModel

@Composable
fun Perfil(
    invernaderoViewModel: InvernaderoViewModel = viewModel(),
    onLogout: () -> Unit = {},
    onNavigateToAcercaDe: () -> Unit = {},
    onNavigateToConectarDispositivo: () -> Unit = {}
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("user_profile", Context.MODE_PRIVATE) }

    val isConnectedVal by invernaderoViewModel.isConnected.collectAsState()

    val currentUser = FirebaseAuth.getInstance().currentUser
    val firebaseEmail = currentUser?.email ?: "usuario@indoor.cl"
    val defaultName = if (currentUser?.email != null) {
        currentUser.email!!.substringBefore('@').replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    } else {
        "Usuario"
    }

    val nameState = remember { mutableStateOf(prefs.getString("name", defaultName) ?: defaultName) }
    val emailState = remember { mutableStateOf(prefs.getString("email", firebaseEmail) ?: firebaseEmail) }
    val locationState = remember { mutableStateOf(prefs.getString("location", "Valparaíso, Chile") ?: "Valparaíso, Chile") }

    LaunchedEffect(currentUser?.uid) {
        val uid = currentUser?.uid
        if (uid != null) {
            try {
                FirebaseFirestore.getInstance().collection("users").document(uid)
                    .get()
                    .addOnSuccessListener { doc ->
                        if (doc.exists()) {
                            val remoteName = doc.getString("name")
                            val remoteLocation = doc.getString("location")
                            if (!remoteName.isNullOrEmpty()) {
                                nameState.value = remoteName
                                prefs.edit { putString("name", remoteName) }
                            }
                            if (!remoteLocation.isNullOrEmpty()) {
                                locationState.value = remoteLocation
                                prefs.edit { putString("location", remoteLocation) }
                            }
                        }
                    }
            } catch (_: Exception) {}
        }
    }

    val deviceStatusText = if (isConnectedVal) "ESP32-INDOOR-A1 · Estado: Conectado" else "ESP32-INDOOR-A1 · Estado: Desconectado"
    val deviceStatusColor = if (isConnectedVal) Color(0xFF4A724B) else Color(0xFF9A5100)

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
                    "Mi Perfil",
                    color = Color(0xFF203823),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "Cerrar sesión",
                    color = Color(0xFFA13225),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        try {
                            prefs.edit { clear() }
                            FirebaseAuth.getInstance().signOut()
                        } catch (_: Exception) {}
                        onLogout()
                    }
                )
            }
            Text(
                "Gestiona tu cuenta y preferencias",
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 14.dp, start = 16.dp)
                ) {
                    CoilImage(
                        imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/FykQ5f4HLe/crf1hsp4_expires_30_days.png" },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .width(44.dp)
                            .height(44.dp)
                    )
                    Column {
                        Text(
                            nameState.value,
                            color = Color(0xFF203823),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        Text(
                            emailState.value,
                            color = Color(0xFF687168),
                            fontSize = 12.sp,
                        )
                    }
                }
                Text(
                    "Nombre completo",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                )
                TextFieldView(
                    placeholder = "Ingresa tu nombre",
                    value = nameState.value,
                    onValueChange = { newText -> nameState.value = newText },
                    textStyle = TextStyle(
                        color = Color(0xFF203823),
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier
                        .padding(bottom = 14.dp, start = 16.dp, end = 16.dp)
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
                Text(
                    "Correo electrónico",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                )
                TextFieldView(
                    placeholder = "ana@indoor.cl",
                    value = emailState.value,
                    onValueChange = { newText -> emailState.value = newText },
                    textStyle = TextStyle(
                        color = Color(0xFF203823),
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier
                        .padding(bottom = 14.dp, start = 16.dp, end = 16.dp)
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
                Text(
                    "Ubicación del invernadero",
                    color = Color(0xFF687168),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                )
                TextFieldView(
                    placeholder = "Valparaíso, Chile",
                    value = locationState.value,
                    onValueChange = { newText -> locationState.value = newText },
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
                    "Dispositivo asociado",
                    color = Color(0xFF203823),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 11.dp, start = 17.dp)
                )
                Text(
                    deviceStatusText,
                    color = deviceStatusColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp, start = 16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        "Cambiar de dispositivo",
                        color = Color(0xFF4A724B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigateToConectarDispositivo() }
                    )
                    if (isConnectedVal) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            "Desconectar",
                            color = Color(0xFFA13225),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                invernaderoViewModel.disconnectMqtt()
                                Toast.makeText(context, "Dispositivo desconectado", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
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
                    .clickable {
                        prefs.edit {
                            putString("name", nameState.value)
                            putString("email", emailState.value)
                            putString("location", locationState.value)
                        }
                        val uid = currentUser?.uid
                        if (uid != null) {
                            val profileData = mapOf(
                                "name" to nameState.value,
                                "email" to emailState.value,
                                "location" to locationState.value
                            )
                            FirebaseFirestore.getInstance().collection("users").document(uid)
                                .set(profileData, SetOptions.merge())
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Perfil guardado en la nube", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Perfil guardado localmente", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(context, "Perfil guardado exitosamente", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .padding(vertical = 18.dp)
            ) {
                Text(
                    "Guardar perfil",
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 35.dp, start = 24.dp, end = 24.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFE7EFE5),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onNavigateToAcercaDe() }
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    "Acerca de la aplicación",
                    color = Color(0xFF4A724B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
