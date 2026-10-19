package indoor.invernadero.ui.theme.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import indoor.invernadero.ui.theme.extension.TextFieldView

@Composable
fun RecuperarContrasena(
    viewModel: AuthViewModel = viewModel(),
    onResetSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val emailState = remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF4A724B))
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val minHeight = maxHeight
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = minHeight)
                        .padding(top = 24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(bottom = 14.dp, start = 26.dp, end = 37.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "INDOOR",
                                color = Color(0xFFFFFFFF),
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = "Logo",
                                tint = Color.White,
                                modifier = Modifier.size(38.dp)
                            )
                        }
                        Text(
                            "Tu cultivo, siempre conectado.",
                            color = Color(0xFFDCE9D8),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 35.dp, start = 24.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(bottom = 30.dp, start = 24.dp, end = 24.dp)
                                .clip(shape = RoundedCornerShape(24.dp))
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(top = 35.dp, bottom = 35.dp, end = 16.dp)
                        ) {
                            Text(
                                "Recuperar acceso",
                                color = Color(0xFF203823),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 18.dp, start = 17.dp)
                            )
                            Text(
                                "Te enviaremos un enlace de restablecimiento",
                                color = Color(0xFF687168),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 28.dp, start = 17.dp)
                            )
                            Text(
                                "Correo electrónico",
                                color = Color(0xFF687168),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 9.dp, start = 16.dp)
                            )
                            TextFieldView(
                                placeholder = "ana@indoor.cl",
                                value = emailState.value,
                                onValueChange = { newText ->
                                    emailState.value = newText
                                    if (authState is AuthState.Error || authState is AuthState.Success) {
                                        viewModel.resetAuthState()
                                    }
                                },
                                textStyle = TextStyle(
                                    color = Color(0xFF203823),
                                    fontSize = 14.sp,
                                ),
                                modifier = Modifier
                                    .padding(bottom = 16.dp, start = 16.dp)
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

                            // Error or Success Message
                            when (val state = authState) {
                                is AuthState.Error -> {
                                    Text(
                                        text = state.message,
                                        color = Color(0xFFA13225),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                    )
                                }
                                is AuthState.Success -> {
                                    Text(
                                        text = "Enlace enviado. Revisa tu correo electrónico.",
                                        color = Color(0xFF4A724B),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                    )
                                }
                                else -> {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(bottom = 28.dp, start = 16.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFF4A724B),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable(enabled = authState !is AuthState.Loading) {
                                        viewModel.resetPassword(
                                            email = emailState.value,
                                            onSuccess = onResetSuccess
                                        )
                                    }
                                    .padding(top = 18.dp, bottom = 18.dp, start = 15.dp)
                            ) {
                                if (authState is AuthState.Loading) {
                                    CircularProgressIndicator(
                                        color = Color(0xFFFFFFFF),
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        "Enviar enlace",
                                        color = Color(0xFFFFFFFF),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                            Text(
                                "Volver a iniciar sesión",
                                color = Color(0xFF4A724B),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clickable { onNavigateToLogin() }
                            )
                        }
                    }
                    Text(
                        "Cultiva con confianza.",
                        color = Color(0xFFDCE9D8),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 32.dp, start = 24.dp)
                    )
                }
            }
        }
    }
}
