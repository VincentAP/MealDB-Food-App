package com.example.tugassoavincentardyanputra2101658344.foundation

import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    inline val windowHeight: Int
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val metrics = windowManager.currentWindowMetrics
                val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
                metrics.bounds.height() - insets.bottom - insets.top
            } else {
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.heightPixels
            }
        }

    protected fun showShortToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    protected fun showShortToastByResId(stringResId: Int) {
        Toast.makeText(this, resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToastByResId(stringResId: Int) {
        Toast.makeText(this, resources.getString(stringResId), Toast.LENGTH_LONG).show()
    }
}