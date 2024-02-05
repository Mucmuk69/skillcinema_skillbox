package com.example.test_kinopoisk.ui.movieinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.data_model_staff.ListStaff
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentFullListStaffBinding
import com.example.test_kinopoisk.ui.staffinfo.StaffInfoFragment
import kotlinx.coroutines.launch

class FullListStaffFragment : Fragment() {

    private var _binding: FragmentFullListStaffBinding? = null
    private val binding get() = _binding!!

    private val sharedVM: SharedStaffViewModel by activityViewModels()

    private val listActorsAdapter = ListStaffAdapter { staffId -> onItemClick(staffId) }
    private val listStaffAdapter = ListStaffAdapter { staffId -> onItemClick(staffId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullListStaffBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.recyclerStaff
        val titleStaff = binding.tvStaff

        when (val receivedStaff = arguments?.getString("staff")) {
            "В фильме снимались" -> {
                recycler.adapter = listActorsAdapter
                titleStaff.text = receivedStaff
                lifecycleScope.launch {
                    sharedVM.actors.collect {
                        listActorsAdapter.submitList(it)
                    }
                }
            }

            "Над фильмом работали" -> {
                recycler.adapter = listStaffAdapter
                titleStaff.text = receivedStaff
                lifecycleScope.launch {
                    sharedVM.staff.collect {
                        listStaffAdapter.submitList(it)
                    }
                }
            }
        }
    }

    //Клик по актеру, переход к инфо об актере
    private fun onItemClick(item: ListStaff) {
        val staffId = item.staffId!!
        val staffInfoFragment = StaffInfoFragment.newInstance(staffId = staffId)
        val currentDestination = findNavController().currentDestination
        if (currentDestination?.id == R.id.navigation_full_list_staff) {
            findNavController().navigate(
                R.id.action_navigation_full_list_staff_to_navigation_staff_info,
                staffInfoFragment.arguments
            )
        }
    }
}