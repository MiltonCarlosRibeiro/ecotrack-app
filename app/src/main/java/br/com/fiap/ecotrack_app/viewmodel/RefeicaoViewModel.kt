package br.com.fiap.ecotrack_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecotrack_app.database.AppDatabase
import br.com.fiap.ecotrack_app.database.repository.RefeicaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.Normalizer

class RefeicaoViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: RefeicaoRepository

    private val _databaseInitialized = MutableStateFlow(false)
    val databaseInitialized: StateFlow<Boolean> = _databaseInitialized

    val allRefeicoes by lazy { repository.allRefeicoes }
    val rankingComidas by lazy { repository.rankingComidas }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val refeicaoDao = AppDatabase.getDatabase(application).refeicaoDao()
            repository = RefeicaoRepository(refeicaoDao)
            _databaseInitialized.value = true // Indica que o banco de dados está pronto
        }
    }

    fun insertRefeicao(tipo: String, comida: String, data: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val comidaSemAcentos = removerAcentos(comida)
            repository.insert(br.com.fiap.ecotrack_app.model.entity.Refeicao(tipo = tipo, comida = comidaSemAcentos, data = data))
        }
    }

    fun updateRefeicao(refeicao: br.com.fiap.ecotrack_app.model.entity.Refeicao) {
        viewModelScope.launch(Dispatchers.IO) {
            val comidaSemAcentos = removerAcentos(refeicao.comida)
            val refeicaoAtualizada = refeicao.copy(comida = comidaSemAcentos)
            repository.update(refeicaoAtualizada)
        }
    }

    fun deleteRefeicao(refeicao: br.com.fiap.ecotrack_app.model.entity.Refeicao) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(refeicao)
        }
    }

    private fun removerAcentos(texto: String): String {
        val textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
        return textoNormalizado.replace(Regex("[\\p{Mn}]"), "")
    }
}