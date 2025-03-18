package br.com.fiap.ecotrack_app.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.ecotrack_app.database.AppDatabase
import br.com.fiap.ecotrack_app.model.entity.Usuario
import br.com.fiap.ecotrack_app.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterDialog(
    onDismiss: () -> Unit,
    viewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Criar Conta") },
        text = {
            Column {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (message != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = message!!, color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.register(email, password) { success, msg ->
                        message = msg
                        if (success) onDismiss()
                    }
                }
            }) {
                Text("Registrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
