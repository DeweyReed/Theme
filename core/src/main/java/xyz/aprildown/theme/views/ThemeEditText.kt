package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import xyz.aprildown.theme.ColorIsDarkState
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.setTintAuto
import xyz.aprildown.theme.utils.textColorPrimary
import xyz.aprildown.theme.utils.textColorSecondary

internal class ThemeEditText(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        val wizard = AttrWizard(context, attrs)
        val tintColorValue = wizard.getRawValue(R.attr.tint)
        val textColorValue = wizard.getRawValue(android.R.attr.textColor)
        val textColorHintValue = wizard.getRawValue(android.R.attr.textColorHint)

        val theme = get(context)

        theme.colorForAttrName(tintColorValue, theme.colorAccent)?.let {
            invalidateColors(ColorIsDarkState(it, theme.isDark))
        }

        theme.colorForAttrName(textColorValue, context.textColorPrimary)?.let {
            setTextColor(it)
        }

        theme.colorForAttrName(textColorHintValue, context.textColorSecondary)?.let {
            setHintTextColor(it)
        }
    }

    private fun invalidateColors(state: ColorIsDarkState) =
        setTintAuto(state.color, true, state.isDark)
}
