package com.enraher.githublist.ui.repos

import androidx.lifecycle.*
import com.enraher.githublist.common.Resource
import com.enraher.githublist.common.Status
import com.enraher.githublist.domain.ReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

interface RepoDetailViewModelInterface {
    fun getProgressStatus(): LiveData<Status>
    fun getRepo(): LiveData<Resource<RepoModels.Repo>>
    fun loadRepo(ownerName: String, repoName: String)
}

@HiltViewModel
class RepoDetailViewModel @Inject constructor(private val repository: ReposRepository)
    : ViewModel(), RepoDetailViewModelInterface {

    private val _loadStatus = MutableLiveData<Status>()
    private val _repos = MutableLiveData<Resource<RepoModels.Repo>>()

    override fun getProgressStatus() = _loadStatus
    override fun getRepo() = _repos

    override fun loadRepo(ownerName: String, repoName: String) {
        _loadStatus.value = Status.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _repos.postValue(repository.getRepo(ownerName, repoName))
        }
    }

}