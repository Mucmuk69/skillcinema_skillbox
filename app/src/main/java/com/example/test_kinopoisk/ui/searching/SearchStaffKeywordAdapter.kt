package com.example.test_kinopoisk.ui.searching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.entity.data_model_search.staff_keyword.Item
import com.example.test_kinopoisk.databinding.StaffItemBinding

class SearchStaffKeywordAdapter(
    private val onClick: (Item) -> Unit
) : ListAdapter<Item, StaffViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding =
            StaffItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvName.text = item.nameRu ?: item.nameEn ?: ""
            tvCharacter.visibility = View.GONE
            item?.let {
                Glide
                    .with(ivStaff.context)
                    .load(it.posterUrl)
                    .transform(RoundedCorners(30))
                    .into(ivStaff)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem.kinopoiskId == newItem.kinopoiskId


        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem == newItem
    }
}

class StaffViewHolder(val binding: StaffItemBinding) : RecyclerView.ViewHolder(binding.root)