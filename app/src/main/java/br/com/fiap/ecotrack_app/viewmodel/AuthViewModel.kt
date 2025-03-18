package br.com.fiap.ecotrack_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecotrack_app.database.AppDatabase
import br.com.fiap.ecotrack_app.database.dao.UsuarioDao
import br.com.fiap.ecotrack_app.model.entity.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val usuarioDao: UsuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    // 🔹 Função para autenticar o usuário
    fun login(email: String, senha: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = usuarioDao.getUserByEmail(email)
            withContext(Dispatchers.Main) {
                if (user != null && user.password == senha) {
                    onResult(true, null)
                } else {
                    onResult(false, "E-mail ou senha inválidos.")
                }
            }
        }
    }

    // 🔹 Função para registrar um novo usuário
    fun register(email: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = usuarioDao.getUserByEmail(email)
            if (existingUser != null) {
                withContext(Dispatchers.Main) {
                    callback(false, "E-mail já cadastrado.")
                }
            } else {
                usuarioDao.insert(Usuario(email = email, password = password))
                withContext(Dispatchers.Main) {
                    callback(true, "Conta criada com sucesso!")
                }
            }
        }
    }
}
