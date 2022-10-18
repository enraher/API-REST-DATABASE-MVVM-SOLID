package com.enraher.githublist.data.local


import androidx.room.*
import com.enraher.githublist.ui.repos.RepoModels

@Dao
interface ReposDao {
    @Query("SELECT * FROM ${RepoModels.RepoEntity.TABLE_NAME} LIMIT :initialItem, :totalItems")
    suspend fun getRepos(initialItem: Int, totalItems: Int): List<RepoModels.RepoEntity>

    @Query("SELECT * FROM ${RepoModels.RepoEntity.TABLE_NAME} WHERE ${RepoModels.RepoEntity.COLUMN_NAME_OWNER_LOGIN} = :githubOwner AND ${RepoModels.RepoEntity.COLUMN_NAME_DISPLAY_NAME} = :repoName")
    suspend fun getRepo(githubOwner: String, repoName: String): RepoModels.RepoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoModels.RepoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: RepoModels.RepoEntity)

    @Delete
    suspend fun deleteRepos(repos: List<RepoModels.RepoEntity>)

    @Query("DELETE FROM ${RepoModels.RepoEntity.TABLE_NAME} WHERE ${RepoModels.RepoEntity.COLUMN_NAME_ID} = :id")
    suspend fun deleteRepo(id: String)

    @Query("SELECT COUNT(*) FROM ${RepoModels.RepoEntity.TABLE_NAME}")
    suspend fun countAll(): Int

    @Query("DELETE FROM ${RepoModels.RepoEntity.TABLE_NAME}")
    suspend fun clear()
}