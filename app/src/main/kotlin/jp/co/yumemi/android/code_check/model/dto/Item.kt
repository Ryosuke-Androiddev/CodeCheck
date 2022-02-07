package jp.co.yumemi.android.code_check.model.dto


import com.google.gson.annotations.SerializedName
import jp.co.yumemi.android.code_check.model.DetailItem

data class Item(

    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("language")
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
) {
    
    fun toDetailItem(): DetailItem {

        return DetailItem(
            fullName = fullName,
            avatarUrl = owner.avatarUrl,
            language = language,
            stargazersCount = stargazersCount.toLong(),
            watchersCount = watchersCount.toLong(),
            forksCount = forksCount.toLong(),
            openIssuesCount = openIssuesCount.toLong()
        )
    }
}