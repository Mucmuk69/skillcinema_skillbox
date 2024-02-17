package com.example.test_kinopoisk.ui.searching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.entity.data_model_movie.Items
import com.example.test_kinopoisk.databinding.MovieItemAllBinding

class SearchingAdvancedAdapter(
    private val onClick: (Items) -> Unit
) : ListAdapter<Items, MovieViewHolder>(DiffUtilCallback()) {

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
            year.text = (item.year ?: "").toString()
            duration.visibility = View.GONE
            if (item?.ratingKinopoisk != null) {
                rating.text = item.ratingKinopoisk.toString()
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

    class DiffUtilCallback : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean =
            oldItem.kinopoiskId == newItem.kinopoiskId


        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean =
            oldItem == newItem
    }
}