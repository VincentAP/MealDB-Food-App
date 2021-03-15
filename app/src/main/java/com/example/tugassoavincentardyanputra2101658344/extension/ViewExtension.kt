package com.example.tugassoavincentardyanputra2101658344.extension

import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.tugassoavincentardyanputra2101658344.R
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(
    imageUrl: String
) {
    Glide
        .with(context)
        .asBitmap()
        .load(imageUrl)
        .into(this)
}

fun AppCompatImageView.loadImage(
    imageUrl: String
) {
    Glide
        .with(context)
        .asBitmap()
        .load(imageUrl)
        .into(this)
}

fun AppCompatImageView.animateClicked() {
    val animClicked = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    val animClickedOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out)
    this.startAnimation(animClicked)
    this.startAnimation(animClickedOut)
}

fun AppCompatImageView.setFavoriteBG(isFavorite: Boolean) {
    if (isFavorite) this.setImageResource(R.drawable.ic_favorite)
    else this.setImageResource(R.drawable.ic_unfavorite)
}