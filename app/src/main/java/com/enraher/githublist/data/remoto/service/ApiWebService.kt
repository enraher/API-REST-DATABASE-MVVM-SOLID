package com.enraher.githublist.data.remoto.service

import com.enraher.githublist.ui.repos.RepoModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiWebService {

    @GET("orgs/google/repos")
    suspend fun fetchRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20): Response<List<RepoModels.RepoResponse>>

    @GET("repos/{owner_name}/{repo_name}")
    suspend fun fetchRepo(
        @Path("owner_name") ownerName: String,
        @Path("repo_name") repoName: String): Response<RepoModels.RepoResponse>
}