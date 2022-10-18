package com.enraher.githublist.ui.repos

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.enraher.githublist.common.Status
import com.enraher.githublist.domain.ReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

interface ReposListViewModelInterface {
    fun getProgressStatus(): LiveData<Status>
    fun getRepos(): LiveData<PagedList<RepoModels.Repo>>
    fun reloadRepos()
}

@HiltViewModel
class ReposListViewModel @Inject constructor(repository: ReposRepository)
    : ViewModel(), ReposListViewModelInterface {

    private val reposDataSourceFactory: ReposDataSourceFactory =
        ReposDataSourceFactory(repository, viewModelScope)
    private val loadStatus: LiveData<Status>
    private val repos: LiveData<PagedList<RepoModels.Repo>>

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()

        repos = LivePagedListBuilder(reposDataSourceFactory, config).build()

        loadStatus = Transformations.switchMap(reposDataSourceFactory.liveData,
            ReposDataSource::getProgressLiveStatus)
    }

    override fun getProgressStatus() = loadStatus
    override fun getRepos() = repos


    override fun reloadRepos(){
        reposDataSourceFactory.reposDataSource.invalidate()
    }
}

class ReposDataSourceFactory(
    private val repository: ReposRepository,
    private val coroutineScope: CoroutineScope
) : DataSource.Factory<Int, RepoModels.Repo>() {

    lateinit var reposDataSource: ReposDataSource
    val liveData = MutableLiveData<ReposDataSource>()

    override fun create(): DataSource<Int, RepoModels.Repo> {
        reposDataSource = ReposDataSource(repository, coroutineScope)
        liveData.postValue(reposDataSource)
        return reposDataSource
    }
}