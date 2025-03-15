package br.com.fiap.ecotrack_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refeicoes")
data class Refeicao(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tipo: String, // "Almoço" ou "Jantar"
    val comida: String,
    val data: Long // Adicionando o campo de data
)