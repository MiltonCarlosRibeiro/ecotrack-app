package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrack_app.MainActivity
import br.com.fiap.ecotrack_app.R
import br.com.fiap.ecotrack_app.ui.theme.Ecotrack_appTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen_rev01(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(MainActivity.Routes.LOGIN_SCREEN) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5783AF)
                )
            )
        }

    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color(0xFF5783AF))
                .fillMaxSize()
                .padding(16.dp)
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
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.checklist),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Text(
                text = stringResource(id = R.string.welcome_message),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(16.dp),
                letterSpacing = 1.sp,
                lineHeight = 24.sp,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.roboto))
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.did_you_know),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.physical_activity_tip),
                    letterSpacing = 1.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(10.dp)
                )
            }
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.did_you_know),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.sleep_tip),
                    letterSpacing = 1.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(10.dp)
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
                    text = stringResource(id = R.string.take_care),
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.ExtraBold,
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
                Text(
                    text = stringResource(id = R.string.meal_control),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = stringResource(id = R.string.calorie_search),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { navController.navigate(route = MainActivity.Routes.REFEICAO_SCREEN) }) {
                    Text(
                        text = stringResource(id = R.string.my_meals),
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Button(onClick = { navController.navigate(route = MainActivity.Routes.API_SCREEN) }) {
                    Text(
                        text = stringResource(id = R.string.search),
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun IntroScreenPreview() {
    val fakeNavController = rememberNavController()
    Ecotrack_appTheme {
        IntroScreen_rev01(navController = fakeNavController)
    }
}
