/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.repository.SearchRepository
import jp.co.yumemi.android.code_check.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchResult: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)
    val searchResult: StateFlow<Result> = _searchResult

    // 検索をするための処理 Ktor Client → Retrofit
    fun searchRepository(query: String) = viewModelScope.launch {

        searchRepository.searchGithubRepository(query)
            .catch { e ->
                _searchResult.value = Result.Error(e.toString())
            }.collect { data ->
                _searchResult.value = Result.Success(data)
            }
    }
}

@Parcelize
data class DetailItem(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable