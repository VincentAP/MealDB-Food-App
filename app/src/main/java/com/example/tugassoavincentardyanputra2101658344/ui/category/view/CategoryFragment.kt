package com.example.tugassoavincentardyanputra2101658344.ui.category.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.AdditionalSpaceViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.ListItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.ListViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.databinding.FragmentCategoryBinding
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseFullScreenDialogFragment
import com.example.tugassoavincentardyanputra2101658344.ui.category.viewmodel.CategoryViewModel
import com.example.tugassoavincentardyanputra2101658344.ui.detail.view.DetailFragment
import com.example.tugassoavincentardyanputra2101658344.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : BaseFullScreenDialogFragment() {

    private var binding: FragmentCategoryBinding? = null

    @Inject
    lateinit var sharePref: SharePref

    private val categoryViewModel: CategoryViewModel by viewModels()

    private val category by extra<String>(CATEGORY)

    private val listener: (BaseItem, View) -> Unit = { item, _ ->
        setOnClick(item)
    }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            ListViewHolderFactory(),
            AdditionalSpaceViewHolderFactory(),
            NoDataViewHolderFactory()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        sharePref.put(Constant.SELECTED_FILTER_TYPE, category)
        binding?.apply {
            toolbarCategory.setup()
            layoutToolbar.imageBack.setOnClickListener { dismiss() }
            recyclerCategory.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerAdapter
            }

            layoutToolbar.editCategorySearch.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        categoryViewModel.onInputStateChanged(s.toString().trim())
                        categoryViewModel.searchFood(s.toString().trim())
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })

            linearFilterButtonWrapper.setOnClickListener {
                activity?.supportFragmentManager?.let { fm ->
                    FilterTypeBottomFragment.newInstance()
                        .setOnFilterTypeSelected(object :
                            FilterTypeBottomFragment.OnFilterTypeSelected {
                            override fun onFilterTypeSelected(
                                filterType: String,
                                filterName: String
                            ) {
                                when (filterType) {
                                    "Categories" -> categoryViewModel.getMealByCategory(filterName)
                                    "Area" -> categoryViewModel.getMealByArea(filterName)
                                    "Ingredients" -> categoryViewModel.getMealByIngredient(
                                        filterName
                                    )
                                }
                            }
                        })
                        .show(fm, FilterTypeBottomFragment.TAG)
                }
            }
        }

        setupCategoryViewModel()
        return binding?.root
    }

    private fun setupCategoryViewModel() {
        category?.let { categoryViewModel.getMealByCategory(it) }
        categoryViewModel.mealItemList.observe(this) {
            when (it.status) {
                Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                Status.SUCCESS, Status.EMPTY -> {
                    binding?.progressBar?.visibility = View.GONE
                    recyclerAdapter.submitList(it.data)
                }
                Status.ERROR -> showShortToast(it.message ?: "")
            }
        }
    }

    private fun setOnClick(item: BaseItem) {
        when (item) {
            is ListItem -> {
                activity?.supportFragmentManager?.let { fm ->
                    DetailFragment.newInstance(
                        idMeal = item.idMeal,
                        type = Constant.CATEGORY
                    )
                        .show(fm, DetailFragment.TAG)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharePref.clear()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = CategoryFragment::class.java.name
        private const val CATEGORY = "CATEGORY"
        private const val DELAY = 500L

        @JvmStatic
        fun newInstance(
            category: String
        ) = CategoryFragment().apply {
            arguments = Bundle().apply {
                putString(CATEGORY, category)
            }
        }
    }
}