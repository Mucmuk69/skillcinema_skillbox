package com.example.test_kinopoisk.ui.staffinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.test_kinopoisk.databinding.FragmentFilmographyParentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class FilmographyParentFragment : Fragment() {

    private var _binding: FragmentFilmographyParentBinding? = null
    private val binding get() = _binding!!

    private val sharedStaffInfoVM: SharedStaffInfoViewModel by activityViewModels()

    private lateinit var adapter: FilmographyTabAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabNames: Array<String> = arrayOf(
        "Актер/Актриса",
        "Актер/Актриса дубляжа",
        "Играет саму/самого себя"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmographyParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FilmographyTabAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = adapter
        tabLayout = binding.tabLayout

        lifecycleScope.launch {
            sharedStaffInfoVM.staffInfo.collect { filmInfo ->


                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = filmInfo[0]?.films?.get(position)?.professionKey
                }.attach()
            }
        }
    }
}