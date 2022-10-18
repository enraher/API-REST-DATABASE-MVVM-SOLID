package com.enraher.githublist.ui.repos

import com.enraher.githublist.common.Resource
import com.enraher.githublist.data.local.ReposDao
import com.enraher.githublist.data.remoto.service.ApiWebService
import com.enraher.githublist.domain.ReposRepository
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor (private val apiWebService: ApiWebService,
                                               private val reposDao: ReposDao
) : ReposRepository {

    override suspend fun getRepos(pageNumber: Int) : Resource<List<RepoModels.Repo>> {
        val savedRepos = reposDao.getRepos(((pageNumber - 1) * PAGE_SIZE), PAGE_SIZE)

        //Get repos from cache if less than 30 mins
        if (savedRepos.isNotEmpty()) {
            val refreshTimePass = 30 * 60 * 1000
            val firstRepoSaved = savedRepos.minBy { it.storedAt }
            if (System.currentTimeMillis() - firstRepoSaved.storedAt < refreshTimePass) {
                return Resource.Success(RepoMappers().mapFromDatabase(savedRepos))
            } else {
                reposDao.deleteRepos(savedRepos)
            }
        }

        try {
            val apiResult = apiWebService.fetchRepos(pageNumber, PAGE_SIZE)
            return if (apiResult.isSuccessful) {
                apiResult.body()?.let { reposResultResponse ->
                    reposDao.insertAll(reposResultResponse.map {
                        RepoModels.RepoEntity(
                            it.id,
                            it.url,
                            it.name,
                            it.description,
                            it.fork,
                            it.owner.login,
                            it.owner.avatarUrl,
                            it.fullName,
                            it.openIssues,
                            it.forks,
                            it.watchers,
                            System.currentTimeMillis()
                        )
                    })
                    val repos = RepoMappers().mapFromApi(reposResultResponse)
                    Resource.Success(repos)
                } ?: kotlin.run {
                    Resource.Error("No body response")
                }
            } else {
                Resource.Error(Resource.Error.getError(apiResult))
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getRepo(githubOwner: String,
                                 repoName: String) : Resource<RepoModels.Repo> {


        val savedRepo = reposDao.getRepo(githubOwner, repoName)

        //Get repo from cache if less than 30 mins
        if (savedRepo != null) {
            val refreshTimePass = 30 * 60 * 1000
            if (System.currentTimeMillis() - savedRepo.storedAt < refreshTimePass) {
                return Resource.Success(RepoMappers().mapFromDatabase(savedRepo))
            } else {
                reposDao.deleteRepo(savedRepo.id)
            }
        }

        try {
            val apiResult = apiWebService.fetchRepo(githubOwner, repoName)
            return if (apiResult.isSuccessful) {
                apiResult.body()?.let { reposResultResponse ->
                    reposDao.insert(
                        RepoModels.RepoEntity(
                            reposResultResponse.id,
                            reposResultResponse.url,
                            reposResultResponse.name,
                            reposResultResponse.description,
                            reposResultResponse.fork,
                            reposResultResponse.owner.login,
                            reposResultResponse.owner.avatarUrl,
                            reposResultResponse.fullName,
                            reposResultResponse.openIssues,
                            reposResultResponse.forks,
                            reposResultResponse.watchers,
                            System.currentTimeMillis()
                        )
                    )
                    val repo = RepoMappers().mapFromApi(reposResultResponse)
                    Resource.Success(repo)
                } ?: kotlin.run {
                    Resource.Error("No body response")
                }
            } else {
                Resource.Error(Resource.Error.getError(apiResult))
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error")
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}