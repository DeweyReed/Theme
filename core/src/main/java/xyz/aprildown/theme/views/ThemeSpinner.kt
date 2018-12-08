package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.setTintAuto

internal class ThemeSpinner(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatSpinner(context, attrs) {

    init {
        val wizard = AttrWizard(context, attrs)
        val backgroundColorValue = wizard.getRawValue(android.R.attr.background)

        val theme = get(context)
        theme.colorForAttrName(backgroundColorValue, theme.colorAccent)?.let {
            invalidateColors(it, theme.isDark)
        }
    }

    private fun invalidateColors(color: Int, isDark: Boolean) = setTintAuto(color, true, isDark)
}
