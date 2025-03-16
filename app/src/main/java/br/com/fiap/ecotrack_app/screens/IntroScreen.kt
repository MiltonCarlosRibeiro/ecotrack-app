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
    val robotoFont = FontFamily(Font(R.font.roboto))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.dashboard),
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(MainActivity.Routes.LOGIN_SCREEN) }) {
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
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color(0xFF5783AF))
                .fillMaxSize()
                .padding(16.dp)
        ) {
//            Exercicio2()
            Exercicio8()
            Exercicio22()
            Exercicio7()
            Exercicio10(navController)
        }
    }
}

@Composable
fun Exercicio8() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.corrida),
            contentDescription = stringResource(id = R.string.running_image_desc),
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
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
            Spacer(modifier = Modifier.height(10.dp))
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
