package jp.co.yumemi.android.code_check.api

import jp.co.yumemi.android.code_check.model.ApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi {

    @Headers(
        "Accept: application/vnd.github.v3+json"
    )
    @GET
    suspend fun searchRepository(@Query("q") query: String): Response<ApiResult>
}