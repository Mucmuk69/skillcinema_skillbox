package com.example.test_kinopoisk.ui.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentSearchCountryBinding
import com.example.test_kinopoisk.ui.loadingcollections.SharedCollectionsViewModel
import kotlinx.coroutines.launch

const val ARG_COUNTRY_KEY = "country key"

class SearchCountryFragment : Fragment() {

    private var _binding: FragmentSearchCountryBinding? = null
    private val binding get() = _binding!!

    private val sharedListCountriesAndGenreVM: SharedCollectionsViewModel by activityViewModels()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchViewCountry = binding.svSearchCountry
        val listViewCountries = binding.tvListCountries
        val listCountries = mutableListOf<String>()

        lifecycleScope.launch {
            sharedListCountriesAndGenreVM.listCountriesAndGenres.collect { listCountriesAndGenres ->
                listCountriesAndGenres[0].countries.forEach {
                    it.country?.let { it1 ->
                        listCountries.add(
                            it1
                        )
                        adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            listCountries
                        )
                        listViewCountries.adapter = adapter
                    }
                }
            }
        }

        //Вся область поисковой строки кликабельна
        searchViewCountry.setOnClickListener {
            searchViewCountry.isIconified = false
        }
        searchViewCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        listViewCountries.setOnItemClickListener { _, _, position, _ ->
            val selectedCountry = adapter.getItem(position)
            val fragment = AdvancedSearchFragment()
            val bundle = Bundle()
            bundle.putString(ARG_COUNTRY_KEY, selectedCountry)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_navigation_search_country_to_navigation_advanced_search,
                bundle
            )
        }
    }
}