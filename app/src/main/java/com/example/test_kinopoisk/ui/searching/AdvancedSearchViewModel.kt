package com.example.test_kinopoisk.ui.searching

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AdvancedSearchViewModel : ViewModel() {

    var country = MutableStateFlow<String?>(null)
    var genre = MutableStateFlow<String?>(null)
    var yearFrom = MutableStateFlow<Int?>(null)
    var yearTo = MutableStateFlow<Int?>(null)
    var ratingFrom = MutableStateFlow<Int?>(null)
    var ratingTo = MutableStateFlow<Int?>(null)
    var type = MutableStateFlow<String?>(null)
    var order = MutableStateFlow<String?>(null)
}