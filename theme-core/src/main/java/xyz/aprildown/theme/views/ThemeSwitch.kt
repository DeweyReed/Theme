package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch
import xyz.aprildown.theme.ColorIsDarkState
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.setTint

class ThemeSwitch(
    context: Context,
    attrs: AttributeSet? = null
) : Switch(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)

    init {
        invalidateColors(
            ColorIsDarkState(
                get().colorForAttrName(backgroundColorValue, get().colorAccent)!!,
                get().isDark
            )
        )
    }

    private fun invalidateColors(state: ColorIsDarkState) = setTint(state.color, state.isDark)
}
