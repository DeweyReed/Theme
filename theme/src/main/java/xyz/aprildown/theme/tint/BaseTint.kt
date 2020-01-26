package xyz.aprildown.theme.tint

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.themeRes

internal abstract class BaseTint<T : View>(
    private val attrs: IntArray,
    @AttrRes private val defStyleAttr: Int = 0,
    @StyleRes private val defStyleRes: Int = 0,
    private val onTint: ThemeHelper<T>.() -> Unit
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

    fun matchThemeColor(@StyleableRes index: Int): Int? {
        val resourceId = typedArray.getResourceId(index, -1)
        return try {
            if (resourceId != -1) {
                Theme.get().run {
                    when (view.resources.getResourceEntryName(resourceId)) {
                        "colorPrimary" -> colorPrimary
                        "colorPrimaryVariant" -> colorPrimaryVariant
                        "colorOnPrimary" -> colorOnPrimary
                        "colorSecondary" -> colorSecondary
                        "colorSecondaryVariant" -> colorSecondaryVariant
                        "colorOnSecondary" -> colorOnSecondary
                        else -> null
                    }
                }
            } else {
                null
            }
        } catch (e: Resources.NotFoundException) {
            null
        }
    }

    fun findAttributeColor(@AttrRes attrRes: Int): Int? {
        val theme = Theme.get()
        return when (view.context.themeRes(attrRes)) {
            theme.colorPrimaryRes -> theme.colorPrimary
            theme.colorSecondaryRes -> theme.colorSecondary
            else -> null
        }
    }

    fun withColorOrResourceId(
        @StyleableRes index: Int,
        applySolidColor: (color: Int) -> Unit,
        applyResource: ((resourceId: Int) -> Unit)? = null,
        applyDefault: (() -> Unit)? = null
    ) {
        val color = matchThemeColor(index)
        if (color != null) {
            applySolidColor.invoke(color)
        } else {
            if (typedArray.hasValue(index)) {
                val resourceId = typedArray.getResourceId(index, -1)
                if (resourceId != -1 && applyResource != null) {
                    applyResource.invoke(resourceId)
                }
            } else {
                applyDefault?.invoke()
            }
        }
    }
}
