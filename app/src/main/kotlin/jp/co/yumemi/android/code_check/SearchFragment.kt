/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding

class SearchFragment: Fragment(R.layout.fragment_search){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val binding= FragmentSearchBinding.bind(view)

        val viewModel= SearchViewModel(context!!)

        val layoutManager= LinearLayoutManager(context!!)

        val dividerItemDecoration=
            DividerItemDecoration(context!!, layoutManager.orientation)

        val adapter= CustomAdapter(object : CustomAdapter.OnItemClickListener{

            // TODO this viewModel fun is comment out now
//            override fun itemClick(DetailItem: DetailItem){
//                navigateToDetailWithArgs(DetailItem)
//            }
        })

        binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    editText.text.toString().let {
                        // TODO this viewModel fun is comment out now
//                        viewModel.searchResults(it).apply{
//                            _adapter.submitList(this)
//                        }
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

    // TODO this viewModel fun is comment out now
//    fun navigateToDetailWithArgs(DetailItem: DetailItem) {
//
//        val action= SearchFragmentDirections
//            .actionRepositoriesFragmentToRepositoryFragment(item= DetailItem)
//
//        // Navigate to DetailFragment with SafeArgs
//        findNavController().navigate(action)
//    }
}

val diff_util= object: DiffUtil.ItemCallback<DetailItem>(){
    override fun areItemsTheSame(
        oldDetailItem: DetailItem,
        newDetailItem: DetailItem
    ): Boolean {
        return oldDetailItem.name== newDetailItem.name
    }

    override fun areContentsTheSame(
        oldDetailItem: DetailItem,
        newDetailItem: DetailItem
    ): Boolean {

        return oldDetailItem== newDetailItem
    }

}

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<DetailItem, CustomAdapter.ViewHolder>(diff_util){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
    	fun itemClick(DetailItem: DetailItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    	val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)

    	return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    	val item= getItem(position)

        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            item.name

    	holder.itemView.setOnClickListener{
     		itemClickListener.itemClick(item)
    	}
    }
}
