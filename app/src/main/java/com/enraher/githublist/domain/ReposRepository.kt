package com.enraher.githublist.domain

import com.enraher.githublist.common.Resource
import com.enraher.githublist.ui.repos.RepoModels



interface ReposRepository {
    suspend fun getRepo(githubOwner: String, repoName: String) : Resource<RepoModels.Repo>
    suspend fun getRepos(pageNumber: Int) : Resource<List<RepoModels.Repo>>
}