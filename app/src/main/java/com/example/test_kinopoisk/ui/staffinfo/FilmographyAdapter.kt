package com.example.test_kinopoisk.ui.staffinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.data_model_staff.Film
import com.example.test_kinopoisk.databinding.FilmographyItemBinding

class FilmographyAdapter(
    private val onClick: (Film) -> Unit
) : ListAdapter<Film, FilmViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding =
            FilmographyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            nameFilm.text = item.nameRu ?: item.nameEn
            nameFilm.isClickable = true
            nameFilm.isFocusable = true
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }

    }
}

class FilmViewHolder(val binding: FilmographyItemBinding) : RecyclerView.ViewHolder(binding.root)