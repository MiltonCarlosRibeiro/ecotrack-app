package br.com.fiap.ecotrack_app.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.fiap.ecotrack_app.database.dao.RefeicaoDao
import br.com.fiap.ecotrack_app.model.entity.Refeicao
import br.com.fiap.ecotrack_app.database.dao.UsuarioDao
import br.com.fiap.ecotrack_app.model.entity.Usuario

// 🔥 CORREÇÃO: Adicionando a entidade `Usuario` na anotação `@Database`
@Database(entities = [Refeicao::class, Usuario::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun refeicaoDao(): RefeicaoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // 🔹 Adiciona a opção de migração
                    .addMigrations(MIGRATION_1_2) // 🔹 Adiciona a migração
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // 🔥 MIGRAÇÃO PARA ADICIONAR A COLUNA "calorias" NA TABELA `refeicao`
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE refeicao ADD COLUMN calorias INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
