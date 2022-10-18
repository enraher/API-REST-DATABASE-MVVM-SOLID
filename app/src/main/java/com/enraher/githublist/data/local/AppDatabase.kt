package com.enraher.githublist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enraher.githublist.ui.repos.RepoModels


@Database(entities = [RepoModels.RepoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reposDao(): ReposDao
}