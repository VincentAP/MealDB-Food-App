package com.example.tugassoavincentardyanputra2101658344.ui.detail.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.AdditionalSpaceViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.FoodTitleViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.databinding.FragmentDetailBinding
import com.example.tugassoavincentardyanputra2101658344.extension.animateClicked
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseFullScreenDialogFragment
import com.example.tugassoavincentardyanputra2101658344.ui.detail.viewholder.DetailImageViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.ui.detail.viewholder.DetailListViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.ui.detail.viewmodel.DetailViewModel
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseRecyclerAdapter
import com.example.tugassoavincentardyanputra2101658344.util.Constant
import com.example.tugassoavincentardyanputra2101658344.util.Status
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFullScreenDialogFragment() {

    private var binding: FragmentDetailBinding? = null
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val detailViewModel: DetailViewModel by viewModels()

    private val isRandomFood by extraNotNull(IS_RANDOM_FOOD, false)
    private val isFavorite by extraNotNull(IS_FAVORITE, false)
    private val idMeal by extra<String>(ID_MEAL)
    private val type by extraNotNull(TYPE, Constant.BEST_FOOD_OF_THE_DAY)

    private var isAddedToFavorite = false

    private val listener: (BaseItem, View) -> Unit = { _, _ -> }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            NoDataViewHolderFactory(),
            DetailListViewHolderFactory(),
            DetailImageViewHolderFactory(),
            AdditionalSpaceViewHolderFactory(),
            FoodTitleViewHolderFactory()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setupBottomSheet()
        isAddedToFavorite = isFavorite

        if (isRandomFood) binding?.buttonRandom?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                detailViewModel.getRandomFood()
            }
        } else {
            idMeal?.let {
                binding?.textCookFood?.visibility = View.GONE
                binding?.textChallengeYourself?.apply {
                    text = getString(R.string.food_detail)
                    textSize = 50F
                }
                when (type) {
                    Constant.BEST_FOOD_OF_THE_DAY -> detailViewModel.getBestFoodOfTheDayItem(it)
                    Constant.MOST_POPULAR_FOOD -> detailViewModel.getMostPopularFoodItem(it)
                    else -> detailViewModel.getMealFromCategory(it)
                }
            }
        }

        binding?.recyclerViewFavorite?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapter
        }

        binding?.imageAddToFavorite?.setOnClickListener {
            isAddedToFavorite = !isAddedToFavorite
            binding?.imageAddToFavorite?.apply {
                animateClicked()
                if (isAddedToFavorite) setImageResource(R.drawable.ic_favorite)
                else setImageResource(R.drawable.ic_unfavorite)
            }
            if (isAddedToFavorite) detailViewModel.setFavorite()
            else detailViewModel.getIdMeal()?.let { id ->
                detailViewModel.deleteFavorite(id)
            }
        }

        binding?.imageBack?.setOnClickListener { dismiss() }

        binding?.textSource?.setOnClickListener { openBrowser() }

        binding?.textYoutube?.setOnClickListener { openYoutube() }

        setupDetailViewModel()
        return binding?.root
    }

    private fun setupDetailViewModel() {
        if (isRandomFood) detailViewModel.getRandomFood()
        detailViewModel.detailItem.observe(this) {
            when (it.status) {
                Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.linearSourceYoutubeWrapper?.visibility = View.VISIBLE
                    recyclerAdapter.submitList(it.data)
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                    showShortToast("Error")
                }
                Status.EMPTY -> {
                    binding?.progressBar?.visibility = View.GONE
                    recyclerAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun setupBottomSheet() {
        binding?.apply {
            mBottomSheetBehavior = BottomSheetBehavior.from(
                constraintFragmentDetailBottomSheetWrapper
            )
            mBottomSheetBehavior.setPeekHeight(windowHeight / 2, true)
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            constraintFragmentDetailBottomSheetWrapper.minHeight = windowHeight
        }
    }

    private fun openBrowser() {
        detailViewModel.getItemSource()?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(it)
            startActivity(browserIntent)
        }
    }

    private fun openYoutube() {
        detailViewModel.getItemYoutubeSource()?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = DetailFragment::class.java.name
        private const val IS_RANDOM_FOOD = "IS_RANDOM_FOOD"
        private const val ID_MEAL = "ID_MEAL"
        private const val TYPE = "TYPE"
        private const val IS_FAVORITE = "IS_FAVORITE"

        @JvmStatic
        fun newInstance(
            isRandomFood: Boolean = false,
            idMeal: String? = null,
            type: String = Constant.BEST_FOOD_OF_THE_DAY,
            isFavorite: Boolean = false
        ) = DetailFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_RANDOM_FOOD, isRandomFood)
                putBoolean(IS_FAVORITE, isFavorite)
                putString(ID_MEAL, idMeal)
                putString(TYPE, type)
            }
        }
    }
}