package jp.co.yumemi.android.code_check.repository

import android.util.Log
import jp.co.yumemi.android.code_check.api.RetrofitInstance
import jp.co.yumemi.android.code_check.model.DetailItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepository {

    fun searchGithubRepository(query: String): Flow<List<DetailItem>> = flow<List<DetailItem>> {

        // List<DetailItem>>形式に変換 mapを使って，コードの可読性を向上させる．
        val detailItemList = RetrofitInstance.githubApi.searchGithubRepository(query).items.map { it.toDetailItem() }
        emit(detailItemList)

        Log.d("API Call", "called API")

    }.flowOn(Dispatchers.IO)
}