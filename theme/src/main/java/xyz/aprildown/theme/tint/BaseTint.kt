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
    private val onTint: (helper: ThemeHelper<T>) -> Unit
) {

    fun apply(view: T, set: AttributeSet?): T {
        view.context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes) {
            onTint.invoke(ThemeHelper(view, this))
        }
        return view
    }
}

internal fun <T : View> T.decorate(attrs: AttributeSet?, tint: BaseTint<T>): T {
    return tint.apply(this, attrs)
}

internal class ThemeHelper<T : View>(val view: T, val typedArray: TypedArray) {

    fun findThemeColor(
        @StyleableRes index: Int,
        fallback: ((resourceId: Int) -> Unit)? = null,
        onGet: (color: Int) -> Unit
    ) {
        val resourceId = typedArray.getResourceId(index, -1)
        if (resourceId != -1) {
            Theme.get().run {
                when (view.resources.getResourceEntryName(resourceId)) {
                    "colorPrimary" -> onGet(colorPrimary)
                    "colorPrimaryVariant" -> onGet(colorPrimaryVariant)
                    "colorOnPrimary" -> onGet(colorOnPrimary)
                    "colorSecondary" -> onGet(colorSecondary)
                    "colorSecondaryVariant" -> onGet(colorSecondaryVariant)
                    "colorOnSecondary" -> onGet(colorOnPrimary)
                    else -> fallback?.invoke(resourceId)
                }
            }
        }
    }
}
