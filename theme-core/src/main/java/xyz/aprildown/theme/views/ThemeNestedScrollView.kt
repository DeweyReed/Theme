package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.EdgeGlowUtil.setEdgeGlowColor

internal class ThemeNestedScrollView(
    context: Context,
    attrs: AttributeSet? = null
) : NestedScrollView(context, attrs) {

    init {
        invalidateColors(get().colorAccent)
    }

    private fun invalidateColors(color: Int) = setEdgeGlowColor(this, color)
}
