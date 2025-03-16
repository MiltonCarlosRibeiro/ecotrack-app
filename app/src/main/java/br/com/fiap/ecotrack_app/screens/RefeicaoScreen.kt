package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.ecotrack_app.MainActivity
import br.com.fiap.ecotrack_app.R
import br.com.fiap.ecotrack_app.viewmodel.RefeicaoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefeicaoScreen(navController: NavController) {
    val viewModel: RefeicaoViewModel = viewModel()
    val databaseInitialized by viewModel.databaseInitialized.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val robotoFont = FontFamily(Font(R.font.roboto))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.meals),
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(MainActivity.Routes.INTRO_SCREEN_REV01) }) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = stringResource(id = R.string.home),
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5783AF)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF5783AF),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_meal))
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFE3F2FD))
        ) {
            if (databaseInitialized) {
                val refeicoes by viewModel.allRefeicoes.collectAsState(initial = emptyList())
                val rankingComidas by viewModel.rankingComidas.collectAsState(initial = emptyList())

                refeicoes.forEach { refeicao ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(id = R.string.meal_type, refeicao.tipo),
                                fontWeight = FontWeight.Bold,
                                fontFamily = robotoFont,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.meal_food, refeicao.comida),
                                fontWeight = FontWeight.Normal,
                                fontFamily = robotoFont,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val dataFormatada = sdf.format(Date(refeicao.data))
                            Text(
                                text = stringResource(id = R.string.meal_date, dataFormatada),
                                fontWeight = FontWeight.Light,
                                fontFamily = robotoFont,
                                color = Color.DarkGray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                rankingComidas.forEachIndexed { index, ranking ->
                    Card(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Text(
                            text = stringResource(id = R.string.meal_ranking, index + 1, ranking.comida),
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = robotoFont,
                            color = Color.Black
                        )
                    }
                }
            } else {
                Text(
                    stringResource(id = R.string.loading),
                    modifier = Modifier.padding(16.dp),
                    fontFamily = robotoFont,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        }

        if (showDialog) {
            AddRefeicaoDialog(
                onDismiss = { showDialog = false },
                onConfirm = { tipo, comida, data ->
                    viewModel.insertRefeicao(tipo, comida, data)
                    showDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRefeicaoDialog(onDismiss: () -> Unit, onConfirm: (String, String, Long) -> Unit) {
    var tipo by remember { mutableStateOf("Almoço") }
    var comida by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)
    val robotoFont = FontFamily(Font(R.font.roboto))

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(id = R.string.add_meal),
                fontFamily = robotoFont, // APPLIED FONT
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        text = {
            Column {
                TextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text(stringResource(id = R.string.meal_type_label), fontFamily = robotoFont) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = comida,
                    onValueChange = { comida = it },
                    label = { Text(stringResource(id = R.string.meal_food_label), fontFamily = robotoFont) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showDatePicker = true }) {
                    Text(stringResource(id = R.string.select_date))
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(tipo, comida, selectedDate) }) {
                Text(stringResource(id = R.string.add))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        containerColor = Color(0xFFE3F2FD)
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = { Button(onClick = { showDatePicker = false }) { Text("Confirm") } },
            dismissButton = { Button(onClick = { showDatePicker = false }) { Text("Cancel") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
