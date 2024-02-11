package com.example.test_kinopoisk.ui.searching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.entity.data_model_search.film_keyword.Film
import com.example.test_kinopoisk.databinding.MovieItemAllBinding

class SearchFilmKeywordAdapter(
    private val onClick: (Film) -> Unit
) : ListAdapter<Film, MovieViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieItemAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            title.text = item?.nameRu ?: item.nameEn ?: ""
            country.text = item.countries.joinToString(", ") { it.country!! }
            genres.text = item?.genres?.joinToString(", ") { it.genre!! }
            year.text = item.year ?: ""
            duration.visibility = View.GONE
            if (item?.rating != null) {
                rating.text = item.rating.toString()
            } else {
                rating.visibility = View.GONE
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

    class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
            oldItem.filmId == newItem.filmId


        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
            oldItem == newItem
    }
}

class MovieViewHolder(val binding: MovieItemAllBinding) : RecyclerView.ViewHolder(binding.root)