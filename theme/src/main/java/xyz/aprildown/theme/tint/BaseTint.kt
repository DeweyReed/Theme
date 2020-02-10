package xyz.aprildown.theme.tint

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.findAttrFinalResourceId

internal abstract class BaseTint<T : View>(
    private val attrs: IntArray,
    @AttrRes private val defStyleAttr: Int = 0,
    private val onTint: ThemeHelper<T>.() -> Unit
) {

    fun apply(view: T, set: AttributeSet?): T {
        view.context.withStyledAttributes(set, attrs, defStyleAttr) {
            onTint.invoke(ThemeHelper(view, this))
        }
        return view
    }
}

internal fun <T : View> T.decorate(attrs: AttributeSet?, tint: BaseTint<T>): T {
    return tint.apply(this, attrs)
}

internal class ThemeHelper<T : View>(val view: T, private val typedArray: TypedArray) {

    fun matchThemeColor(@StyleableRes index: Int): Int? {
        val resourceId = typedArray.getResourceId(index, -1)
        return try {
            if (resourceId != -1) {
                Theme.get().run {
                    /**
                     * Here's something weired. In order to make this implementation work,
                     * you have to define this way(I use primary color as the example):
                     * <color name="colorPrimary">#FF0000</color>
                     * Then in the styles.xml:
                     * <style name="AppTheme" ...>
                     *     <item name="colorPrimary">@color/colorPrimary</item>
                     *     ...
                     * </style>
                     * The final color(#FF0000)'s name("colorPrimary")
                     * must be identical to the values below, or the names here can't be found.
                     */
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
        val context = view.context
        return when (context.findAttrFinalResourceId(attrRes)) {
            context.findAttrFinalResourceId(R.attr.colorPrimary) -> theme.colorPrimary
            context.findAttrFinalResourceId(R.attr.colorSecondary) -> theme.colorSecondary
            else -> null
        }
    }

    fun withColorOrResourceId(
        @StyleableRes index: Int,
        applySolidColor: ((color: Int) -> Unit)? = null,
        applyResource: ((resourceId: Int) -> Unit)? = null,
        applyDefault: (() -> Unit)? = null
    ) {
        val color = matchThemeColor(index)
        if (color != null) {
            applySolidColor?.invoke(color)
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
