package com.example.test_kinopoisk.ui.loadingcollections

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.entity.data_model_movie.Items
import com.example.domain.usecase.DynamicCompilationUseCase
import com.example.domain.usecase.PremieresUseCase
import com.example.domain.usecase.TopPopularAllUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//Пагинация премьер
class MoviePagingSource(
    private val year: Int,
    private val month: String,
    private val useCase: PremieresUseCase
) : PagingSource<Int, Items>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        val page = params.key ?: FIRST_PAGE
        return runCatching {
            useCase.execute(year, month)
        }.fold(
            onSuccess = {
                val result = LoadResult.Page(
                    data = it.items,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (it.items.isNotEmpty()) page + 1 else null
                )
                //Получаем общее количество фильмов в подборке
                _countTotalPremieres.value = it.total
                Log.d("MyTag", "Paging source: total premieres: ${countTotalPremieres.value}")
                _isLoadingPremieres.value = true
                Log.d("MyTag", "Paging source: premieres: ${isLoadingPremieres.value}")
                result
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private var _isLoadingPremieres = MutableStateFlow(false)
        val isLoadingPremieres = _isLoadingPremieres.asStateFlow()
        private var _countTotalPremieres = MutableStateFlow<Int?>(null)
        val countTotalPremieres = _countTotalPremieres.asStateFlow()
    }
}

//Пагинация подборки популярных фильмов
class MoviePagingSourceTopPopular(
    private val type: String,
    private val useCase: TopPopularAllUseCase
) : PagingSource<Int, Items>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        val page = params.key ?: FIRST_PAGE
        return runCatching {
            _isLoadingTopPopular.value = false
            useCase.execute(type, page)
        }.fold(
            onSuccess = {
                val result = LoadResult.Page(
                    data = it.items,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (it.items.isNotEmpty()) page + 1 else null
                )
                _countTotalTopPopular.value = it.total
                _isLoadingTopPopular.value = true
                result
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private var _isLoadingTopPopular = MutableStateFlow(false)
        val isLoadingTopPopular = _isLoadingTopPopular.asStateFlow()
        private var _countTotalTopPopular = MutableStateFlow<Int?>(null)
        val countTotalTopPopular = _countTotalTopPopular.asStateFlow()
    }
}

//Пагинация подборки топ 250 фильмов
class MoviePagingSourceTop250(
    private val type: String,
    private val useCase: TopPopularAllUseCase
) : PagingSource<Int, Items>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        val page = params.key ?: FIRST_PAGE
        return runCatching {
            _isLoadingTop250.value = false
            useCase.execute(type, page)
        }.fold(
            onSuccess = {
                val result = LoadResult.Page(
                    data = it.items,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (it.items.isNotEmpty()) page + 1 else null
                )
                _countTotalTop250.value = it.total
                _isLoadingTop250.value = true
                result
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private var _isLoadingTop250 = MutableStateFlow(false)
        val isLoadingTop250 = _isLoadingTop250.asStateFlow()
        private var _countTotalTop250 = MutableStateFlow<Int?>(null)
        val countTotalTop250 = _countTotalTop250.asStateFlow()
    }
}

//Пагинация динамической подборки фильмов
class MoviePagingSourceDynamic(
    private val countries: List<Int>,
    private val genres: List<Int>,
    private val order: String,
    private val type: String,
    private val ratingFrom: Int,
    private val ratingTo: Int,
    private val yearFrom: Int,
    private val yearTo: Int,
    private val useCase: DynamicCompilationUseCase
) : PagingSource<Int, Items>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        val page = params.key ?: FIRST_PAGE
        return runCatching {
            _isLoadingDynamic.value = false
            useCase.execute(
                countries,
                genres,
                order,
                type,
                ratingFrom,
                ratingTo,
                yearFrom,
                yearTo,
                page
            )
        }.fold(
            onSuccess = {
                val result = LoadResult.Page(
                    data = it.items,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (it.items.isNotEmpty()) page + 1 else null
                )
                _countTotalDynamic.value = it.total
                _isLoadingDynamic.value = true
                result
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private var _isLoadingDynamic = MutableStateFlow(false)
        val isLoadingDynamic = _isLoadingDynamic.asStateFlow()
        private var _countTotalDynamic = MutableStateFlow<Int?>(null)
        val countTotalDynamic = _countTotalDynamic.asStateFlow()
    }
}

//Пагинация подборки с сериалами
class MoviePagingSourceDynamicSerials(
    private val countries: List<Int>,
    private val genres: List<Int>,
    private val order: String,
    private val type: String,
    private val ratingFrom: Int,
    private val ratingTo: Int,
    private val yearFrom: Int,
    private val yearTo: Int,
    private val useCase: DynamicCompilationUseCase
) : PagingSource<Int, Items>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        val page = params.key ?: FIRST_PAGE
        return runCatching {
            _isLoadingDynamicSerials.value = false
            useCase.execute(
                countries,
                genres,
                order,
                type,
                ratingFrom,
                ratingTo,
                yearFrom,
                yearTo,
                page
            )
        }.fold(
            onSuccess = {
                val result = LoadResult.Page(
                    data = it.items,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (it.items.isNotEmpty()) page + 1 else null
                )
                _countTotalDynamicSerials.value = it.total
                _isLoadingDynamicSerials.value = true
                result
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private var _isLoadingDynamicSerials = MutableStateFlow(false)
        val isLoadingDynamicSerials = _isLoadingDynamicSerials.asStateFlow()
        private var _countTotalDynamicSerials = MutableStateFlow<Int?>(null)
        val countTotalDynamicSerials = _countTotalDynamicSerials.asStateFlow()
    }
}