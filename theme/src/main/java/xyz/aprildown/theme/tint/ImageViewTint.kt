package xyz.aprildown.theme.tint

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.toColorStateList

internal class ImageViewTint : BaseTint<AppCompatImageView>(
    attrs = R.styleable.Theme_ImageView,
    onTint = {
        val imageView = view
        findThemeColor(R.styleable.Theme_ImageView_android_background)?.let {
            ViewCompat.setBackgroundTintList(imageView, it.toColorStateList())
        }
        findThemeColor(R.styleable.Theme_ImageView_tint)?.let {
            ImageViewCompat.setImageTintList(imageView, it.toColorStateList())
        }
    }
)
