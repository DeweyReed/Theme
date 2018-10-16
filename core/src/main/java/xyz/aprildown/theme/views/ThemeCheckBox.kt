package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import xyz.aprildown.theme.ColorIsDarkState
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.setTint

internal class ThemeCheckBox(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatCheckBox(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)

    init {
        get().colorForAttrName(backgroundColorValue, get().colorAccent)?.let {
            invalidateColors(ColorIsDarkState(it, get().isDark))
        }
    }

    private fun invalidateColors(state: ColorIsDarkState) = setTint(state.color, state.isDark)
}
