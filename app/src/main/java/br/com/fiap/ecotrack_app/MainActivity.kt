package br.com.fiap.ecotrack_app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack_app.database.AppDatabase
import br.com.fiap.ecotrack_app.screens.*
import br.com.fiap.ecotrack_app.ui.theme.Ecotrack_appTheme
import br.com.fiap.ecotrack_app.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import br.com.fiap.ecotrack_app.components.RegisterDialog

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val isLoggedInKey = "isLoggedIn"

    object Routes {
        const val LOGIN_SCREEN = "LoginScreen"
        const val INTRO_SCREEN = "IntroScreen"
        const val REFEICAO_SCREEN = "RefeicaoScreen"
        const val API_SCREEN = "ApiScreen"
        const val BMI_SCREEN = "BmiScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        setContent {
            Ecotrack_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF5783AF)
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                    var showLoginErrorDialog by remember { mutableStateOf(false) }
                    var showRegisterDialog by remember { mutableStateOf(false) }

                    if (showLoginErrorDialog) {
                        AlertDialog(
                            onDismissRequest = { showLoginErrorDialog = false },
                            title = { Text(text = "Erro de Login") },
                            text = { Text(text = "Usuário ou senha incorretos.") },
                            confirmButton = {
                                TextButton(onClick = { showLoginErrorDialog = false }) {
                                    Text("OK")
                                }
                            }
                        )
                    }

                    val startDestination = if (sharedPreferences.getBoolean(isLoggedInKey, false)) {
                        Routes.INTRO_SCREEN
                    } else {
                        Routes.LOGIN_SCREEN
                    }

                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable(Routes.LOGIN_SCREEN) {
                            LoginScreen(
                                navController = navController,
                                viewModel = authViewModel
                            )
                        }

                        composable(Routes.INTRO_SCREEN) {
                            IntroScreen_rev01(navController)
                        }

                        composable(Routes.REFEICAO_SCREEN) {
                            RefeicaoScreen(navController)
                        }

                        composable(Routes.API_SCREEN) {
                            ApiScreen(navController)
                        }

                        composable(Routes.BMI_SCREEN) {
                            BmiScreen(navController)
                        }
                    }

                    if (showRegisterDialog) {
                        RegisterDialog(
                            onDismiss = { showRegisterDialog = false },
                            viewModel = authViewModel
                        )
                    }
                }
            }
        }
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(isLoggedInKey, isLoggedIn).apply()
    }
}
