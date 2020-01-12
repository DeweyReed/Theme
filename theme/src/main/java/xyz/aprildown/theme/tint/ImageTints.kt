package xyz.aprildown.theme.tint

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.toColorStateList

internal class ImageViewTint : BaseTint<AppCompatImageView>(
    attrs = R.styleable.Theme_ImageView,
    onTint = {
        tintImageView()
    }
)

internal class ImageButtonTint : BaseTint<AppCompatImageButton>(
    attrs = R.styleable.Theme_ImageView,
    defStyleAttr = R.attr.imageButtonStyle,
    onTint = {
        tintImageView()
    }
)

private fun ThemeHelper<*>.tintImageView() {
    val imageView = view as ImageView
    findThemeColor(R.styleable.Theme_ImageView_android_background)?.let {
        ViewCompat.setBackgroundTintList(imageView, it.toColorStateList())
    }
    findThemeColor(R.styleable.Theme_ImageView_tint)?.let {
        ImageViewCompat.setImageTintList(imageView, it.toColorStateList())
    }
}
