package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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

    val robotoFont = FontFamily(Font(R.font.roboto))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.food_query),
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate("IntroScreen_rev01") }) {
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
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE3F2FD))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column {
                OutlinedTextField(
                    value = descricaoState,
                    onValueChange = { descricaoState = it },
                    label = {
                        Text(
                            stringResource(id = R.string.food_description),
                            fontFamily = robotoFont
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = {
                            val call = RetrofitFactory().getAlimentoService().getAlimentos(descricaoState)
                            call.enqueue(object : Callback<List<Alimento>> {
                                override fun onResponse(call: Call<List<Alimento>>, response: Response<List<Alimento>>) {
                                    listaAlimentosState = response.body() ?: emptyList()
                                }

                                override fun onFailure(call: Call<List<Alimento>>, t: Throwable) {
                                }
                            })
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.search),
                                tint = Color(0xFF5783AF)
                            )
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5783AF)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(id = R.string.back),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = robotoFont
                    )
                }
            }
        }
    }
}

@Composable
fun CardAlimento(alimento: Alimento) {
    val robotoFont = FontFamily(Font(R.font.roboto))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.food_description_label, alimento.descricao),
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFont,
                color = Color(0xFF5783AF)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.food_quantity, alimento.quantidade),
                fontFamily = robotoFont,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.food_calories, alimento.calorias),
                fontFamily = robotoFont,
                color = Color.DarkGray
            )
        }
    }
}
