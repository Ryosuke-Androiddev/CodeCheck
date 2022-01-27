package jp.co.yumemi.android.code_check.api

import jp.co.yumemi.android.code_check.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Create Retrofit Instance using object for Singleton pattern
    val githubApi: GithubApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}