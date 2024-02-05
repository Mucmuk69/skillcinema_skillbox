package com.example.test_kinopoisk.ui.loadingcollections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.test_kinopoisk.databinding.MovieItemBinding
import com.example.domain.entity.data_model_movie.Items

class MoviePagedAdapter(
    private val onClick: (Items) -> Unit
) : PagingDataAdapter<Items, MovieViewHolder>(DiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            title.text = item?.nameRu ?: ""
            genres.text = item?.genres?.joinToString(", ") { it.genre!! }
            if (item?.ratingKinopoisk != null) {
                rating.text = item.ratingKinopoisk.toString()
            } else {
                rating.isVisible = false
            }
            item?.let {
                Glide
                    .with(poster.context)
                    .load(it.posterUrl)
                    .transform(RoundedCorners(30))
                    .into(poster)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean =
            oldItem.kinopoiskId == newItem.kinopoiskId


        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean =
            oldItem == newItem
    }
}

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)
