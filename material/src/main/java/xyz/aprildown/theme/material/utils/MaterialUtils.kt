package xyz.aprildown.theme.material.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 *  Copied some methods from theme module.
 *  Is there any better way?
 */

@ColorInt
internal fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

@CheckResult
internal fun Drawable?.tint(@ColorInt color: Int): Drawable? {
    var result: Drawable = this ?: return null
    result = DrawableCompat.wrap(result.mutate())
    DrawableCompat.setTintMode(result, PorterDuff.Mode.SRC_IN)
    DrawableCompat.setTint(result, color)
    return result
}