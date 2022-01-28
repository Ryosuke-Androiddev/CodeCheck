package jp.co.yumemi.android.code_check.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.co.yumemi.android.code_check.SearchViewModel
import jp.co.yumemi.android.code_check.repository.SearchRepository

class SearchViewModelFactory(
    private val repository: SearchRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {

            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Please check ViewModel class")
    }
}