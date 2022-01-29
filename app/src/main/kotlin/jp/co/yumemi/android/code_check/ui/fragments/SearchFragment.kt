/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.adapter.ItemListAdapter
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import jp.co.yumemi.android.code_check.repository.SearchRepository
import jp.co.yumemi.android.code_check.util.Result
import jp.co.yumemi.android.code_check.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.flow.collect

class SearchFragment: Fragment(R.layout.fragment_search) {

    // onCreatedView() の戻り値である View の初期化を行う → Data Binding を使わない
    // 引数で与えたレイアウトをinflateされて、戻り値となる
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // inflate されたViewをどこで取得する
        val binding = FragmentSearchBinding.bind(view)

        // ViewModel Factory を用いた ViewModelの生成
        val repository = SearchRepository()
        val searchViewModelFactory = SearchViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, searchViewModelFactory).get(SearchViewModel::class.java)

        Log.d("ViewModel", "$viewModel created")

        val layoutManager= LinearLayoutManager(requireContext())

        // RecyclerView における境界線
        val dividerItemDecoration=
            DividerItemDecoration(requireContext(), layoutManager.orientation)

        // 引数をとらないように変更を加える
        val adapter = ItemListAdapter()

        // Input に対する入力を、Enterキーを使って検知している
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->

                // 入力した editText を，検索したい文字列として受け取ることがわかる
                if (action == EditorInfo.IME_ACTION_SEARCH) {

                    // null check 後に、空文字のチェックを通過後に、searchRepository を呼び出す
                    editText.text?.toString()?.let { searchQuery ->
                        if (searchQuery.isNotEmpty()) {

                            viewModel.searchGithubRepository(searchQuery)

                            // 検索結果に応じて、collectする
                            lifecycleScope.launchWhenCreated {

                                Log.d("Query", searchQuery)

                                viewModel.searchResult.collect { result ->

                                    when(result){

                                        is Result.Success -> {
                                            adapter.submitList(result.data)
                                            Log.d("Result Success", "${viewModel.searchResult.value}")
                                            Log.d("Result Success", "${result.data}")
                                        }
                                        is Result.Error -> {
                                            Log.d("Result Error", result.message)
                                            Log.d("Result Error", "${viewModel.searchResult.value}")
                                        }
                                        is Result.Idle -> {
                                            Log.d("Result Idle", "${viewModel.searchResult.value}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also{
            it.layoutManager= layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter= adapter
        }
    }
}
