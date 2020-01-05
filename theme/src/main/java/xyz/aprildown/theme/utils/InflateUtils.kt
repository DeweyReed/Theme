package xyz.aprildown.theme.utils

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import xyz.aprildown.theme.Theme

internal class ThemeHelper(private val typedArray: TypedArray) {
    fun findThemeColor(@StyleableRes index: Int): Int? {
        val resourceId = typedArray.getResourceId(index, -1)
        return if (resourceId != -1) {
            return Theme.get().run {
                when (context.resources.getResourceEntryName(resourceId)) {
                    "colorPrimary" -> colorPrimary
                    "colorPrimaryVariant" -> colorPrimaryVariant
                    "colorOnPrimary" -> colorOnPrimary
                    "colorSecondary" -> colorSecondary
                    "colorSecondaryVariant" -> colorSecondaryVariant
                    "colorOnSecondary" -> colorOnPrimary
                    else -> null
                }
            }
        } else {
            null
        }
    }
}

internal fun <T : View> T.decorateWithTheme(
    set: AttributeSet? = null,
    attrs: IntArray,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    onGet: (T, ThemeHelper) -> Unit
): T {
    val context = context
    context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes) {
        onGet.invoke(this@decorateWithTheme, ThemeHelper(this))
    }
    return this
}
