package com.daggery.present.data.di

import android.content.Context
import com.daggery.present.data.db.*
import com.daggery.present.data.db.BreathPatternDao
import com.daggery.present.data.db.IBreathPatternDao
import com.daggery.present.data.db.INotificationItemDao
import com.daggery.present.data.db.NotificationItemDao
import com.daggery.present.data.db.PresentDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context): PresentDatabase {
        return PresentDatabase.getDatabase(appContext)
    }

    @Provides
    fun providesBreathPatternDao(db: PresentDatabase): BreathPatternDao {
        return db.getBreathPatternDao()
    }

    @Provides
    fun providesNotificationItemDao(db: PresentDatabase): NotificationItemDao {
        return db.getNotificationItemDao()
    }

    @Provides
    fun providesRoutineItemDao(db: PresentDatabase): RoutineItemDao {
        return db.getRoutineItemDao()
    }

}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseInterfaceModule {
    @Binds
    abstract fun bindsBreathPatternDao(dao: BreathPatternDao): IBreathPatternDao

    @Binds
    abstract fun bindsNotificationItemDao(dao: NotificationItemDao): INotificationItemDao

    @Binds
    abstract fun bindsRoutineItemDao(dao: RoutineItemDao): IRoutineItemDao
}