package jp.co.yumemi.android.code_check.model


import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
)