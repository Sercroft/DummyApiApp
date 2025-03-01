package com.sercroft.dummyappasd.ui.adapter

import android.annotation.SuppressLint
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
    private val onItemClick: (Post, String?) -> Unit,
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
        private val onItemClick: (Post, String?) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val postImage   : ImageView = view.findViewById(R.id.postImage)
        private val name        : TextView = view.findViewById(R.id.name)
        private val email       : TextView = view.findViewById(R.id.email)
        private val userName    : TextView = view.findViewById(R.id.userName)
        private val phone       : TextView = view.findViewById(R.id.phone)
        private val website     : TextView = view.findViewById(R.id.website)

        @SuppressLint("SetTextI18n")
        fun bind(post: Post, position: Int) {
            name.text       = "Name: ${post.name}"
            email.text      = "Email: ${post.email}"
            userName.text   = "User Name: ${post.userName}"
            phone.text      = "Phone: ${post.phone}"
            website.text    = "Website: ${post.website}"

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
