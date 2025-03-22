package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack_app.MainActivity
import br.com.fiap.ecotrack_app.R
import br.com.fiap.ecotrack_app.ui.theme.Ecotrack_appTheme
import kotlin.math.pow
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiScreen(navController: NavController) {
    val robotoFont = FontFamily(Font(R.font.roboto))
    val viewModel: BmiViewModel = remember { BmiViewModel() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.bmi_calculator),
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5783AF)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize() // Ensures the entire screen is covered
                .background(Color(0xFF5783AF)) // Background color applied first
                .padding(paddingValues) // Padding applied after background
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.weighing_machine),
                contentDescription = stringResource(id = R.string.weighing_image_desc),
                modifier = Modifier.size(140.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.enter_details),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = robotoFont
                    )

                    TextField(
                        value = viewModel.weight,
                        onValueChange = { viewModel.onWeightChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(stringResource(id = R.string.weight_placeholder)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    TextField(
                        value = viewModel.height,
                        onValueChange = { viewModel.onHeightChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(stringResource(id = R.string.height_placeholder)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        onClick = { viewModel.calculateBmi() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5783AF))
                    ) {
                        Text(stringResource(id = R.string.calculate_bmi), fontFamily = robotoFont, fontWeight = FontWeight.Bold)
                    }

                    if (viewModel.bmi > 0) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = String.format(Locale.getDefault(), stringResource(id = R.string.bmi_result), viewModel.bmi),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = robotoFont
                        )

                        Text(
                            text = stringResource(id = viewModel.getBmiStatusResId()),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = robotoFont,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

class BmiViewModel : ViewModel() {
    var weight by mutableStateOf("")
        private set

    var height by mutableStateOf("")
        private set

    var bmi by mutableStateOf(0.0)
        private set

    private var bmiStatus by mutableStateOf("")

    fun onWeightChange(newWeight: String) {
        weight = newWeight
    }

    fun onHeightChange(newHeight: String) {
        height = newHeight
    }

    fun calculateBmi() {
        val w = weight.toDoubleOrNull()
        val h = height.toDoubleOrNull()
        if (w != null && h != null) {
            bmi = w / (h / 100).pow(2)
            bmiStatus = when {
                bmi < 18.5 -> "underweight"
                bmi < 25.0 -> "ideal_weight"
                bmi < 30.0 -> "slightly_overweight"
                bmi < 35.0 -> "obesity_grade_1"
                bmi < 40.0 -> "obesity_grade_2"
                else -> "obesity_grade_3"
            }
        }
    }

    fun getBmiStatusResId(): Int {
        return when (bmiStatus) {
            "underweight" -> R.string.underweight
            "ideal_weight" -> R.string.ideal_weight
            "slightly_overweight" -> R.string.slightly_overweight
            "obesity_grade_1" -> R.string.obesity_grade_1
            "obesity_grade_2" -> R.string.obesity_grade_2
            "obesity_grade_3" -> R.string.obesity_grade_3
            else -> R.string.ideal_weight // Default case
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BmiScreenPreview() {
    val fakeNavController = rememberNavController()
    Ecotrack_appTheme {
        BmiScreen(navController = fakeNavController)
    }
}
