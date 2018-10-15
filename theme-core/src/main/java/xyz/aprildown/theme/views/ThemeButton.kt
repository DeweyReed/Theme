package xyz.aprildown.theme.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import xyz.aprildown.theme.ColorIsDarkState
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.isColorLight
import xyz.aprildown.theme.utils.setTintAuto

internal class ThemeButton(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

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

    private fun invalidateColors(state: ColorIsDarkState) {
        setTintAuto(state.color, true, state.isDark)
        val textColorSl = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled)
            ),
            intArrayOf(
                if (state.color.isColorLight()) BLACK else WHITE,
                if (state.isDark) WHITE else BLACK
            )
        )
        setTextColor(textColorSl)

        // Hack around button color not updating
        isEnabled = !isEnabled
        isEnabled = !isEnabled
    }
}
