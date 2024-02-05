package com.example.test_kinopoisk.ui.homescreen

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test_kinopoisk.ui.loadingcollections.MoviePagedAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RefreshData(
    private val lifecycleScope: LifecycleCoroutineScope
) {
    //обновляем адаптеры новой информацией и убираем swipe, если загрузилось
    fun refresh(
        adapter1: MoviePagedAdapter,
        adapter2: MoviePagedAdapter,
        adapter3: MoviePagedAdapter,
        adapter4: MoviePagedAdapter,
        adapter5: MoviePagedAdapter,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        adapter1.refresh()
        adapter2.refresh()
        adapter3.refresh()
        adapter4.refresh()
        adapter5.refresh()

        adapter1.loadStateFlow.onEach {
            swipeRefreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(lifecycleScope)
        adapter2.loadStateFlow.onEach {
            swipeRefreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(lifecycleScope)
        adapter3.loadStateFlow.onEach {
            swipeRefreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(lifecycleScope)
        adapter4.loadStateFlow.onEach {
            swipeRefreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(lifecycleScope)
        adapter5.loadStateFlow.onEach {
            swipeRefreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(lifecycleScope)
    }
}