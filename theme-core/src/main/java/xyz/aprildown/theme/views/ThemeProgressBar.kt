package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.setTint

class ThemeProgressBar(
    context: Context?,
    attrs: AttributeSet? = null
) : ProgressBar(context, attrs) {

    init {
        invalidateColors(get().colorAccent)
    }

    private fun invalidateColors(color: Int) = setTint(color)
}
