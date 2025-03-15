package br.com.fiap.ecotrack_app
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack_app.model.Usuario
import br.com.fiap.ecotrack_app.screens.ApiScreen
import br.com.fiap.ecotrack_app.screens.IntroScreen_rev01
import br.com.fiap.ecotrack_app.screens.LoginScreen
import br.com.fiap.ecotrack_app.screens.RefeicaoScreen
import br.com.fiap.ecotrack_app.ui.theme.Ecotrack_appTheme

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val isLoggedInKey = "isLoggedIn"
    private val isSkipLoginForTestKey = "isSkipLoginForTest"
    private val isFirstTimeKey = "isFirstTime"
    private val usuarioFicticio = Usuario("chico", "chico@fiap.com", "12345678")

    object Routes {
        const val LOGIN_SCREEN = "LoginScreen"
        const val INTRO_SCREEN_REV01 = "IntroScreen_rev01"
        const val API_SCREEN = "ApiScreen"
        const val REFEICAO_SCREEN = "RefeicaoScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        setContent {
            Ecotrack_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val isLoggedIn = sharedPreferences.getBoolean(isLoggedInKey, false)
                    val skipLoginForTest = sharedPreferences.getBoolean(isSkipLoginForTestKey, false)
                    val isFirstTime = sharedPreferences.getBoolean(isFirstTimeKey, true)
                    var showLoginErrorDialog by remember { mutableStateOf(false) }

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

                    val startDestination = when {
                        sharedPreferences.getBoolean(isFirstTimeKey, true) -> Routes.LOGIN_SCREEN
                        sharedPreferences.getBoolean(isLoggedInKey, false) || sharedPreferences.getBoolean(
                            isSkipLoginForTestKey,
                            false
                        ) -> Routes.INTRO_SCREEN_REV01
                        else -> Routes.LOGIN_SCREEN
                    }

                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable(Routes.LOGIN_SCREEN) {
                            LoginScreen(
                                onLoginSuccess = { email, password ->
                                    if (validarUsuario(email, password)) {
                                        saveLoginState(true)
                                        saveIsFirstTime(false)
                                        navController.navigate(Routes.INTRO_SCREEN_REV01) {
                                            popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                                        }
                                    } else {
                                        showLoginErrorDialog = true
                                    }
                                },
                                onSkipLogin = {
                                    saveLoginState(false)
                                    saveSkipLoginForTest(true)
                                    saveIsFirstTime(false)
                                    navController.navigate(Routes.INTRO_SCREEN_REV01) {
                                        popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Routes.INTRO_SCREEN_REV01) {
                            IntroScreen_rev01(navController)
                        }
                        composable(Routes.API_SCREEN) {
                            ApiScreen(navController)
                        }

                        composable(Routes.REFEICAO_SCREEN) {
                          RefeicaoScreen(navController)
                        }
                    }
                }
            }
        }
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(isLoggedInKey, isLoggedIn).apply()
    }

    private fun saveSkipLoginForTest(isSkipLogin: Boolean) {
        sharedPreferences.edit().putBoolean(isSkipLoginForTestKey, isSkipLogin).apply()
    }

    private fun saveIsFirstTime(isFirstTime: Boolean) {
        sharedPreferences.edit().putBoolean(isFirstTimeKey, isFirstTime).apply()
    }

    private fun validarUsuario(email: String, password: String): Boolean {
        return email == usuarioFicticio.email && password == usuarioFicticio.password
    }
}