package jp.co.yumemi.android.code_check.model.dto


import android.util.Log
import com.google.gson.annotations.SerializedName
import jp.co.yumemi.android.code_check.model.DetailItem

data class ApiResult(
    @SerializedName("items")
    val items: List<Item>,
)