package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.tint.EdgeEffectTint

internal class ThemeRecyclerView(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        EdgeEffectTint.setEdgeGlowColor(this, get(context).colorAccent)
    }

}
