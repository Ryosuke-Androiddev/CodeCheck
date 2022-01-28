/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.api.RetrofitInstance
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class SearchViewModel() : ViewModel() {

    // 検索をするための処理 Ktor Client → Retrofit
    fun searchRepository(query: String) = viewModelScope.launch {

        // TODO 取得したデータを与える必要がある
        val searchResult = RetrofitInstance.githubApi.searchRepository(query)
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