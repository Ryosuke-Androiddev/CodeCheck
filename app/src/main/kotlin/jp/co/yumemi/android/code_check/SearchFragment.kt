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
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding

class SearchFragment: Fragment(R.layout.fragment_search){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val binding= FragmentSearchBinding.bind(view)

        val viewModel= SearchViewModel()

        val layoutManager= LinearLayoutManager(context!!)


        // RecyclerView における境界線
        val dividerItemDecoration=
            DividerItemDecoration(context!!, layoutManager.orientation)

        val adapter= ItemListAdapter(object : ItemListAdapter.OnItemClickListener{

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

// 作成したDiffUtilは，ViewHolderの中で呼び出しを行う
val Diff_UTIL_ITEM_CALLBACK = object: DiffUtil.ItemCallback<DetailItem>(){

    // オブジェクト特有の値を用いて，比較を行う．今回は，それぞれのオブジェクトの名前の比較を行う
    override fun areItemsTheSame(
        oldDetailItem: DetailItem,
        newDetailItem: DetailItem
    ): Boolean {
        return oldDetailItem.name == newDetailItem.name
    }

    // data class を比較しているので，今回は中身が同じであるかは以下のようにして判定することができる
    override fun areContentsTheSame(
        oldDetailItem: DetailItem,
        newDetailItem: DetailItem
    ): Boolean {

        return oldDetailItem == newDetailItem
    }

}

//表示するデータの型DetailItem，次に, ViewHolderの型を指定する
class ItemListAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<DetailItem, ItemListAdapter.DetailItemViewHolder>(Diff_UTIL_ITEM_CALLBACK){

    //bind fun で、Viewと受け取ったObjectを結び付ける
    class DetailItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
    	fun itemClick(DetailItem: DetailItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {

        // 生成されたBinding Classを使えば、fromが必要なくなる?　そもそも、fromの意味とは?
    	val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)

    	return DetailItemViewHolder(view)
    }

    // 生成されたBinding Classを引数で渡す。
    override fun onBindViewHolder(holderDetailItem: DetailItemViewHolder, position: Int) {

        //holder.bind(getItem(position))
    	val item= getItem(position)

        //ここで強制キャストが行われてるのか この処理は、フルネームを表示するためのものである。
        (holderDetailItem.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            item.name

        // click されたときに、何を渡すのかを決める必要がある
    	holderDetailItem.itemView.setOnClickListener{
     		itemClickListener.itemClick(item)
    	}
    }
}
