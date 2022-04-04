package com.daggery.present.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.daggery.present.data.entities.BreathPatternItemEntity
import com.daggery.present.data.entities.NotificationItemEntity

@Database(entities = [BreathPatternItemEntity::class, NotificationItemEntity::class], version = 1, exportSchema = true)
internal abstract class PresentDatabase : RoomDatabase() {

    abstract fun getBreathPatternDao(): BreathPatternDao

    companion object {
        @Volatile
        private var INSTANCE: PresentDatabase? = null
        fun getDatabase(context: Context): PresentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PresentDatabase::class.java,
                    "present_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}