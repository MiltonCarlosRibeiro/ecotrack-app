package br.com.fiap.ecotrack_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Usuario(
    @PrimaryKey(autoGenerate = false) val email: String, // O email é a chave primária
    val password: String
)
