package com.example.test_kinopoisk.ui.movieinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.data_images.Item
import com.example.test_kinopoisk.databinding.MovieImageItemBinding

class MovieImagesAdapter : ListAdapter<Item, MovieImagesViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImagesViewHolder {
        val binding =
            MovieImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieImagesViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(ivMovieImage.context)
                    .load(it.imageUrl)
                    .into(ivMovieImage)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }
}

class MovieImagesViewHolder(val binding: MovieImageItemBinding) :
    RecyclerView.ViewHolder(binding.root)