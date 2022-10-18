package com.enraher.githublist.ui.repos

import com.enraher.githublist.domain.FakeReposRepositoryImpl
import com.enraher.githublist.util.MainCoroutineRule
import com.enraher.githublist.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enraher.githublist.util.RepoFactory
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReposListViewModelTest {

    private lateinit var viewModel: ReposListViewModel


    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = ReposListViewModel(FakeReposRepositoryImpl())
    }

    @Test
    fun `get repos list, returns true`()  {

        val repos = viewModel.getRepos().getOrAwaitValue()
        assertThat(repos).isEqualTo(RepoFactory.repoList)


    }
}