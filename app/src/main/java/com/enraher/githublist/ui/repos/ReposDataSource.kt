package com.enraher.githublist.ui.repos

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.enraher.githublist.common.Resource
import com.enraher.githublist.common.Status
import com.enraher.githublist.domain.ReposRepository
import kotlinx.coroutines.*


class ReposDataSource(
    private val repository: ReposRepository,
    private val scope: CoroutineScope) : PageKeyedDataSource<Int, RepoModels.Repo>() {

    private val progressLiveStatus = MutableLiveData<Status>()

    fun getProgressLiveStatus() = progressLiveStatus


    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, RepoModels.Repo>) {
        scope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                progressLiveStatus.postValue(Status.Loading)
                when (val dataResult = repository.getRepos(1)) {
                    is Resource.Success -> {
                        progressLiveStatus.postValue(Status.Success)
                        callback.onResult(dataResult.data, null, 2)
                    }
                    is Resource.Error ->
                        progressLiveStatus.postValue(Status.Error(dataResult.errorMessage))
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RepoModels.Repo>) {
        scope.launch(Dispatchers.IO) {
            progressLiveStatus.postValue(Status.Loading)
            when (val dataResult = repository.getRepos(params.key)) {
                is Resource.Success -> {
                    progressLiveStatus.postValue(Status.Success)
                    callback.onResult(dataResult.data, params.key + 1)
                }
                is Resource.Error ->
                    progressLiveStatus.postValue(Status.Error(dataResult.errorMessage))
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RepoModels.Repo>) {

    }
}

