package com.example.test_kinopoisk.ui.staffinfo

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.entity.data_model_movie_info.FilmInfo
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentStaffInfoBinding
import com.example.test_kinopoisk.ui.movieinfo.MovieInfoFragment
import kotlinx.coroutines.launch


class StaffInfoFragment : Fragment() {

    private var _binding: FragmentStaffInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StaffInfoViewModel by activityViewModels()
    private val bestFilmsAdapter = BestFilmsAdapter { film -> onItemClick(film) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameActor = binding.tvName
        val profession = binding.tvProfession
        val poster = binding.ivPoster
        val staffId = arguments?.getInt(ARG_STAFF_ID)

        binding.recyclerStaffInfo.adapter = bestFilmsAdapter

        //Получение информации об актере и показ её на экране
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (staffId != null) {
                    viewModel.getStaffInfo(staffId)
                    Log.d("MyTag", "SIF: staffId -  $staffId")
                }
                viewModel.isLoadingStaffInfo.collect { isLoading ->
                    if (isLoading) {
                        viewModel.listStaffInfo.collect {
                            nameActor.text = it[0]?.nameRu ?: ""
                            profession.text = it[0]?.profession
                            Glide
                                .with(poster.context)
                                .load(it[0]?.posterUrl)
                                .into(poster)
                            //Получение списка лучших фильмов
                            viewModel.getListBestFilms()
                        }
                    }
                }
            }
        }
//Запрос в сеть по каждому лучшему фильму
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingListBestFilms.collect { loadingListBestFilms ->
                    if (loadingListBestFilms) {
                        viewModel.listIdBestFilms.collect { idFilm ->
                            idFilm.forEach {
                                it.filmId?.let { it1 -> viewModel.getBestFilms(it1) }
                            }
                        }
                    }
                }
            }
        }
        //Передача в адаптер списка лучших фильмов актера
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingBestFilms.collect { loadingBestFilms ->
                    if (loadingBestFilms) {
                        viewModel.listBestFilms.collect {
                            bestFilmsAdapter.submitList(it)
                            Log.d("MyTag", "SIF: best film -  $it")
                        }
                    }
                }
            }
        }

        //Фильмография
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingStaffInfo.collect { loadingListFilms ->
                    if (loadingListFilms) {
                        viewModel.listStaffInfo.collect { staffInfo ->
                            binding.tvCountFilms.text = staffInfo[0]?.films?.size.toString()
                        }
                    }
                }
            }
        }


        // Обработчик клика по изображению для отображения в диалоговом окне в полноэкранном режиме
        poster.setOnClickListener {
            val builder = Dialog(requireContext())
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //Можно поставить TRANSPARENT для прозрачного фона
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
            val dialogImageView = ImageView(requireContext())
            // Отображение изображения из ImageView в диалоговом окне
            dialogImageView.setImageDrawable(poster.drawable)
            // Установка параметров ширины и высоты изображения
            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels
            builder.addContentView(
                dialogImageView,
                ViewGroup.LayoutParams(screenWidth, screenHeight)
            )
            builder.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //клик по фильму, переход к инфо о фильме
    private fun onItemClick(item: FilmInfo) {
        val movieId = item.kinopoiskId!!
        val movieInfoFragment = MovieInfoFragment.newInstance(movieId = movieId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_staff_info) {
            findNavController().navigate(
                R.id.action_navigation_staff_info_to_navigation_movie_info,
                movieInfoFragment.arguments
            )
        }
    }


    companion object {
        private const val ARG_STAFF_ID = "staffId"
        fun newInstance(staffId: Int): StaffInfoFragment {
            val fragment = StaffInfoFragment()
            val args = Bundle()
            args.putInt(ARG_STAFF_ID, staffId)
            fragment.arguments = args
            return fragment
        }
    }
}