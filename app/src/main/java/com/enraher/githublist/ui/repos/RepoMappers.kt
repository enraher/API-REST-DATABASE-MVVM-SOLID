package com.enraher.githublist.ui.repos

class RepoMappers {
    fun mapFromApi(repoResponse: RepoModels.RepoResponse): RepoModels.Repo {
        return RepoModels.Repo(
            repoResponse.id,
            repoResponse.url,
            repoResponse.name,
            repoResponse.description,
            repoResponse.fork,
            repoResponse.owner.login,
            repoResponse.owner.avatarUrl,
            repoResponse.fullName,
            repoResponse.openIssues,
            repoResponse.forks,
            repoResponse.watchers
        )
    }

    fun mapFromApi(repoResponse: List<RepoModels.RepoResponse>): List<RepoModels.Repo> {
        return repoResponse.map {
            mapFromApi(it)
        }
    }

    fun mapFromDatabase(repoEntities: RepoModels.RepoEntity): RepoModels.Repo {
        return RepoModels.Repo(
            repoEntities.id,
            repoEntities.url,
            repoEntities.name,
            repoEntities.description,
            repoEntities.fork,
            repoEntities.ownerLogin,
            repoEntities.ownerAvatar,
            repoEntities.fullName,
            repoEntities.openIssues,
            repoEntities.forks,
            repoEntities.watchers
        )
    }

    fun mapFromDatabase(repoEntities: List<RepoModels.RepoEntity>): List<RepoModels.Repo> {
        return repoEntities.map {
            mapFromDatabase(it)
        }
    }
}