package com.sercroft.dummyappasd.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sercroft.dummyappasd.R
import com.sercroft.dummyappasd.data.service.RandomDogApiService
import com.sercroft.dummyappasd.ui.adapter.PostAdapter
import com.sercroft.dummyappasd.ui.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val postViewModel: PostViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter

    @Inject
    lateinit var randomDogApiService: RandomDogApiService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView    = view.findViewById<RecyclerView>(R.id.recyclerView)
        val searchBar       = view.findViewById<EditText>(R.id.searchBar)
        val progressBar     = view.findViewById<ProgressBar>(R.id.progressBar)

        postAdapter = PostAdapter({ post, imageUrl ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                postId      = post.id,
                postTitle   = post.title,
                postUrl     = imageUrl ?: post.url
            )
            findNavController().navigate(action)
        }, randomDogApiService)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = postAdapter

        initObservers(progressBar, recyclerView)

        searchBar.addTextChangedListener {
            postViewModel.filterPosts(it.toString())
        }

        postViewModel.fetchPosts()
    }

    private fun initObservers(progressBar: ProgressBar, recyclerView: RecyclerView) {
        postViewModel.filteredPosts.observe(viewLifecycleOwner) { posts ->
            postAdapter.submitList(posts)
        }

        postViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility  = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }
}