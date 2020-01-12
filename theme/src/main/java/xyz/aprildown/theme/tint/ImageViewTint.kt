package xyz.aprildown.theme.tint

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.textview.MaterialTextView
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.toColorStateList

/**
 * [MaterialTextView]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialTextView.md
 */
internal class ImageViewTint : BaseTint<AppCompatImageView>(
    attrs = R.styleable.Theme_ImageView,
    onTint = { helper ->
        val imageView = helper.view
        helper.findThemeColor(R.styleable.Theme_ImageView_android_background)?.let {
            ViewCompat.setBackgroundTintList(imageView, it.toColorStateList())
        }
        helper.findThemeColor(R.styleable.Theme_ImageView_tint)?.let {
            ImageViewCompat.setImageTintList(imageView, it.toColorStateList())
        }
    }
)
