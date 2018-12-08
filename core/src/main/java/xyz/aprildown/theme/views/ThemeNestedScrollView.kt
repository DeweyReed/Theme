package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.tint.EdgeEffectTint

internal class ThemeNestedScrollView(
    context: Context,
    attrs: AttributeSet? = null
) : NestedScrollView(context, attrs) {

    init {
        EdgeEffectTint.setEdgeGlowColor(this, get(context).colorAccent)
    }

}
