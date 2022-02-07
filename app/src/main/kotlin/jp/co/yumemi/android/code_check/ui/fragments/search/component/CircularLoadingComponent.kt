package jp.co.yumemi.android.code_check.ui.fragments.search.component

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import jp.co.yumemi.android.code_check.SearchViewModel

@Composable
fun ShowLoadingView(
    viewModel: SearchViewModel
) {

    val loadingState = viewModel.loadingState.value

    if (loadingState) {
        CircularProgressIndicator()
    }
}