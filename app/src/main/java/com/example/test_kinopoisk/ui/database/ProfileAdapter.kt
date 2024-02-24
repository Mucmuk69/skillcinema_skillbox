package com.example.test_kinopoisk.ui.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.test_kinopoisk.databinding.MovieItemBinding
import com.example.test_kinopoisk.ui.database.model.MovieDatabase

class ProfileAdapter(
    private val onClick: (MovieDatabase) -> Unit
) : ListAdapter<MovieDatabase, ProfileAdapter.ProfileViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<MovieDatabase>() {
        override fun areItemsTheSame(oldItem: MovieDatabase, newItem: MovieDatabase): Boolean =
            oldItem.movieId == newItem.movieId


        override fun areContentsTheSame(oldItem: MovieDatabase, newItem: MovieDatabase): Boolean =
            oldItem == newItem
    }

    class ProfileViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            title.text = item.nameRu ?: item.nameEn
            genres.visibility = View.GONE
            rating.visibility = View.GONE
            item?.let {
                Glide
                    .with(poster.context)
                    .load(it.posterUrl)
                    .transform(RoundedCorners(20))
                    .into(poster)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}