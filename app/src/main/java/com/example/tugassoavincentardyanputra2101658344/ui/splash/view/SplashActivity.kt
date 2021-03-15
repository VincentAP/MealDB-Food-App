package com.example.tugassoavincentardyanputra2101658344.ui.splash.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.ActivitySplashBinding
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseActivity
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.view.Homepage
import com.example.tugassoavincentardyanputra2101658344.ui.splash.viewmodel.SplashViewModel
import com.example.tugassoavincentardyanputra2101658344.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val splashViewModel: SplashViewModel by viewModels()

    private var isInserted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animate()

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            binding.root.windowInsetsController?.let {
                it.apply {
                    hide(WindowInsets.Type.systemBars())
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            setupSplashViewModel()
        }, TIME_LIMIT)
    }

    private fun setupSplashViewModel() {
        splashViewModel.getFilter()
        splashViewModel.filterItem.observe(this) {
            when (it.status) {
                Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!isInserted) {
                        it.data?.let { ft ->
                            isInserted = true
                            splashViewModel.insertFilterType(ft)
                        }
                    } else navigate()
                }
                Status.EMPTY -> {
                    binding.progressBar.visibility = View.GONE
                    navigate()
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textWaiting.text = getString(R.string.error_initializing)
                    Handler(Looper.getMainLooper()).postDelayed({
                        navigate()
                    }, TIME_LIMIT)
                }
            }
        }
    }

    private fun navigate() {
        Intent(applicationContext, Homepage::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun animate() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        binding.imageFoodSplash.startAnimation(animation)
        binding.textMealDB.startAnimation(animation)
    }

    companion object {
        private const val TIME_LIMIT = 1000L
    }
}