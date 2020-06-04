package xyz.aprildown.theme.tint

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.matchThemeColor
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
    return if (!isInEditMode && Theme.get().enabled) {
        tint.apply(this, attrs)
    } else {
        this
    }
}

internal class ThemeHelper<T : View>(val view: T, val typedArray: TypedArray) {

    fun matchThemeColor(@StyleableRes index: Int): Int? {
        return typedArray.matchThemeColor(index)
    }

    fun findAttributeColor(@AttrRes attrRes: Int): Int? {
        val theme = Theme.get()
        val context = view.context
        return when (context.findAttrFinalResourceId(attrRes)) {
            context.findAttrFinalResourceId(R.attr.colorPrimary) -> theme.colorPrimary
            context.findAttrFinalResourceId(R.attr.colorOnPrimary) -> theme.colorOnPrimary
            context.findAttrFinalResourceId(R.attr.colorSecondary),
            context.findAttrFinalResourceId(R.attr.colorAccent) -> theme.colorSecondary
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
