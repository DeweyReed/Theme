package xyz.aprildown.theme.utils

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import com.google.android.material.color.MaterialColors
import com.google.android.material.internal.ViewUtils
import kotlin.math.roundToInt

/**
 * [MaterialColors]
 * Calculates a color that represents the layering of the `overlayColor` on top of the
 * `backgroundColor`.
 */
@ColorInt
internal fun layer(@ColorInt backgroundColor: Int, @ColorInt overlayColor: Int): Int {
    return ColorUtils.compositeColors(overlayColor, backgroundColor)
}

/**
 * [MaterialColors]
 * Calculates a color that represents the layering of the `overlayColor` (with `overlayAlpha` applied) on top of the `backgroundColor`.
 */
@ColorInt
internal fun layer(
    @ColorInt backgroundColor: Int,
    @ColorInt overlayColor: Int,
    @FloatRange(from = 0.0, to = 1.0) overlayAlpha: Float
): Int {
    val computedAlpha = (Color.alpha(overlayColor) * overlayAlpha).roundToInt()
    val computedOverlayColor = ColorUtils.setAlphaComponent(overlayColor, computedAlpha)
    return layer(backgroundColor, computedOverlayColor)
}

/**
 * [ViewUtils]
 * Returns the absolute elevation of the parent of the provided `view`, or in other words,
 * the sum of the elevations of all ancestors of the `view`.
 */
internal fun getParentAbsoluteElevation(view: View): Float {
    var absoluteElevation = 0f
    var viewParent = view.parent
    while (viewParent is View) {
        absoluteElevation += ViewCompat.getElevation((viewParent as View))
        viewParent = viewParent.getParent()
    }
    return absoluteElevation
}
