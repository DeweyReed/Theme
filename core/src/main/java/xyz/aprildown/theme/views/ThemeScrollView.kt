package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.EdgeGlowUtil.setEdgeGlowColor

internal class ThemeScrollView(
    context: Context?,
    attrs: AttributeSet? = null
) : ScrollView(context, attrs) {

    init {
        invalidateColors(get().colorAccent)
    }

    private fun invalidateColors(color: Int) = setEdgeGlowColor(this, color)
}
