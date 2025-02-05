package com.sercroft.dummyappasd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sercroft.dummyappasd.R
import com.sercroft.dummyappasd.data.model.Post
import com.sercroft.dummyappasd.data.service.RandomDogApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostAdapter(
    private val onItemClick: (Post, String?) -> Unit, // Modificación aquí
    private val randomDogApiService: RandomDogApiService
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    private val dogImageUrls = mutableListOf<String?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view, randomDogApiService, onItemClick)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post, position)
    }

    inner class PostViewHolder(
        view: View,
        private val randomDogApiService: RandomDogApiService,
        private val onItemClick: (Post, String?) -> Unit // Modificación aquí
    ) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val postImage: ImageView = view.findViewById(R.id.postImage)

        fun bind(post: Post, position: Int) {
            title.text = post.title

            // Save images in cache
            val cachedImageUrl = dogImageUrls.getOrNull(position)

            if (cachedImageUrl != null) {
                Glide.with(itemView)
                    .load(cachedImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(postImage)

                itemView.setOnClickListener {
                    onItemClick(post, cachedImageUrl)
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = randomDogApiService.getRandomDog()
                        dogImageUrls.add(response.url)
                        withContext(Dispatchers.Main) {
                            Glide.with(itemView)
                                .load(response.url)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_foreground)
                                .into(postImage)

                            itemView.setOnClickListener {
                                onItemClick(post, response.url)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
