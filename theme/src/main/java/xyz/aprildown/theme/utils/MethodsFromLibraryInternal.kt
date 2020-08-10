package xyz.aprildown.theme.utils

import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.internal.ViewUtils

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
