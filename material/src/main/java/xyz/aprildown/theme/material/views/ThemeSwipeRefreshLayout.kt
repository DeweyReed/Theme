package xyz.aprildown.theme.material.views

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import xyz.aprildown.theme.Theme.Companion.get

internal class ThemeSwipeRefreshLayout(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    init {
        setColorSchemeColors(get().colorAccent)
    }
}
