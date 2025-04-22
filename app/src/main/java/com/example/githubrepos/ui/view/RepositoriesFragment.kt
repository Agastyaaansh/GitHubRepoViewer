package com.example.githubrepos.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepos.R
import com.example.githubrepos.databinding.FragmentRepositoriesBinding
import com.example.githubrepos.ui.adapter.RepositoriesAdapter
import com.example.githubrepos.ui.viewmodel.RepositoriesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {
    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepositoriesViewModel by viewModels()
    private lateinit var adapter: RepositoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()

        // Default load for 'google' repositories
        viewModel.setUsername("google")
    }

    private fun setupRecyclerView() {
        adapter = RepositoriesAdapter()
        binding.repositoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@RepositoriesFragment.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (lastVisibleItemPosition == totalItemCount - 1 && !viewModel.isLoading.value) {
                        viewModel.loadRepositories() // This will now work
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repositories.collect { repos ->
                    if (repos.isEmpty() && !viewModel.isLoading.value) {
                        // Show message if no repositories found
                        showSnackbar("No repositories found for this user")
                    }
                    adapter.submitList(repos.toList())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { error ->
                    error?.let {
                        when {
                            it.contains("404") -> showSnackbar("User not found")
                            it.contains("403") -> showSnackbar("API rate limit exceeded")
                            else -> showSnackbar(it)
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.searchButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            if (username.isNotEmpty()) {
                viewModel.setUsername(username)
            } else {
                showSnackbar("Please enter a username")
            }
        }
    }

    private fun showSnackbar(message: String, retryAction: (() -> Unit)? = null) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).apply {
            retryAction?.let { action ->
                setAction("Retry") { action() }
            }
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
