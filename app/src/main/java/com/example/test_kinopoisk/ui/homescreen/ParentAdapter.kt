package com.example.test_kinopoisk.ui.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentLoadingCollectionsBinding
import com.example.test_kinopoisk.ui.allfilmsincollection.AllFilmsInCollectionFragment
import com.example.test_kinopoisk.ui.loadingcollections.MoviePagedAdapter

class ParentAdapter :
    ListAdapter<MoviePagedAdapter, ParentAdapter.ParentHolder>(DiffUtilCallback()) {

    private var countryAndGenre: String? = ""
    private var itemCountPremieres: Int? = null
    private var itemCountDynamic: Int? = null
    private var itemCountSerials: Int? = null
    private var itemCountTopPopularAll: Int? = null
    private var itemCountTop250Films: Int? = null
    fun setCountryAndGenre(countryAndGenre: String?) {
        this.countryAndGenre = countryAndGenre
    }

    fun setCountFilms(
        itemCountPremieres: Int?,
        itemCountTopPopularAll: Int?,
        itemCountTop250Films: Int?,
        itemCountDynamic: Int?,
        itemCountSerials: Int?
    ) {
        this.itemCountPremieres = itemCountPremieres
        this.itemCountTopPopularAll = itemCountTopPopularAll
        this.itemCountTop250Films = itemCountTop250Films
        this.itemCountDynamic = itemCountDynamic
        this.itemCountSerials = itemCountSerials
    }

    class ParentHolder(val binding: FragmentLoadingCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentHolder {
        val binding =
            FragmentLoadingCollectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentHolder, position: Int) {

        //Кнопка "ВСЕ" - показывает весь список подборки фильмов на отдельном экране
        fun countCollections(itemCount: Int?, view: TextView, title: String?) {
            val allFilmsFragment = AllFilmsInCollectionFragment.newInstance(title)
            if (itemCount != null) {
                if (itemCount >= 20) {
                    view.text = "$itemCount"
                    view.visibility = View.VISIBLE
                    view.isClickable = true
                    view.isFocusable = true
                    view.setOnClickListener {
                        val navController = it.findNavController()
                        navController.navigate(
                            R.id.action_navigation_home_to_navigation_all_film,
                            allFilmsFragment.arguments
                        )
                    }
                }
            }
        }

        val listTitles = listOf(
            "Премьеры",
            "Топ популярных",
            "Топ 250 фильмов",
            countryAndGenre,
            "Сериалы"
        )
        val listCountFilms = listOf(
            itemCountPremieres,
            itemCountTopPopularAll,
            itemCountTop250Films,
            itemCountDynamic,
            itemCountSerials
        )

        val item = getItem(position)
        with(holder.binding) {

            recyclerParent.adapter = item
            titleParent.text = listTitles[position]
            countCollections(listCountFilms[position], buttonAllFilms, listTitles[position])
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<MoviePagedAdapter>() {
        override fun areItemsTheSame(
            oldItem: MoviePagedAdapter,
            newItem: MoviePagedAdapter
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MoviePagedAdapter,
            newItem: MoviePagedAdapter
        ): Boolean {
            return oldItem == newItem
        }
    }
}


