package com.mvvm.coroutineexample.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mvvm.coroutineexample.data.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], exportSchema = false, version = 2)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        // Migration from 1 to 2, Room 2.1.0
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE characters ADD COLUMN status TEXT NOT NULL DEFAULT ''")
            }
        }


        fun getDatabase(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext.applicationContext, AppDatabase::class.java, "ricky")
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .build()
    }

}
