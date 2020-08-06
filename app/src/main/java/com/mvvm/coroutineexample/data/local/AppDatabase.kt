package com.mvvm.coroutineexample.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvvm.coroutineexample.data.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext.applicationContext, AppDatabase::class.java, "ricky")
                .fallbackToDestructiveMigration()
                .build()
    }

}
