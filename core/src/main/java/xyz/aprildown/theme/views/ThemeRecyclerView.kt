package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.EdgeGlowUtil.setEdgeGlowColor

internal class ThemeRecyclerView(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        invalidateColors(get(context).colorAccent)
    }

    private fun invalidateColors(color: Int) = setEdgeGlowColor(this, color, null)
}
