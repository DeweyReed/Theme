package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner
import xyz.aprildown.theme.ColorIsDarkState
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.setTintAuto

internal class ThemeSpinner(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatSpinner(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)

    init {
        get().colorForAttrName(backgroundColorValue, get().colorAccent)?.let {
            invalidateColors(ColorIsDarkState(it, get().isDark))
        }
    }

    private fun invalidateColors(state: ColorIsDarkState) =
        setTintAuto(state.color, true, state.isDark)
}
