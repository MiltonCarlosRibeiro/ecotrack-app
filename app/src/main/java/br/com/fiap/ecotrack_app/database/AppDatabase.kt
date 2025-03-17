package br.com.fiap.ecotrack_app.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.fiap.ecotrack_app.database.dao.RefeicaoDao
import br.com.fiap.ecotrack_app.model.entity.Refeicao

@Database(entities = [Refeicao::class], version = 2) // Aumentamos a versão para 2
abstract class AppDatabase : RoomDatabase() {
    abstract fun refeicaoDao(): RefeicaoDao

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
                    .addMigrations(MIGRATION_1_2) // Adiciona a migração
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // 🔥 MIGRAÇÃO PARA ADICIONAR A COLUNA "calorias"
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE refeicao ADD COLUMN calorias INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
