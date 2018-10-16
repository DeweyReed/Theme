package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import xyz.aprildown.theme.ColorIsDarkState
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.*

internal class ThemeEditText(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val tintColorValue = wizard.getRawValue(R.attr.tint)
    private val textColorValue = wizard.getRawValue(android.R.attr.textColor)
    private val textColorHintValue = wizard.getRawValue(android.R.attr.textColorHint)

    init {
        get().colorForAttrName(tintColorValue, get().colorAccent)?.let {
            invalidateColors(ColorIsDarkState(it, get().isDark))
        }

        get().colorForAttrName(textColorValue, context.textColorPrimary)?.let {
            changeTextColor(it)
        }

        get().colorForAttrName(textColorHintValue, context.textColorSecondary)?.let {
            changeHintTextColor(it)
        }
    }

    private fun invalidateColors(state: ColorIsDarkState) =
        setTintAuto(state.color, true, state.isDark)
}
