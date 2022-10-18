package com.enraher.githublist.domain

import com.enraher.githublist.common.Resource
import com.enraher.githublist.ui.repos.RepoModels
import com.enraher.githublist.util.RepoFactory

class FakeReposRepositoryImpl : ReposRepository {

    private val reposList = mutableListOf<RepoModels.Repo>()

    private var shouldReturnNetworkError = false

    init {
        reposList.addAll(RepoFactory.repoList)
    }

    override suspend fun getRepo(githubOwner: String, repoName: String): Resource<RepoModels.Repo> {
        TODO("Not yet implemented")
    }

    override suspend fun getRepos(pageNumber: Int): Resource<List<RepoModels.Repo>> {
        return if(shouldReturnNetworkError) {
            Resource.Error("FakeReposRepositoryImpl: Network error")
        } else {
            Resource.Success(reposList)
        }
    }

    fun getReposCount(): Int {
        return reposList.count()
    }
}