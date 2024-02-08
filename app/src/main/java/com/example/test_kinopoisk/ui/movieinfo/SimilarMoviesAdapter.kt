package com.example.test_kinopoisk.ui.movieinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.entity.data_model_similar_movies.Item
import com.example.test_kinopoisk.databinding.MovieItemBinding

class SimilarMoviesAdapter(
    private val onClick: (Item) -> Unit
) : ListAdapter<Item, SimilarMoviesAdapter.SimilarViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem.filmId == newItem.filmId


        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem == newItem
    }

    class SimilarViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            title.text = item.nameRu ?: item.nameEn
                    ?: item.nameOriginal
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