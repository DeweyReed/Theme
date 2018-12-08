package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.setTint

internal class ThemeSeekBar(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatSeekBar(context, attrs) {

    init {
        val wizard = AttrWizard(context, attrs)
        val backgroundColorValue = wizard.getRawValue(android.R.attr.background)

        val theme = get(context)
        theme.colorForAttrName(backgroundColorValue, theme.colorAccent)?.let {
            invalidateColors(it, theme.isDark)
        }
    }

    private fun invalidateColors(color: Int, isDark: Boolean) = setTint(color, isDark)
}
