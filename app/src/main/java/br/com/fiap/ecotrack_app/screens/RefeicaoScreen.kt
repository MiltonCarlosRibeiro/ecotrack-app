package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.ecotrack_app.MainActivity
import br.com.fiap.ecotrack_app.R
import br.com.fiap.ecotrack_app.viewmodel.RefeicaoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefeicaoScreen(navController: NavController) {
    val viewModel: RefeicaoViewModel = viewModel()
    val databaseInitialized by viewModel.databaseInitialized.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.meals)) },
                actions = {
                    IconButton(onClick = { navController.navigate(MainActivity.Routes.INTRO_SCREEN_REV01) }) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = stringResource(id = R.string.home),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_meal))
                }
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Scroll adicionado aqui
        ) {
            if (databaseInitialized) {
                val refeicoes by viewModel.allRefeicoes.collectAsState(initial = emptyList())
                val rankingComidas by viewModel.rankingComidas.collectAsState(initial = emptyList())

                // Lista de Refeições
                refeicoes.forEach { refeicao ->
                    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                        Column {
                            Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                                Text(
                                    text = stringResource(id = R.string.meal_type, refeicao.tipo),
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = FontWeight.SemiBold
                                )

                            }
                            Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                                Text(
                                    text = stringResource(id = R.string.meal_food, refeicao.comida),
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val dataFormatada = sdf.format(Date(refeicao.data))
                                Text(
                                    text = stringResource(id = R.string.meal_date, dataFormatada),
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Ranking de Comidas
                rankingComidas.forEachIndexed { index, ranking ->
                    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                        Text(
                            text = stringResource(id = R.string.meal_ranking, index + 1, ranking.comida),
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            } else {
                Text(stringResource(id = R.string.loading), modifier = Modifier.padding(16.dp))
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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.add_meal)) },
        text = {
            Column {
                TextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text(stringResource(id = R.string.meal_type_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = comida,
                    onValueChange = { comida = it },
                    label = { Text(stringResource(id = R.string.meal_food_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showDatePicker = true }) {
                    Text(stringResource(id = R.string.select_date))
                }

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(tipo, comida, selectedDate)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(id = R.string.add))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = MaterialTheme.shapes.medium
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    selectedDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    showDatePicker = false
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}