package br.com.fiap.ecotrack_app.database.repository

import br.com.fiap.ecotrack_app.database.dao.RefeicaoDao
import br.com.fiap.ecotrack_app.database.dao.RankingComida // Importe RankingComida
import br.com.fiap.ecotrack_app.model.entity.Refeicao
import kotlinx.coroutines.flow.Flow

class RefeicaoRepository(private val refeicaoDao: RefeicaoDao) {
    val allRefeicoes: Flow<List<Refeicao>> = refeicaoDao.getAllRefeicoes()
    val rankingComidas: Flow<List<RankingComida>> = refeicaoDao.getRankingComidas()

    suspend fun insert(refeicao: Refeicao) {
        refeicaoDao.insert(refeicao)
    }

    suspend fun update(refeicao: Refeicao) {
        refeicaoDao.update(refeicao)
    }

    suspend fun delete(refeicao: Refeicao) {
        refeicaoDao.delete(refeicao)
    }
}