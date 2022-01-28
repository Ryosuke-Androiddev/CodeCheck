/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.bind
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.databinding.FragmentDetailBinding
import java.time.LocalDate

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    // view inflate
    private var binding: FragmentDetailBinding? = null
    private val _binding get() = binding!!

    // MainActivity から、lateinit を削除後，呼び出された時刻を表示する．
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ここに対しての，lateinit を削除した．
        Log.d("検索した日時", LocalDate.now().toString())

        // Viewの生成方法に違和感あり
        binding = FragmentDetailBinding.bind(view)

        // this is navigated item
        var item = args.item

        // set info into view using viewBinding
        _binding.ownerIconView.load(item.ownerIconUrl);
        _binding.nameView.text = item.name;
        _binding.languageView.text = item.language;
        _binding.starsView.text = "${item.stargazersCount} stars";
        _binding.watchersView.text = "${item.watchersCount} watchers";
        _binding.forksView.text = "${item.forksCount} forks";
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}
