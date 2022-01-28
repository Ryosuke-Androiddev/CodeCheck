package jp.co.yumemi.android.code_check.repository

import android.util.Log
import jp.co.yumemi.android.code_check.DetailItem
import jp.co.yumemi.android.code_check.api.RetrofitInstance
import jp.co.yumemi.android.code_check.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException

class SearchRepository {

    fun searchGithubRepository(query: String): Flow<List<DetailItem>> = flow<List<DetailItem>> {

        // List<DetailItem>>形式に変換
        val detailItemList = RetrofitInstance.githubApi.searchGithubRepository(query).toDetailItemList()
        emit(detailItemList)

        Log.d("API Call", "called API")

    }.flowOn(Dispatchers.IO)
}