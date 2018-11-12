package xyz.aprildown.theme.material.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import xyz.aprildown.theme.material.R

/**
 *  Copied some methods from theme module.
 *  Is there any better way to use internal methods and not expose them to other modules?
 */

@ColorInt
internal fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

@ColorInt
internal fun Int.darkenColor(): Int {
    return shiftColor(0.9f)
}

internal fun Int.isColorLight(): Boolean {
    if (this == Color.BLACK) {
        return false
    } else if (this == Color.WHITE || this == Color.TRANSPARENT) {
        return true
    }
    val darkness =
        1 - (0.299 * Color.red(this) +
                0.587 * Color.green(this) +
                0.114 * Color.blue(this)) / 255
    return darkness < 0.4
}

@ColorInt
internal fun Int.adjustAlpha(factor: Float): Int {
    val alpha = Math.round(Color.alpha(this) * factor)
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}

@ColorInt
internal fun Int.shiftColor(
    @FloatRange(from = 0.0, to = 2.0) by: Float
): Int {
    if (by == 1f) return this
    val hsv = FloatArray(3)
    Color.colorToHSV(this, hsv)
    hsv[2] *= by // value component
    return Color.HSVToColor(hsv)
}

@SuppressLint("PrivateResource")
@ColorInt
internal fun defaultRippleColor(
    context: Context,
    useDarkRipple: Boolean
): Int {
    // Light ripple is actually translucent black, and vice versa
    return context.color(
        if (useDarkRipple) R.color.ripple_material_light
        else R.color.ripple_material_dark
    )
}

@CheckResult
internal fun Drawable?.tint(@ColorInt color: Int): Drawable? {
    var result: Drawable = this ?: return null
    result = DrawableCompat.wrap(result.mutate())
    DrawableCompat.setTintMode(result, PorterDuff.Mode.SRC_IN)
    DrawableCompat.setTint(result, color)
    return result
}