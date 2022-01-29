package jp.co.yumemi.android.code_check.model


import android.util.Log
import com.google.gson.annotations.SerializedName
import jp.co.yumemi.android.code_check.DetailItem

data class ApiResult(
    @SerializedName("items")
    val items: List<Item>,
) {

    fun toDetailItemList(): List<DetailItem> {

        val emptyList = mutableListOf<DetailItem>()
        val detailItemList: List<DetailItem> = emptyList

        items.forEach { item ->

            emptyList.add(
                DetailItem(
                    fullName = item.fullName,
                    avatarUrl = item.owner.avatarUrl,
                    language = item.language,
                    stargazersCount = item.stargazersCount.toLong(),
                    watchersCount = item.watchersCount.toLong(),
                    forksCount = item.forksCount.toLong(),
                    openIssuesCount = item.openIssuesCount.toLong()
                )
            )
        }

        Log.d("object change", "called object change")
        return detailItemList
    }
}