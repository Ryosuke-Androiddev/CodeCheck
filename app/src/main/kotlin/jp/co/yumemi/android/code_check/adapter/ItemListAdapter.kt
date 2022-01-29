package jp.co.yumemi.android.code_check.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.SearchFragmentDirections
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding
import jp.co.yumemi.android.code_check.model.DetailItem

// 作成したDiffUtilは，ViewHolderの中で呼び出しを行う
val DIFF_UTIL_ITEM_CALLBACK = object: DiffUtil.ItemCallback<DetailItem>() {

    // オブジェクト特有の値を用いて，比較を行う．今回は，それぞれのオブジェクトの名前の比較を行う
    override fun areItemsTheSame(
        oldDetailItem: DetailItem,
        newDetailItem: DetailItem
    ): Boolean {

        return oldDetailItem.fullName == newDetailItem.fullName
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
class ItemListAdapter : ListAdapter<DetailItem, DetailItemViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemBinding.inflate(layoutInflater, parent, false)

        return DetailItemViewHolder(binding)
    }

    // 生成されたBinding Classを引数で渡す。
    override fun onBindViewHolder(holderDetailItem: DetailItemViewHolder, position: Int) {

        holderDetailItem.bind(getItem(position))
    }
}

//bind fun で、Viewと受け取ったObjectを結び付ける
class DetailItemViewHolder(
    private val binding: LayoutItemBinding,
): RecyclerView.ViewHolder(binding.root) {

    // Objectを渡して、このメソッドでバインドする
    fun bind(
        detailItem: DetailItem,
    ) {
        binding.repositoryNameView.text = detailItem.fullName

        binding.repositoryNameView.setOnClickListener {

            // 遷移時に、try-catchを追加
            try {

                val action= SearchFragmentDirections
                    .actionSearchFragmentToDetailFragment(detailItem)

                binding.repositoryNameView.findNavController().navigate(action)

            } catch (e: Exception) {
                Log.d("onItemClickListener", e.toString())
            }
        }
    }
}