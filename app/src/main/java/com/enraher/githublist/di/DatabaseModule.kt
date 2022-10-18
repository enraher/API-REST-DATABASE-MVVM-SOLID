package com.enraher.githublist.di

import android.content.Context
import androidx.room.Room
import com.enraher.githublist.data.local.AppDatabase
import com.enraher.githublist.data.local.ReposDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideReposDao(appDatabase: AppDatabase): ReposDao {
        return appDatabase.reposDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "main.db"
        ).build()
    }
}