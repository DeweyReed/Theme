package xyz.aprildown.theme.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt

internal fun View.changeBackgroundColor(@ColorInt color: Int) {
    setBackgroundColor(color)
}

internal fun TextView.changeTextColor(@ColorInt color: Int) {
    setTextColor(color)
}

internal fun TextView.changeHintTextColor(@ColorInt color: Int) {
    setHintTextColor(color)
}

internal fun ImageView.changeImageTint(@ColorInt color: Int) {
    setColorFilter(color)
}