/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.repository.SearchRepository
import jp.co.yumemi.android.code_check.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchResult: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)
    val searchResult: StateFlow<Result> = _searchResult

    private val _loadingState = mutableStateOf(false)
    val loadingState: State<Boolean> = _loadingState


    // Avoid using GlobalScope
    // Flow の例外キャッチを、catch を用いて行う
    fun searchGithubRepository(query: String) = viewModelScope.launch {

        _loadingState.value = true

        Log.d("API Call", "called API")

        _searchResult.value = Result.Loading

        delay(1000L)

        searchRepository.searchGithubRepository(query)
            .catch { e ->
                _searchResult.value = Result.Error(e.toString())
            }.collect {
                    data ->
                _searchResult.value = Result.Success(data)
            }

        _loadingState.value = false

    }

}