package com.example.transit_ags

import com.example.transit_ags.Data.BottomNavItem
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.transit_ags.DataBaseLocal.DatabaseHelper
import com.example.transit_ags.Screens.CuentaScreen
import com.example.transit_ags.Screens.LoginScreen
import com.example.transit_ags.Screens.MapScreen
import com.example.transit_ags.Screens.MiCuentaScreen
import com.example.transit_ags.Screens.RecargaMapScreen
import com.example.transit_ags.Screens.RecargarScreen
import com.example.transit_ags.Screens.RegistroScreen
import com.example.transit_ags.Screens.ReporteScreen
import com.example.transit_ags.Screens.SaldoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permitir que la app ocupe toda la pantalla y esconda la barra de estado
        //WindowCompat.setDecorFitsSystemWindows(window, false)

        // Esconder la barra de estado (notificaciones) y la barra de navegación
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        try{
            val dbHelper = DatabaseHelper(this)
            val db = dbHelper.writableDatabase
            Log.d("DB", "Database loaded successfully")
        }catch (exception:Exception){
            Log.d("DB", "error: $exception")
        }

        setContent {
            ComposeMultiScreenApp()
        }
    }
}


@Composable
fun ComposeMultiScreenApp() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Lista de pantallas donde NO queremos mostrar la barra de navegación
    val hideBottomBarRoutes = listOf("registro", "login")
    val shouldShowBottomBar = currentRoute !in hideBottomBarRoutes

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier.padding(innerPadding)
        ) {
            SetupNavGraph(navController)
        }
    }
}


@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = "registro"){
        composable("registro"){ RegistroScreen(navController) }
        composable("login"){ LoginScreen(navController) }
        composable("maps"){ MapScreen(navController) }
        composable("reporte"){ ReporteScreen(navController) }
        composable("cuenta"){ CuentaScreen(navController) }
        composable("saldo"){ SaldoScreen(navController) }
        composable("micuenta"){ MiCuentaScreen(navController) }
        composable("recargamap"){ RecargaMapScreen(navController) }
        composable("recargarahora"){ RecargarScreen(navController) }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("reporte", Icons.Filled.Warning, "Reporte"),
        BottomNavItem("maps", Icons.Filled.Search , "Mapa"),
        BottomNavItem("cuenta", Icons.Filled.Person , "Cuenta")
    )

    NavigationBar { // Aquí cambiamos BottomNavigation por NavigationBar
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        restoreState = true
                    }
                }
            )
        }
    }
}

