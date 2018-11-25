package xyz.aprildown.theme.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.adjustAlpha

internal class ThemeBorderlessButton(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    init {
        invalidateColors(get(context).colorAccent)
    }

    private fun invalidateColors(accentColor: Int) {
        val textColorSl = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled)
            ),
            intArrayOf(
                accentColor,
                accentColor.adjustAlpha(0.56f)
            )
        )
        setTextColor(textColorSl)

        // Hack around button color not updating
        isEnabled = !isEnabled
        isEnabled = !isEnabled
    }
}
