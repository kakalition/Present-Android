package com.daggery.present.data.di

import android.content.Context
import com.daggery.present.data.db.BreathPatternDao
import com.daggery.present.data.db.IBreathPatternDao
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

}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseInterfaceModule {
    @Binds
    abstract fun bindsBreathPatternDao(dao: BreathPatternDao): IBreathPatternDao
}