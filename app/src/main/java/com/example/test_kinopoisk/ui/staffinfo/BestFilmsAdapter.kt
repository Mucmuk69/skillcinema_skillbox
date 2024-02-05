package com.example.test_kinopoisk.ui.staffinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.data_model_movie_info.FilmInfo
import com.example.test_kinopoisk.databinding.MovieItemBinding


class BestFilmsAdapter(
    private val onClick: (FilmInfo) -> Unit
) : ListAdapter<FilmInfo, MovieViewHolder>(DiffUtilCallback()){

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
                    .into(poster)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<FilmInfo>() {
        override fun areItemsTheSame(oldItem: FilmInfo, newItem: FilmInfo): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: FilmInfo, newItem: FilmInfo): Boolean {
            return oldItem == newItem
        }

    }
}

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)