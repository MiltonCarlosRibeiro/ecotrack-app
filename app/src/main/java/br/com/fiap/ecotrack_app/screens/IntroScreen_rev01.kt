package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.ecotrack_app.MainActivity
import br.com.fiap.ecotrack_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen_rev01(navController: NavController) {
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text("EcoTrack") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(MainActivity.Routes.LOGIN_SCREEN) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Exercicio2()
            Exercicio8()
            Exercicio22()
            Exercicio7()
            Exercicio10(navController)
        }
    }
}

@Composable
fun Exercicio2() {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.checklist),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Seja bem vindo ao ecotrack!",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(16.dp),
                letterSpacing = 1.sp,
                lineHeight = 24.sp,
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily(Font(R.font.stickcream))
            )
        }
    }
}

@Composable
fun Exercicio22() {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.koala),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Você sabia que a combinação de atividade física regular com uma dieta equilibrada é a chave para uma perda de peso saudável e sustentável? Comece a se mexer e veja os resultados! \uD83C\uDFCB\uFE0F\u200D♀\uFE0F",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(16.dp),
                letterSpacing = 1.sp,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun Exercicio7() {
    Card(modifier = Modifier.padding(8.dp)) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.idea),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp).weight(1f)) {
                Text("Você sabia?")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Beber água regularmente melhora a pele, a digestão e o humor? Mantenha-se hidratado(a) e sinta a diferença \uD83D\uDCA7",
                    modifier = Modifier.align(Alignment.End).padding(10.dp),
                    letterSpacing = 1.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Dormir bem (7-9 horas por noite) fortalece o sistema imunológico, melhora a concentração e te deixa mais feliz? \uD83D\uDE34 Priorize o seu descanso!",
                    modifier = Modifier.align(Alignment.End).padding(10.dp),
                    letterSpacing = 1.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Uma alimentação rica em frutas e vegetais fortalece o corpo e a mente? \uD83C\uDF4E\uD83E\uDD55 Invista em uma dieta equilibrada!",
                    modifier = Modifier.align(Alignment.End).padding(10.dp),
                    letterSpacing = 1.sp,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun Exercicio8() {
    Card(modifier = Modifier.padding(8.dp)) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.gym),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .background(Color.White.copy(alpha = 0.6f))
            ) {
                Text(
                    "Cuide-se!",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun Exercicio10(navController: NavController) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.alimento),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Text("Controle suas Refeições")
                Text("Pesquise Calorias")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { navController.navigate(route = MainActivity.Routes.REFEICAO_SCREEN) }) {
                    Text("Minhas Refeições")
                }
                Button(onClick = { navController.navigate(route = MainActivity.Routes.API_SCREEN) }) {
                    Text("Pesquisar")
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}