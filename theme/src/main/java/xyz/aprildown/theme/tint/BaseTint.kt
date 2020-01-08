package xyz.aprildown.theme.tint

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import xyz.aprildown.theme.Theme

internal abstract class BaseTint<T : View>(
    private val attrs: IntArray,
    @AttrRes private val defStyleAttr: Int = 0,
    @StyleRes private val defStyleRes: Int = 0,
    private val onTint: (view: T, helper: ThemeHelper) -> Unit
) {

    fun apply(view: T, set: AttributeSet?): T {
        view.context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes) {
            onTint.invoke(view, ThemeHelper(this))
        }
        return view
    }
}

internal fun <T : View> T.decorate(attrs: AttributeSet?, tint: BaseTint<T>): T {
    return tint.apply(this, attrs)
}

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
