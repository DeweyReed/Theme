package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.tint.EdgeEffectTint

internal class ThemeListView(
    context: Context,
    attrs: AttributeSet? = null
) : ListView(context, attrs) {

    init {
        EdgeEffectTint.setEdgeGlowColor(this, get(context).colorAccent)
    }

}
