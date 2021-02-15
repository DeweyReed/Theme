package xyz.aprildown.theme.app.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

@ColorInt
fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.drawable(@DrawableRes drawable: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawable)
}
