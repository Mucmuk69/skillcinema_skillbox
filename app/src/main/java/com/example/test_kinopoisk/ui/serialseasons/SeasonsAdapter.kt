package com.example.test_kinopoisk.ui.serialseasons

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.data_serial_seasons.Episode
import com.example.test_kinopoisk.databinding.SeasonsItemBinding

class SeasonsAdapter : ListAdapter<Episode, SeasonsViewHolder>(DiffUtilCallback()) {

    class DiffUtilCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.episodeNumber == newItem.episodeNumber
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {
        val binding = SeasonsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        val item = getItem(position)

        //Функция для скрытия текста описания серии
         fun expandText(textView: TextView) {
            textView.maxLines = Integer.MAX_VALUE
            textView.ellipsize = null
        }

        with(holder.binding) {
            val synopsis = item?.synopsis ?: ""
            val name = item?.nameRu ?: item?.nameEn ?: ""
            episodeNumber.text = "${item.episodeNumber} серия.$name\n $synopsis"
            episodeNumber.setOnClickListener {
                expandText(episodeNumber)
            }
            releaseDate.text = item.releaseDate
        }
    }
}

class SeasonsViewHolder(val binding: SeasonsItemBinding) : RecyclerView.ViewHolder(binding.root)