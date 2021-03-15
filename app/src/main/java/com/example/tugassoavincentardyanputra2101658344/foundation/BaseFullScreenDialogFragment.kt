package com.example.tugassoavincentardyanputra2101658344.foundation

import android.os.Bundle
import com.example.tugassoavincentardyanputra2101658344.R

open class BaseFullScreenDialogFragment : BaseDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        setHasOptionsMenu(true)
    }
}