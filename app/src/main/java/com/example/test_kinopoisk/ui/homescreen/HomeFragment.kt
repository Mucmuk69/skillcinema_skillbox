package com.example.test_kinopoisk.ui.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentHomeBinding
import com.example.test_kinopoisk.ui.loadingcollections.SharedCollectionsViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()
    private val sharedViewModel: SharedCollectionsViewModel by activityViewModels()
    private val parentAdapter = ParentAdapter()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //При нажатии на значки навигации внизу экрана
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_searching -> findNavController().navigate(R.id.navigation_searching)
            }
            true
        }

        //Кнопка назад - свернуть приложение
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.moveTaskToBack(true)
        }

        binding.recyclerHome.adapter = parentAdapter

        //Передача в адаптер других адаптеров и страны и жанра для названия динамической подборки
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentAdapter.submitList(sharedViewModel.allAdapters.value)
                parentAdapter.setCountryAndGenre(sharedViewModel.countryAndGenre.value)
                forUnlockButtonAll()
                parentAdapter.notifyDataSetChanged()
            }
        }

        val refreshData = RefreshData(lifecycleScope)

        //Обновить подборки по свайпу вниз
        swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            refreshData.refresh(
                adapter1 = sharedViewModel.allAdapters.value[0],
                adapter2 = sharedViewModel.allAdapters.value[1],
                adapter3 = sharedViewModel.allAdapters.value[2],
                adapter4 = sharedViewModel.allAdapters.value[3],
                adapter5 = sharedViewModel.allAdapters.value[4],
                swipeRefreshLayout = swipeRefreshLayout
            )
            refreshData()
        }
    }

    //Передача количества фильмов в подборке в адаптер для разблокировки кнопки "ВСЕ"
    private fun forUnlockButtonAll() {
        val premieresCount = arguments?.getInt(ARG_PREMIERES_COUNT, 0)
        val popularCount = arguments?.getInt(ARG_POPULAR_COUNT, 0)
        val top250Count = arguments?.getInt(ARG_TOP250_COUNT, 0)
        val dynamicCount = arguments?.getInt(ARG_DYNAMIC_COUNT, 0)
        val serialsCount = arguments?.getInt(ARG_SERIALS_COUNT, 0)
        Log.d("MyTag", "HF: premieres count: $premieresCount")
        parentAdapter.setCountFilms(
            premieresCount,
            popularCount,
            top250Count,
            dynamicCount,
            serialsCount
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //обновление подборок в адаптере
    private fun refreshData() {
        lifecycleScope.launch {
            findNavController().navigate(
                R.id.action_navigation_home_to_navigation_dashboard
            )
            swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {
        const val ARG_PREMIERES_COUNT = "premieres"
        const val ARG_POPULAR_COUNT = "popular"
        const val ARG_TOP250_COUNT = "top250"
        const val ARG_DYNAMIC_COUNT = "dynamic"
        const val ARG_SERIALS_COUNT = "serials"
    }
}