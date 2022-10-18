package com.enraher.githublist.ui.repos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.enraher.githublist.R
import com.enraher.githublist.common.Navigator
import com.enraher.githublist.common.Status
import com.enraher.githublist.common.toastLong
import com.enraher.githublist.databinding.FragmentReposListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos_list.*
import javax.inject.Inject

const val OWNER_NAME = "ownerName"
const val REPO_NAME = "repoName"

@AndroidEntryPoint
class ReposListFragment : Fragment(R.layout.fragment_repos_list) {

    private val reposListViewModel: ReposListViewModelInterface by viewModels<ReposListViewModel>()

    private lateinit var binding: FragmentReposListBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReposListBinding.bind(view)
        binding.recyclerViewRepos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRepos.adapter = ReposPagingAdapter(
            RepoClickListener {
                val bundle = Bundle()
                bundle.putString(OWNER_NAME, it.ownerLogin)
                bundle.putString(REPO_NAME, it.name)
                navigator.navigate(R.id.navDirectionDetailScreen, bundle)
            })
        setupViewModel()
    }

    private fun setupViewModel() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            reposListViewModel.reloadRepos()
        }

        reposListViewModel.getProgressStatus().observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is Status.Success -> binding.swipeRefreshLayout.isRefreshing = false
                is Status.Error ->  {
                    binding.swipeRefreshLayout.isRefreshing = false
                    requireContext().toastLong(it.errorMessage)
                }
            }
        }

        reposListViewModel.getRepos().observe(viewLifecycleOwner) {
            (recycler_view_repos.adapter as ReposPagingAdapter).submitList(it)
        }
    }
}