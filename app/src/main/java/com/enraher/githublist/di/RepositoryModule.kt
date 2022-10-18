package com.enraher.githublist.di

import com.enraher.githublist.domain.ReposRepository
import com.enraher.githublist.ui.repos.ReposRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindReposRepository(
        myRepositoryImpl: ReposRepositoryImpl
    ): ReposRepository
}