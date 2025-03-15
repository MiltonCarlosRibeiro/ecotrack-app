package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.ecotrack_app.R
import br.com.fiap.ecotrack_app.model.alimento.Alimento
import br.com.fiap.ecotrack_app.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(navController: NavController) {
    var descricaoState by remember { mutableStateOf("") }
    var listaAlimentosState by remember { mutableStateOf(listOf<Alimento>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Utiliza a cor de fundo do tema
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            Text(
                text = "Consulta de Alimentos",
                fontSize = 70.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground, // Utiliza a cor do texto do tema
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontFamily = FontFamily(Font(R.font.stickcream))
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = descricaoState,
                onValueChange = { descricaoState = it },
                label = { Text("Descrição do Alimento") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        val call = RetrofitFactory().getAlimentoService().getAlimentos(descricaoState)
                        call.enqueue(object : Callback<List<Alimento>> {
                            override fun onResponse(call: Call<List<Alimento>>, response: Response<List<Alimento>>) {
                                listaAlimentosState = response.body() ?: emptyList()
                            }

                            override fun onFailure(call: Call<List<Alimento>>, t: Throwable) {
                                // Tratar erro
                            }
                        })
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(listaAlimentosState) { alimento ->
                    CardAlimento(alimento = alimento)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(route = "IntroScreen_rev01")
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary), // Utiliza a cor primária do tema
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "BACK", fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary) // Utiliza a cor do texto no botão do tema
            }
        }
    }
}

@Composable
fun CardAlimento(alimento: Alimento) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Adiciona padding ao card
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Adiciona elevação ao card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Descrição: ${alimento.descricao}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Quantidade: ${alimento.quantidade}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Calorias: ${alimento.calorias}")
        }
    }
}