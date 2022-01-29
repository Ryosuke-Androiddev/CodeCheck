package jp.co.yumemi.android.code_check.util

import jp.co.yumemi.android.code_check.model.DetailItem


sealed class Result{

    class Success(val data: List<DetailItem>): Result()
    class Error(val message: String): Result()
    object Idle: Result()
}
