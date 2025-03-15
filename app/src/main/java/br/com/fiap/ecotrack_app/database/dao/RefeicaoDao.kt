package br.com.fiap.ecotrack_app.database.dao

import androidx.room.*
import br.com.fiap.ecotrack_app.model.entity.Refeicao
import kotlinx.coroutines.flow.Flow

@Dao
interface RefeicaoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refeicao: Refeicao)

    @Update
    suspend fun update(refeicao: Refeicao)

    @Delete
    suspend fun delete(refeicao: Refeicao)

    @Query("SELECT * FROM refeicoes")
    fun getAllRefeicoes(): Flow<List<Refeicao>>

    @Query("SELECT comida, COUNT(*) as quantidade FROM refeicoes GROUP BY comida ORDER BY quantidade DESC LIMIT 3")
    fun getRankingComidas(): Flow<List<RankingComida>>
}

