package br.com.fiap.ecotrack_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecotrack_app.database.AppDatabase
import br.com.fiap.ecotrack_app.database.dao.RankingComida
import br.com.fiap.ecotrack_app.database.repository.RefeicaoRepository
import br.com.fiap.ecotrack_app.model.entity.Refeicao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*

class RefeicaoViewModel(application: Application) : AndroidViewModel(application) {

    // ✅ Inicializando o repository corretamente
    private val refeicaoDao = AppDatabase.getDatabase(application).refeicaoDao()
    private val repository: RefeicaoRepository = RefeicaoRepository(refeicaoDao)

    private val _databaseInitialized = MutableStateFlow(false)
    val databaseInitialized: StateFlow<Boolean> = _databaseInitialized

    val allRefeicoes: StateFlow<List<Refeicao>> = repository.allRefeicoes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val rankingComidas: StateFlow<List<RankingComida>> = repository.rankingComidas.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _totalCalorias = MutableStateFlow(0)
    val totalCalorias: StateFlow<Int> = _totalCalorias

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _databaseInitialized.value = true
            repository.allRefeicoes.collect { refeicoes ->
                _totalCalorias.value = refeicoes.sumOf { it.calorias }
            }
        }
    }

    fun insertRefeicao(tipo: String, comida: String, data: Long, calorias: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val comidaSemAcentos = removerAcentos(comida)
            repository.insert(
                Refeicao(
                    tipo = tipo,
                    comida = comidaSemAcentos,
                    data = data,
                    calorias = calorias
                )
            )
            _totalCalorias.value += calorias
        }
    }

    fun updateRefeicao(refeicao: Refeicao) {
        viewModelScope.launch(Dispatchers.IO) {
            val comidaSemAcentos = removerAcentos(refeicao.comida)
            repository.update(refeicao.copy(comida = comidaSemAcentos))
        }
    }

    fun deleteRefeicao(refeicao: Refeicao) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(refeicao)
            _totalCalorias.value -= refeicao.calorias
        }
    }

    private fun removerAcentos(texto: String): String {
        val textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
        return textoNormalizado.replace(Regex("[\\p{Mn}]"), "")
    }

    fun getCaloriasPorDia(): Flow<Map<String, Int>> {
        return allRefeicoes.map { refeicoes ->
            refeicoes.groupBy { SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date(it.data)) }
                .mapValues { entry -> entry.value.sumOf { it.calorias } }
        }
    }

    fun getCaloriasPorSemana(): Flow<Map<String, Int>> {
        return allRefeicoes.map { refeicoes ->
            refeicoes.filter {
                val dataRefeicao = Calendar.getInstance().apply { timeInMillis = it.data }
                val dataAtual = Calendar.getInstance()
                val semanaAtual = dataAtual.get(Calendar.WEEK_OF_YEAR)
                dataRefeicao.get(Calendar.WEEK_OF_YEAR) == semanaAtual
            }.groupBy { SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date(it.data)) }
                .mapValues { entry -> entry.value.sumOf { it.calorias } }
        }
    }

    fun getCaloriasPorMes(): Flow<Map<String, Int>> {
        return allRefeicoes.map { refeicoes ->
            refeicoes.filter {
                val dataRefeicao = Calendar.getInstance().apply { timeInMillis = it.data }
                val dataAtual = Calendar.getInstance()
                val mesAtual = dataAtual.get(Calendar.MONTH)
                dataRefeicao.get(Calendar.MONTH) == mesAtual
            }.groupBy { SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date(it.data)) }
                .mapValues { entry -> entry.value.sumOf { it.calorias } }
        }
    }
}
