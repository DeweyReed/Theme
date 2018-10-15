package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.EdgeGlowUtil.setEdgeGlowColor

class ThemeListView(
    context: Context,
    attrs: AttributeSet? = null
) : ListView(context, attrs) {

    init {
        invalidateColors(get().colorAccent)
    }

    private fun invalidateColors(color: Int) = setEdgeGlowColor(this, color)
}
