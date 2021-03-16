package com.example.tugassoavincentardyanputra2101658344.ui.homepage.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.FoodTitleViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.HeaderSectionViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.databinding.ActivityMainBinding
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseActivity
import com.example.tugassoavincentardyanputra2101658344.ui.category.view.CategoryFragment
import com.example.tugassoavincentardyanputra2101658344.ui.category.viewmodel.CategoryViewModel
import com.example.tugassoavincentardyanputra2101658344.ui.detail.view.DetailFragment
import com.example.tugassoavincentardyanputra2101658344.ui.favorites.view.FavoriteActivity
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.*
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewmodel.FoodOfTheDayViewModel
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewmodel.HomepageViewModelImpl
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewmodel.MostPopularFoodViewModel
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseRecyclerAdapter
import com.example.tugassoavincentardyanputra2101658344.util.Constant
import com.example.tugassoavincentardyanputra2101658344.util.Status
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Homepage : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    private val mostPopularFoodViewModel: MostPopularFoodViewModel by viewModels()

    private val categoryViewModel: CategoryViewModel by viewModels()

    private val foodOfTheDayViewModel: FoodOfTheDayViewModel by viewModels()

    private val homepageViewModel: HomepageViewModelImpl by viewModels()

    private var isMostPopularFood = false
    private var isFavoriteStateChanged = false
    private var isFromFavoritePage = false
    private var isFoodOfTheDayItem = false

    private val listener: (BaseItem, View) -> Unit = { item, view ->
        setOnClick(item, view)
    }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            NestedHorizontalRecyclerViewHolderFactory(),
            FoodTitleViewHolderFactory(),
            HeaderSectionViewHolderFactory(),
            FoodOfTheDayViewHolderFactory(),
            FeatureCategoryViewHolderFactory()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomSheet()

        with(binding) {
            recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(this@Homepage, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerAdapter
            }

            imageFavoritePage.setOnClickListener {
                startForResult.launch(Intent(applicationContext, FavoriteActivity::class.java))
            }
        }

        setupHomepageViewModel()
    }

    private fun setupHomepageViewModel() {
        homepageViewModel.apply {
            setInitialItem()
            homepageItem.observe(this@Homepage) {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS, Status.EMPTY -> {
                        if (isMostPopularFood) {
                            binding.shimmerRecycler.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        it.data?.let { item ->
                            recyclerAdapter.submitList(item.toMutableList())
                        }
                    }
                    Status.ERROR -> {
                        if (isMostPopularFood) {
                            binding.shimmerRecycler.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                        }
                        showShortToast("ERROR")
                    }
                }
            }
        }

        mostPopularFoodViewModel.mostPopularFood.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    isMostPopularFood = true
                    it.data?.let { it1 ->
                        if (!isFavoriteStateChanged && !isFromFavoritePage ||
                            isFoodOfTheDayItem && !isFavoriteStateChanged
                        ) {
                            homepageViewModel.setData(it1)
                            isFoodOfTheDayItem = false
                        } else {
                            isFromFavoritePage = false
                            isFavoriteStateChanged = false
                        }
                    }
                }
                Status.ERROR -> {
                }
                else -> showShortToast(it.message ?: "")
            }
        }

        categoryViewModel.homepageCategory.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    foodOfTheDayViewModel.refresh()
                    it.data?.let { it1 -> homepageViewModel.setData(it1) }
                }
                Status.ERROR -> {
                }
                else -> showShortToast(it.message ?: "")
            }
        }

        foodOfTheDayViewModel.foodOfTheDay.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    mostPopularFoodViewModel.refresh()
                    it.data?.let { it1 -> homepageViewModel.setData(it1) }
                }
                Status.ERROR -> {
                }
                else -> showShortToast(it.message ?: "")
            }
        }
    }

    private fun setupBottomSheet() {
        with(binding) {
            mBottomSheetBehavior = BottomSheetBehavior.from(nestedBottomSheetWrapper)
            mBottomSheetBehavior.setPeekHeight(windowHeight / 2, true)
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            nestedBottomSheetWrapper.minimumHeight = windowHeight
        }
    }

    private fun setOnClick(item: BaseItem, view: View) {
        when (item) {
            is MostPopularFoodItem -> {
                when (view.id) {
                    R.id.imageFavorite -> {
                        isFavoriteStateChanged = true
                        if (item.isFavoriteAfterClicked) homepageViewModel.setFavorite(
                            mostPopularFoodItem = item.mpfItem
                        ) else homepageViewModel.deleteFavorite(
                            item.mpfItem.idMeal,
                            true
                        )
                    }
                    R.id.cardMostPopularFoodItemWrapper -> {
                        DetailFragment.newInstance(
                            idMeal = item.mpfItem.idMeal,
                            type = Constant.MOST_POPULAR_FOOD,
                            isFavorite = item.mpfItem.isFavorite
                        )
                            .show(supportFragmentManager, DetailFragment.TAG)
                    }
                }
            }
            is FoodOfTheDayItem -> {
                when (view.id) {
                    R.id.imageFavorite -> {
                        isFoodOfTheDayItem = true
                        if (item.isFavoriteAfterClicked) homepageViewModel.setFavorite(
                            foodOfTheDayItem = item.bfItem
                        ) else homepageViewModel.deleteFavorite(item.bfItem.idMeal)
                    }
                    R.id.cardFoodOfTheDayItemWrapper -> {
                        DetailFragment.newInstance(
                            idMeal = item.bfItem.idMeal,
                            isFavorite = item.bfItem.isFavorite
                        )
                            .show(supportFragmentManager, DetailFragment.TAG)
                    }
                }
            }
            is FeatureCategoryItem -> {
                when (item.isCategory) {
                    true -> CategoryFragment.newInstance(item.title)
                        .show(supportFragmentManager, CategoryFragment.TAG)
                    false -> DetailFragment.newInstance(true)
                        .show(supportFragmentManager, DetailFragment.TAG)
                }
            }
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getBooleanExtra(Constant.IS_DELETED, true)?.let {
                    isFromFavoritePage = it
                }
            }
        }

    override fun onResume() {
        super.onResume()
        homepageViewModel.refresh()
        categoryViewModel.refresh()
        isMostPopularFood = false
    }
}