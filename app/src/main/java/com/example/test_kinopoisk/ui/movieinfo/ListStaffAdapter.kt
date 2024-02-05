package com.example.test_kinopoisk.ui.movieinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.entity.data_model_staff.ListStaff
import com.example.test_kinopoisk.databinding.StaffItemBinding

class ListStaffAdapter(
    private val onClick: (ListStaff) -> Unit
) : ListAdapter<ListStaff, ListStaffAdapter.StaffViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback : DiffUtil.ItemCallback<ListStaff>() {
        override fun areItemsTheSame(oldItem: ListStaff, newItem: ListStaff): Boolean =
            oldItem.staffId == newItem.staffId


        override fun areContentsTheSame(oldItem: ListStaff, newItem: ListStaff): Boolean =
            oldItem == newItem
    }

    class StaffViewHolder(val binding: StaffItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding = StaffItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = getItem(position)

        val maxLength = 17
        with(holder.binding){
            tvName.text = if (item.nameRu?.length!! > maxLength){
                item.nameRu?.substring(0,maxLength-3) + "..."
            }else item?.nameRu ?: ""
            tvCharacter.text = item?.description ?: ""
            item?.let {
                Glide
                    .with(ivStaff.context)
                    .load(it.posterUrl)
                    .transform(RoundedCorners(20))
                    .into(ivStaff)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}
