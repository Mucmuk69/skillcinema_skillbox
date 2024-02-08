package com.example.test_kinopoisk.ui.allfilmsincollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentAllFilmsInCollectionBinding
import com.example.test_kinopoisk.ui.loadingcollections.SharedCollectionsViewModel

class AllFilmsInCollectionFragment : Fragment() {

    private var _binding: FragmentAllFilmsInCollectionBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedCollectionsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFilmsInCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (arguments?.getString(ARG_TITLE)) {
            "Премьеры" -> {
                binding.recyclerView.adapter = sharedViewModel.allAdapters.value[0]
                binding.tvTitle.text = getString(R.string.premieres_ru)
            }

            "Топ популярных" -> {
                binding.recyclerView.adapter = sharedViewModel.allAdapters.value[1]
                binding.tvTitle.text = getString(R.string.top_popular_all_ru)
            }

            "Топ 250 фильмов" -> {
                binding.recyclerView.adapter = sharedViewModel.allAdapters.value[2]
                binding.tvTitle.text = getString(R.string.top_250_movies_ru)
            }

            sharedViewModel.countryAndGenre.value -> {
                binding.recyclerView.adapter = sharedViewModel.allAdapters.value[3]
                binding.tvTitle.text = sharedViewModel.countryAndGenre.value
            }

            "Сериалы" -> {
                binding.recyclerView.adapter = sharedViewModel.allAdapters.value[4]
                binding.tvTitle.text = getString(R.string.serials_ru)
            }
        }


    }

    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(title: String?): AllFilmsInCollectionFragment {
            val fragment = AllFilmsInCollectionFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }
}