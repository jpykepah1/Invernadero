package indoor.invernadero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import indoor.invernadero.ui.theme.InvernaderoTheme
import indoor.invernadero.ui.theme.screens.MainScreen
import indoor.invernadero.ui.theme.screens.alertas.Alertas
import indoor.invernadero.ui.theme.screens.auth.Login
import indoor.invernadero.ui.theme.screens.auth.RecuperarContrasena
import indoor.invernadero.ui.theme.screens.auth.Registro
import indoor.invernadero.ui.theme.screens.camara.Camara
import indoor.invernadero.ui.theme.screens.conexion_iot.ConectarDispositivo
import indoor.invernadero.ui.theme.screens.conexion_iot.ESP32Conectado
import indoor.invernadero.ui.theme.screens.configuracion.Configuracion
import indoor.invernadero.ui.theme.screens.dashboard.Dashboard
import indoor.invernadero.ui.theme.screens.dashboard.Splash
import indoor.invernadero.ui.theme.screens.perfil.AcercaDe
import indoor.invernadero.ui.theme.screens.perfil.Perfil
import indoor.invernadero.ui.theme.screens.riego.ControlDeRiego
import indoor.invernadero.ui.theme.screens.riego.NuevaProgramacion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InvernaderoTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            Splash(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            Login(
                onLoginClick = {
                    navController.navigate("main_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate("recuperar_contrasena")
                },
                onRegisterClick = {
                    navController.navigate("registro")
                },
                onGoogleLoginClick = {
                    navController.navigate("main_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("main_screen") {
            MainScreen(
                onNavigateToAlertas = {
                    navController.navigate("alertas")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("main_screen") { inclusive = true }
                    }
                },
                onNavigateToAcercaDe = {
                    navController.navigate("acerca_de")
                },
                onNavigateToConectarDispositivo = {
                    navController.navigate("conectar_dispositivo")
                },
                onNavigateToNuevaProgramacion = {
                    navController.navigate("nueva_programacion")
                }
            )
        }
        composable("conectar_dispositivo") {
            ConectarDispositivo(
                onVincularSuccess = {
                    navController.navigate("esp32_conectado")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("esp32_conectado") {
            ESP32Conectado(
                onContinuarClick = {
                    navController.navigate("main_screen") {
                        popUpTo("conectar_dispositivo") { inclusive = true }
                    }
                }
            )
        }
        composable("nueva_programacion") {
            NuevaProgramacion(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("dashboard") {
            Dashboard(
                onNavigateToCamara = {
                    navController.navigate("camara")
                },
                onNavigateToAlertas = {
                    navController.navigate("alertas")
                },
                onNavigateToConectarDispositivo = {
                    navController.navigate("conectar_dispositivo")
                },
                onRefreshData = {
                    // Refrescar datos
                }
            )
        }
        composable("recuperar_contrasena") {
            RecuperarContrasena(
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable("registro") {
            Registro(
                onRegisterSuccess = {
                    navController.navigate("main_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onGoogleRegisterClick = {
                    navController.navigate("main_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("alertas") {
            Alertas()
        }
        composable("camara") {
            Camara()
        }
        composable("configuracion") {
            Configuracion()
        }
        composable("perfil") {
            Perfil(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("main_screen") { inclusive = true }
                    }
                },
                onNavigateToAcercaDe = {
                    navController.navigate("acerca_de")
                },
                onNavigateToConectarDispositivo = {
                    navController.navigate("conectar_dispositivo")
                }
            )
        }
        composable("acerca_de") {
            AcercaDe(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("control_riego") {
            ControlDeRiego(
                onNavigateToNuevaProgramacion = {
                    navController.navigate("nueva_programacion")
                }
            )
        }
    }
}
