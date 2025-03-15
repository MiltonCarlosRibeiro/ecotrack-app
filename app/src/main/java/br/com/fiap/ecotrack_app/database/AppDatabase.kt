package br.com.fiap.ecotrack_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.ecotrack_app.database.dao.RefeicaoDao
import br.com.fiap.ecotrack_app.model.entity.Refeicao

@Database(entities = [Refeicao::class], version = 1)
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
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}