package br.com.fiap.ecotrack_app.database.repository

import br.com.fiap.ecotrack_app.database.dao.UsuarioDao
import br.com.fiap.ecotrack_app.model.entity.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    // Inserir um novo usuário no banco de dados
    suspend fun insert(usuario: Usuario) {
        usuarioDao.insert(usuario) // 🔹 Nome do objeto corrigido
    }

    // Buscar usuário pelo e-mail
    suspend fun getUserByEmail(email: String): Usuario? {
        return usuarioDao.getUserByEmail(email)
    }
}
