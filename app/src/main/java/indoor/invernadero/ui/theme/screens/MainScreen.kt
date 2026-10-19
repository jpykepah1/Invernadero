package indoor.invernadero.ui.theme.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import indoor.invernadero.ui.theme.screens.camara.Camara
import indoor.invernadero.ui.theme.screens.configuracion.Configuracion
import indoor.invernadero.ui.theme.screens.dashboard.Dashboard
import indoor.invernadero.ui.theme.screens.perfil.Perfil
import indoor.invernadero.ui.theme.screens.riego.ControlDeRiego
import indoor.invernadero.viewmodel.InvernaderoViewModel

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : BottomNavItem("dashboard_tab", "Dashboard", Icons.Default.Home)
    object Control : BottomNavItem("control_tab", "Control", Icons.Default.WaterDrop)
    object Camara : BottomNavItem("camara_tab", "Cámara", Icons.Default.CameraAlt)
    object Config : BottomNavItem("config_tab", "Config", Icons.Default.Settings)
    object Perfil : BottomNavItem("perfil_tab", "Perfil", Icons.Default.Person)
}

@Composable
fun MainScreen(
    onNavigateToAlertas: () -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateToAcercaDe: () -> Unit = {},
    onNavigateToConectarDispositivo: () -> Unit = {},
    onNavigateToNuevaProgramacion: () -> Unit = {}
) {
    val bottomNavController = rememberNavController()
    val invernaderoViewModel: InvernaderoViewModel = viewModel()

    val navItems = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Control,
        BottomNavItem.Camara,
        BottomNavItem.Config,
        BottomNavItem.Perfil
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFFFFFFF),
                contentColor = Color(0xFF203823)
            ) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF4A724B),
                            selectedTextColor = Color(0xFF4A724B),
                            unselectedIconColor = Color(0xFF687168),
                            unselectedTextColor = Color(0xFF687168),
                            indicatorColor = Color(0xFFE7EFE5)
                        ),
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Dashboard.route) {
                Dashboard(
                    invernaderoViewModel = invernaderoViewModel,
                    onNavigateToCamara = {
                        bottomNavController.navigate(BottomNavItem.Camara.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToAlertas = onNavigateToAlertas,
                    onNavigateToConectarDispositivo = onNavigateToConectarDispositivo
                )
            }
            composable(BottomNavItem.Control.route) {
                ControlDeRiego(
                    invernaderoViewModel = invernaderoViewModel,
                    onNavigateToNuevaProgramacion = onNavigateToNuevaProgramacion
                )
            }
            composable(BottomNavItem.Camara.route) {
                Camara()
            }
            composable(BottomNavItem.Config.route) {
                Configuracion()
            }
            composable(BottomNavItem.Perfil.route) {
                Perfil(
                    invernaderoViewModel = invernaderoViewModel,
                    onLogout = onLogout,
                    onNavigateToAcercaDe = onNavigateToAcercaDe,
                    onNavigateToConectarDispositivo = onNavigateToConectarDispositivo
                )
            }
        }
    }
}
