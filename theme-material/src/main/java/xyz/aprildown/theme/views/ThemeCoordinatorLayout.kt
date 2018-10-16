package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.toolbarTitleColor

internal class ThemeCoordinatorLayout(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs) {
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (childCount > 0 && getChildAt(0) is AppBarLayout) {
            val appBarLayout = getChildAt(0) as AppBarLayout
            if (appBarLayout.childCount > 0
                && appBarLayout.getChildAt(0) is CollapsingToolbarLayout
            ) {
                collapsingToolbarLayout = appBarLayout.getChildAt(0) as CollapsingToolbarLayout
            }
        }

        collapsingToolbarLayout?.run {
            val color = get().colorPrimary
            setContentScrimColor(color)
            setStatusBarScrimColor(color)
            val textColor = get().toolbarTitleColor
            setCollapsedTitleTextColor(textColor)
            setExpandedTitleColor(textColor)
        }
    }
}