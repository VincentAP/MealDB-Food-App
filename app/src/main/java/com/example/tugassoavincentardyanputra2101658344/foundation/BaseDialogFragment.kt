package com.example.tugassoavincentardyanputra2101658344.foundation

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment

open class BaseDialogFragment : DialogFragment() {

    var height = 0
    inline val windowHeight: Int
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity?.apply {
                    val metrics = windowManager.currentWindowMetrics
                    val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
                    height = metrics.bounds.height() - insets.bottom - insets.top
                }
                height
            } else {
                activity?.apply {
                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    height = displayMetrics.heightPixels
                }
                height
            }
        }

    protected fun Toolbar.setup() {
        val activity = activity as BaseActivity
        activity.setSupportActionBar(this)
    }

    open fun onBackPressed(): Boolean {
        return true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                val backPressed = this@BaseDialogFragment.onBackPressed()
                if (backPressed) dismiss()
            }
        }
    }

    protected fun showShortToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    protected fun showShortToastByResId(stringResId: Int) {
        Toast.makeText(context, resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToastByResId(stringResId: Int) {
        Toast.makeText(context, resources.getString(stringResId), Toast.LENGTH_LONG).show()
    }

    inline fun <reified T : Any> extra(key: String, default: T? = null) = lazy {
        val value = arguments?.get(key)
        if (value is T) value else default
    }

    inline fun <reified T : Any> extraNotNull(key: String, default: T? = null) = lazy {
        val value = arguments?.get(key)
        requireNotNull(if (value is T) value else default) { key }
    }
}