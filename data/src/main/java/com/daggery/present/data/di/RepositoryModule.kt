package com.daggery.present.data.di

import com.daggery.present.data.repositories.BreathPatternRepositoryImpl
import com.daggery.present.data.repositories.NotificationItemRepositoryImpl
import com.daggery.present.data.repositories.RoutineItemRepositoryImpl
import com.daggery.present.domain.repositories.BreathPatternRepository
import com.daggery.present.domain.repositories.NotificationItemRepository
import com.daggery.present.domain.repositories.RoutineItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindsBreathPatternRepository(value: BreathPatternRepositoryImpl): BreathPatternRepository

    @Binds
    abstract fun bindsNotificationItemRepository(value: NotificationItemRepositoryImpl): NotificationItemRepository

    @Binds
    abstract fun bindsRoutineItemRepository(value: RoutineItemRepositoryImpl): RoutineItemRepository
}