package com.example.test_kinopoisk.ui.searching

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AdvancedSearchViewModel : ViewModel() {

    var country = MutableStateFlow<String?>(null)
    var genre = MutableStateFlow<String?>(null)

}