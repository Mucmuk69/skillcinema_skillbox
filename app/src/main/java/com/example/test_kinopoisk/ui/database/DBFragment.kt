package com.example.test_kinopoisk.ui.database

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test_kinopoisk.R
import com.example.test_kinopoisk.databinding.FragmentDBBinding
import com.example.test_kinopoisk.databinding.FragmentHomeBinding

class DBFragment : Fragment() {

    private var _binding: FragmentDBBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDBBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}