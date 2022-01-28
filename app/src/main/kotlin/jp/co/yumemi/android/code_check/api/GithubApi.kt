package jp.co.yumemi.android.code_check.api

import jp.co.yumemi.android.code_check.model.ApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi {

    // Response でなく、sealed class に置き換えるから、ApiResultを受け取る
//    @Headers(
//        "Accept=application/vnd.github.v3+json"
//    )
    @GET("repositories?")
    suspend fun searchGithubRepository(@Query("q") query: String): ApiResult
}