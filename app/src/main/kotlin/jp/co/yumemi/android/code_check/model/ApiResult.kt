package jp.co.yumemi.android.code_check.model


import com.google.gson.annotations.SerializedName

data class ApiResult(
    @SerializedName("items")
    val items: List<Item>,
)