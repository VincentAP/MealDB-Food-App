package com.example.tugassoavincentardyanputra2101658344.ui.category.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.StrictMode
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.HeaderSectionViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.databinding.FragmentFilterTypeBinding
import com.example.tugassoavincentardyanputra2101658344.ui.category.viewholder.FilterTypeItem
import com.example.tugassoavincentardyanputra2101658344.ui.category.viewholder.FilterTypeViewHolderFactory
import com.example.tugassoavincentardyanputra2101658344.ui.category.viewmodel.FilterTypeViewModel
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseRecyclerAdapter
import com.example.tugassoavincentardyanputra2101658344.util.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterTypeBottomFragment : BottomSheetDialogFragment() {

    private var binding: FragmentFilterTypeBinding? = null
    private var onFilterTypeSelected: OnFilterTypeSelected? = null

    private val filterTypeViewModel: FilterTypeViewModel by viewModels()

    private val listener: (BaseItem, View) -> Unit = { item, _ ->
        setOnClick(item)
    }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            NoDataViewHolderFactory(),
            HeaderSectionViewHolderFactory(),
            FilterTypeViewHolderFactory()
        )
    )

    fun setOnFilterTypeSelected(onFilterTypeSelected: OnFilterTypeSelected) = apply {
        this.onFilterTypeSelected = onFilterTypeSelected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheetDialog.setOnShowListener {
            val coordinator = (it as BottomSheetDialog)
                .findViewById<CoordinatorLayout>(com.google.android.material.R.id.coordinator)
            val containerLayout =
                it.findViewById<FrameLayout>(com.google.android.material.R.id.container)
            val buttonView =
                bottomSheetDialog.layoutInflater.inflate(R.layout.sticky_button_bottom_sheet, null)

            buttonView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
            }
            containerLayout!!.addView(buttonView)

            buttonView.post {
                (coordinator!!.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    buttonView.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )
                    this.bottomMargin = buttonView.measuredHeight
                    containerLayout.requestLayout()
                }
            }

            val button = buttonView.findViewById<AppCompatButton>(R.id.buttonApply)
            button.setOnClickListener {
                onFilterTypeSelected?.onFilterTypeSelected(
                    filterTypeViewModel.getFilterType(),
                    filterTypeViewModel.getFilterName()
                )
                dismiss()
            }
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterTypeBinding.inflate(inflater, container, false)
        filterTypeViewModel.getFilter()

        binding?.recyclerFilterType?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapter
        }

        setupFilterTypeViewModel()
        return binding?.root
    }

    private fun setupFilterTypeViewModel() {
        filterTypeViewModel.filterTypeItem.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                else -> {
                    recyclerAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun setOnClick(item: BaseItem) {
        when (item) {
            is FilterTypeItem -> {
                recyclerAdapter.notifyDataSetChanged()
                filterTypeViewModel.setFilterName(item.filterName)
                filterTypeViewModel.setFilterType(item.filterType)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    interface OnFilterTypeSelected {
        fun onFilterTypeSelected(filterType: String, filterName: String)
    }

    companion object {
        @JvmStatic
        val TAG: String = FilterTypeBottomFragment::class.java.name

        @JvmStatic
        fun newInstance() = FilterTypeBottomFragment()
    }
}