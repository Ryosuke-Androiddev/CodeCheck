/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.repository.SearchRepository
import jp.co.yumemi.android.code_check.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchResult: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)
    val searchResult: StateFlow<Result> = _searchResult

    // Avoid using GlobalScope
    // Flow の例外キャッチを、catch を用いて行う
    fun searchGithubRepository(query: String) = viewModelScope.launch {

        Log.d("API Call", "called API")
        searchRepository.searchGithubRepository(query)
            .catch { e ->
                _searchResult.value = Result.Error(e.toString())
            }.collect { data ->
                _searchResult.value = Result.Success(data)
            }
    }
}