package br.com.fiap.ecotrack_app.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(navController: NavController) {
    var descricaoState by remember { mutableStateOf("") }
    var listaAlimentosState by remember { mutableStateOf(listOf<Alimento>()) }
    var isLoading by remember { mutableStateOf(false) }

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
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF5783AF))
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF5783AF), Color(0xFFB3E5FC))
                    )
                )
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = descricaoState,
                    onValueChange = { descricaoState = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.food_description),
                            fontFamily = robotoFont
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isLoading = true
                                val call = RetrofitFactory().getAlimentoService().getAlimentos(descricaoState)
                                call.enqueue(object : Callback<List<Alimento>> {
                                    override fun onResponse(call: Call<List<Alimento>>, response: Response<List<Alimento>>) {
                                        listaAlimentosState = response.body() ?: emptyList()
                                        isLoading = false
                                    }

                                    override fun onFailure(call: Call<List<Alimento>>, t: Throwable) {
                                        isLoading = false
                                    }
                                })
                            }
                        ) {
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

                // Exibe indicador de carregamento
                AnimatedVisibility(visible = isLoading) {
                    CircularProgressIndicator(
                        color = Color(0xFF5783AF),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                LazyColumn {
                    items(listaAlimentosState) { alimento ->
                        CardAlimento(alimento = alimento)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))



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
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.food_description_label, alimento.descricao),
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFont,
                color = Color(0xFF5783AF),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id=R.string.food_quantity, alimento.quantidade),
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
