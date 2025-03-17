package br.com.fiap.ecotrack_app.screens

import br.com.fiap.ecotrack_app.components.CaloriasChart
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    val totalCalorias by viewModel.totalCalorias.collectAsState(initial = 0)
    var showDialog by remember { mutableStateOf(false) }
    var filtro by remember { mutableStateOf("Diário") }

    // Seleciona o fluxo de calorias com base no filtro escolhido
    val caloriasFlow = when (filtro) {
        "Semanal" -> viewModel.getCaloriasPorSemana()
        "Mensal" -> viewModel.getCaloriasPorMes()
        else -> viewModel.getCaloriasPorDia()
    }

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

                // 📌 Exibir total de calorias consumidas
                Text(
                    text = "Total de calorias consumidas: $totalCalorias kcal",
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoFont,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )

                // 📌 Botões para alternar entre períodos (Diário, Semanal, Mensal)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    listOf("Diário", "Semanal", "Mensal").forEach { periodo ->
                        Button(
                            onClick = { filtro = periodo },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (filtro == periodo) Color(0xFF5783AF) else Color.Gray,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(periodo)
                        }
                    }
                }

                // 📌 Exibir gráfico de consumo calórico
                Text(
                    text = "Gráfico de Consumo de Calorias",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
                CaloriasChart(caloriasFlow = caloriasFlow)

                // 📌 Lista de refeições
                refeicoes.forEachIndexed { index, refeicao ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Tipo: ${refeicao.tipo}", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Comida: ${refeicao.comida}", fontWeight = FontWeight.Normal)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Calorias: ${refeicao.calorias} kcal", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(8.dp))

                            // 📌 Exibição da data formatada corretamente
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val dataFormatada = sdf.format(Date(refeicao.data))
                            Text(text = "Data: $dataFormatada", fontWeight = FontWeight.Light)
                        }
                    }

                    // 📌 Adicionar um divisor entre os itens
                    if (index < refeicoes.size - 1) {
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AddRefeicaoDialog(
                onDismiss = { showDialog = false },
                onConfirm = { tipo, comida, data, calorias ->
                    viewModel.insertRefeicao(tipo, comida, data, calorias)
                    showDialog = false
                }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRefeicaoDialog(onDismiss: () -> Unit, onConfirm: (String, String, Long, Int) -> Unit) {
    var tipo by remember { mutableStateOf("Almoço") }
    var comida by remember { mutableStateOf("") }
    var calorias by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var expanded by remember { mutableStateOf(false) } // 🔹 Estado para abrir/fechar o dropdown

    val mealTypes = listOf("Café da Manhã", "Almoço", "Jantar", "Lanche")

    // 📌 Criando estado do DatePicker
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.add_meal)) },
        text = {
            Column {
                // 📌 MENU DROPDOWN FUNCIONANDO 100%
                Box {
                    TextField(
                        value = tipo,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de Refeição") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.Add, contentDescription = "Expandir")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        mealTypes.forEach { selection ->
                            DropdownMenuItem(
                                text = { Text(selection) },
                                onClick = {
                                    tipo = selection
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 📌 Campo de entrada para comida
                TextField(
                    value = comida,
                    onValueChange = { comida = it },
                    label = { Text("Comida") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 📌 Campo de entrada para calorias
                TextField(
                    value = calorias,
                    onValueChange = { calorias = it.filter { char -> char.isDigit() } },
                    label = { Text("Calorias (kcal)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 📌 Botão para abrir o DatePicker
                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Selecionar Data")
                }

                // 📌 Exibir a data selecionada corretamente
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dataFormatada = sdf.format(Date(selectedDate))
                Text(
                    text = "Data selecionada: $dataFormatada",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val caloriasInt = calorias.toIntOrNull() ?: 0
                onConfirm(tipo, comida, selectedDate, caloriasInt)
            }) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )

    // 📌 DatePicker funcionando corretamente
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
