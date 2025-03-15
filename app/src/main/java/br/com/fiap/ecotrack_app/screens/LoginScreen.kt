package br.com.fiap.ecotrack_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack_app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: (String, String) -> Unit, onSkipLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val maxPasswordLength = 8
    val generalPadding = 35.dp
    val cardPadding = 32.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(generalPadding)

    )
    {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 32.sp, // 🔹 Increased font size for better emphasis
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.montserrat)), // 🔹 Applied Montserrat font
            color = Color.White,
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center // 🔹 Centered text for better layout
        )

        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = stringResource(id = R.string.subtitle),
            fontSize = 18.sp, // 🔹 Increased font size for better readability
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.montserrat)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), // 🔹 Added padding for better positioning
            textAlign = TextAlign.Center // 🔹 Centered text
        )

        Spacer(modifier = Modifier.height(28.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), // 🔹 Added horizontal padding for better layout
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp) // 🔹 Applied rounded corners
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(cardPadding)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(id = R.string.email),
                            fontFamily = FontFamily(Font(R.font.montserrat)) // 🔹 Applied Montserrat

                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    isError = emailError
                )
                if (emailError) {
                    Text(
                        text = stringResource(id = R.string.email_required_error),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat)), // 🔹 Applied Montserrat
                        color = Color.Red,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        if (it.length <= maxPasswordLength) {
                            password = it
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = stringResource(id = R.string.password),
                                fontFamily = FontFamily(Font(R.font.montserrat)) // 🔹 Applied Montserrat

                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordError,
                    trailingIcon = { // Adicionado
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            Icon(
                                imageVector = image,
                                contentDescription = stringResource(id = R.string.password_visibility_toggle)
                            )
                        }
                    }
                )
                if (passwordError) {
                    Text(
                        text = stringResource(id = R.string.password_required_error),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat)), // 🔹 Applied Montserrat
                        color = Color.Red,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        emailError = email.isBlank()
                        passwordError = password.isBlank()
                        if (!emailError && !passwordError) {
                            onLoginSuccess(email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(50.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5783AF) // 🔹 Applied a custom button color
                    )

                ) {
                    Text(
                        text = stringResource(id = R.string.enter),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onSkipLogin()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.skip_login),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onLoginSuccess = { _, _ -> },
        onSkipLogin = {}
    )
}
