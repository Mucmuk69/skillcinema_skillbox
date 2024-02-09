package com.example.test_kinopoisk.ui.movieinfo

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.data_model_images.Item
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
            // Обработчик клика по изображению для отображения в диалоговом окне в полноэкранном режиме
            ivMovieImage.setOnClickListener {
                val builder = Dialog(ivMovieImage.context)
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
                //Можно поставить TRANSPARENT для прозрачного фона
                builder.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
                val dialogImageView = ImageView(ivMovieImage.context)
                // Отображение изображения из ImageView в диалоговом окне
                dialogImageView.setImageDrawable(ivMovieImage.drawable)
                // Установка параметров ширины и высоты изображения
                val screenWidth = ivMovieImage.resources.displayMetrics.widthPixels
                val screenHeight = ivMovieImage.resources.displayMetrics.heightPixels
                builder.addContentView(
                    dialogImageView,
                    ViewGroup.LayoutParams(screenWidth, screenHeight)
                )
                builder.show()
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