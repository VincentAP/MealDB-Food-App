package com.example.tugassoavincentardyanputra2101658344.ui.favorites.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.ListItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.ListViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.databinding.ActivityFavoriteBinding
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseActivity
import com.example.tugassoavincentardyanputra2101658344.ui.favorites.viewmodel.FavoriteViewModel
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseRecyclerAdapter
import com.example.tugassoavincentardyanputra2101658344.util.Constant
import com.example.tugassoavincentardyanputra2101658344.util.Status
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : BaseActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private var isItemDeleted = false
    private var isItemDeletedHere = false
    private var isUpdated = false

    private val listener: (BaseItem, View) -> Unit = { item, _ ->
        setOnClick(item)
    }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            NoDataViewHolderFactory(),
            ListViewHolderFactory()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomSheet()

        binding.recyclerViewFavorite.apply {
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            refreshPage()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.imageBack.setOnClickListener {
            val intent = Intent().putExtra(Constant.IS_DELETED, isItemDeleted)
            setResult(RESULT_OK, intent)
            finish()
        }

        setupFavoriteViewModel()
    }

    private fun setupFavoriteViewModel() {
        favoriteViewModel.favoriteItem.observe(this) {
            when (it.status) {
                Status.SUCCESS, Status.EMPTY -> {
                    binding.shimmerFavoriteRecycler.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    binding.recyclerViewFavorite.visibility = View.VISIBLE
                    if (isItemDeletedHere && isUpdated) {
                        isUpdated = false
                        isItemDeletedHere = false
                        recyclerAdapter.submitList(it.data)
                    } else if (!isItemDeleted) recyclerAdapter.submitList(it.data)
                }
                else -> showShortToast(it.message ?: "")
            }
        }

        favoriteViewModel.isUpdated.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    favoriteViewModel.refresh()
                    isUpdated = true
                }
                else -> {
                }
            }
        }
    }

    private fun setupBottomSheet() {
        with(binding) {
            mBottomSheetBehavior =
                BottomSheetBehavior.from(constraintActivityFavoriteBottomSheetWrapper)
            mBottomSheetBehavior.setPeekHeight(windowHeight / 2, true)
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            constraintActivityFavoriteBottomSheetWrapper.minHeight = windowHeight
        }
    }

    private fun setOnClick(item: BaseItem) {
        when (item) {
            is ListItem -> {
                isItemDeleted = item.isDeleted
                isItemDeletedHere = item.isDeleted
                if (item.isDeleted) favoriteViewModel.deleteFavorite(
                    item.idMeal,
                    item.isFromMostPopularFood
                )
            }
        }
    }

    private fun refreshPage() {
        with(binding) {
            recyclerViewFavorite.visibility = View.GONE
            shimmerFavoriteRecycler.apply {
                visibility = View.VISIBLE
                startShimmer()
            }
            favoriteViewModel.refresh()
        }
    }

    override fun onBackPressed() {
        val intent = Intent().putExtra(Constant.IS_DELETED, isItemDeleted)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.refresh()
    }
}