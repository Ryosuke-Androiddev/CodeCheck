/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.compose.material.CircularProgressIndicator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.SearchViewModel
import jp.co.yumemi.android.code_check.adapter.ItemListAdapter
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import jp.co.yumemi.android.code_check.repository.SearchRepository
import jp.co.yumemi.android.code_check.ui.fragments.search.component.ShowLoadingView
import jp.co.yumemi.android.code_check.util.Result
import jp.co.yumemi.android.code_check.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchFragment: Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        Log.d("Inflate", "Search Fragment view inflated")

        return binding.root
    }

    // onCreatedView() の戻り値である View の初期化を行う → Data Binding を使わない
    // 引数で与えたレイアウトをinflateされて、戻り値となる
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val repository = SearchRepository()
        val viewModelProviderFactory = SearchViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelProviderFactory)[SearchViewModel::class.java]

        Log.d("ViewModel", "$viewModel created")

        val adapter = ItemListAdapter()
        setupRecyclerView(adapter = adapter)

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->

                // 入力した editText を，検索したい文字列として受け取ることがわかる
                if (action == EditorInfo.IME_ACTION_SEARCH) {

                    // null check 後に、空文字のチェックを通過後に、searchRepository を呼び出す
                    editText.text?.toString()?.let { searchQuery ->
                        if (searchQuery.isNotEmpty()) {
                            viewModel.searchGithubRepository(searchQuery)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        viewLifecycleOwner.lifecycleScope.launch {

            Log.d("Current State", "${lifecycle.currentState}")

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.searchResult.collect { result ->

                    Log.d("Current flow State", "${lifecycle.currentState}")

                    when(result){

                        is Result.Success -> {

                            adapter.submitList(result.data)

                            Log.d("Result Success", "${viewModel.searchResult.value}")
                            Log.d("Result Success", "${result.data}")
                            Log.d("Current Success State", "${lifecycle.currentState}")
                        }
                        is Result.Error -> {
                            Log.d("Result Error", result.message)
                            Log.d("Result Error", "${viewModel.searchResult.value}")
                        }
                        is Result.Idle -> {
                            Log.d("Result Idle", "${viewModel.searchResult.value}")
                        }
                        is Result.Loading -> {

                            binding.composeView.apply {
                                setContent {
                                    ShowLoadingView(viewModel = viewModel)
                                }
                            }
                            Log.d("Result Loading", "Loading State")
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(adapter: ItemListAdapter) {

        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        val dividerItemDecoration=
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Destroy", "Search Fragment view destroyed")
    }
}
