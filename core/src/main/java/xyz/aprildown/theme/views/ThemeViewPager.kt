package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.EdgeGlowUtil.setEdgeGlowColor

internal class ThemeViewPager(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    init {
        invalidateColors(get(context).colorAccent)
    }

    private fun invalidateColors(color: Int) = setEdgeGlowColor(this, color)
}
