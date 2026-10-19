package indoor.invernadero.ui.theme.screens.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import indoor.invernadero.ui.theme.extension.TextFieldView

@Composable
fun Registro(
    viewModel: AuthViewModel = viewModel(),
    onRegisterSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onGoogleRegisterClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }

    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("47657586079-uo2rpfv7lrbjnianh7jojonne3fqcgbi.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (!idToken.isNullOrEmpty()) {
                viewModel.signInWithGoogle(idToken = idToken, onSuccess = onRegisterSuccess)
            } else {
                viewModel.signInWithGoogle(idToken = "", onSuccess = onRegisterSuccess)
            }
        } catch (_: ApiException) {
            viewModel.signInWithGoogle(idToken = "", onSuccess = onRegisterSuccess)
        }
    }

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
                                "Crear cuenta",
                                color = Color(0xFF203823),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 18.dp, start = 17.dp)
                            )
                            Text(
                                "Comienza a monitorear tu invernadero",
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
                                    if (authState is AuthState.Error) viewModel.resetAuthState()
                                },
                                textStyle = TextStyle(
                                    color = Color(0xFF203823),
                                    fontSize = 14.sp,
                                ),
                                modifier = Modifier
                                    .padding(bottom = 20.dp, start = 16.dp)
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
                                "Contraseña",
                                color = Color(0xFF687168),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                            )
                            OutlinedTextField(
                                value = passwordState.value,
                                onValueChange = { newText ->
                                    passwordState.value = newText
                                    if (authState is AuthState.Error) viewModel.resetAuthState()
                                },
                                singleLine = true,
                                placeholder = {
                                    Text("Mínimo 8 caracteres", color = Color(0xFF687168), fontSize = 14.sp)
                                },
                                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    Text(
                                        text = if (passwordVisible.value) "Ocultar" else "Mostrar",
                                        color = Color(0xFF4A724B),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clickable { passwordVisible.value = !passwordVisible.value }
                                            .padding(horizontal = 12.dp)
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDCE5D9),
                                    unfocusedBorderColor = Color(0xFFDCE5D9),
                                    focusedContainerColor = Color(0xFFFFFFFF),
                                    unfocusedContainerColor = Color(0xFFFFFFFF),
                                    focusedTextColor = Color(0xFF203823),
                                    unfocusedTextColor = Color(0xFF203823)
                                ),
                                modifier = Modifier
                                    .padding(bottom = 20.dp, start = 16.dp)
                                    .fillMaxWidth()
                            )
                            Text(
                                "Confirmar contraseña",
                                color = Color(0xFF687168),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                            )
                            OutlinedTextField(
                                value = confirmPasswordState.value,
                                onValueChange = { newText ->
                                    confirmPasswordState.value = newText
                                    if (authState is AuthState.Error) viewModel.resetAuthState()
                                },
                                singleLine = true,
                                placeholder = {
                                    Text("Repite tu contraseña", color = Color(0xFF687168), fontSize = 14.sp)
                                },
                                visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    Text(
                                        text = if (confirmPasswordVisible.value) "Ocultar" else "Mostrar",
                                        color = Color(0xFF4A724B),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clickable { confirmPasswordVisible.value = !confirmPasswordVisible.value }
                                            .padding(horizontal = 12.dp)
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDCE5D9),
                                    unfocusedBorderColor = Color(0xFFDCE5D9),
                                    focusedContainerColor = Color(0xFFFFFFFF),
                                    unfocusedContainerColor = Color(0xFFFFFFFF),
                                    focusedTextColor = Color(0xFF203823),
                                    unfocusedTextColor = Color(0xFF203823)
                                ),
                                modifier = Modifier
                                    .padding(bottom = 12.dp, start = 16.dp)
                                    .fillMaxWidth()
                            )

                            // Error Message Display
                            if (authState is AuthState.Error) {
                                Text(
                                    text = (authState as AuthState.Error).message,
                                    color = Color(0xFFA13225),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                )
                            } else {
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(bottom = 14.dp, start = 16.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFF4A724B),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable(enabled = authState !is AuthState.Loading) {
                                        viewModel.register(
                                            email = emailState.value,
                                            pass = passwordState.value,
                                            confirmPass = confirmPasswordState.value,
                                            onSuccess = onRegisterSuccess
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
                                        "Registrarme",
                                        color = Color(0xFFFFFFFF),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(bottom = 35.dp, start = 16.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFFE7EFE5),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable(enabled = authState !is AuthState.Loading) {
                                        googleSignInLauncher.launch(googleSignInClient.signInIntent)
                                    }
                                    .padding(vertical = 17.dp, horizontal = 14.dp)
                            ) {
                                Text(
                                    "G   Registrarme con Google",
                                    color = Color(0xFF4A724B),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                "¿Ya tienes cuenta?  Inicia sesión",
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
