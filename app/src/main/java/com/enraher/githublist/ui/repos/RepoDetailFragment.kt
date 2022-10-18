package com.enraher.githublist.ui.repos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.enraher.githublist.R
import com.enraher.githublist.common.Resource
import com.enraher.githublist.common.Status
import com.enraher.githublist.common.toastLong
import com.enraher.githublist.databinding.FragmentRepoDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoDetailFragment : Fragment(R.layout.fragment_repo_detail) {

    private val reposListViewModel: RepoDetailViewModelInterface by viewModels<RepoDetailViewModel>()

    private lateinit var binding: FragmentRepoDetailBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepoDetailBinding.bind(view)
        setupViewModel()
    }

    private fun setupViewModel() {
          reposListViewModel.getProgressStatus().observe(viewLifecycleOwner) {
            loadingView(it)
        }

        reposListViewModel.getRepo().observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            when(it) {
                is Resource.Success -> {
                    loadingView(Status.Success)
                    binding.repo = it.data
                }
                is Resource.Error -> {
                    loadingView(Status.Error(it.errorMessage))
                }
            }
        }

        val owner = arguments?.getString(OWNER_NAME) ?: ""
        val repoName = arguments?.getString(REPO_NAME) ?: ""
        reposListViewModel.loadRepo(owner, repoName)
    }

    private fun loadingView(show: Status) {
        when(show) {
            Status.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.mainCardView.visibility = View.VISIBLE
            }
            is Status.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.mainCardView.visibility = View.GONE
                requireContext().toastLong(show.errorMessage)
            }
            Status.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.mainCardView.visibility = View.GONE
            }
        }
    }
}