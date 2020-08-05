package xyz.aprildown.theme.tint

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.imageview.ShapeableImageView
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
        // R.style.Widget_AppCompat_ImageButton
        tintImageView()
    }
)

private fun ThemeHelper<*>.tintImageView() {
    val imageView = view as ImageView
    matchThemeColor(R.styleable.Theme_ImageView_android_background)?.let {
        ViewCompat.setBackgroundTintList(imageView, it.toColorStateList())
    }
    matchThemeColor(R.styleable.Theme_ImageView_tint)?.let {
        ImageViewCompat.setImageTintList(imageView, it.toColorStateList())
    }
}

internal class ShapeableImageViewTint : BaseTint<ShapeableImageView>(
    attrs = R.styleable.Theme_ShapeableImageView,
    onTint = {
        // R.style.Widget_MaterialComponents_ShapeableImageView
        val imageView = view
        matchThemeColor(R.styleable.Theme_ShapeableImageView_android_background)?.let {
            ViewCompat.setBackgroundTintList(imageView, it.toColorStateList())
        }
        matchThemeColor(R.styleable.Theme_ShapeableImageView_tint)?.let {
            ImageViewCompat.setImageTintList(imageView, it.toColorStateList())
        }
        matchThemeColor(R.styleable.Theme_ShapeableImageView_strokeColor)?.let {
            imageView.strokeColor = it.toColorStateList()
        }
    }
)
