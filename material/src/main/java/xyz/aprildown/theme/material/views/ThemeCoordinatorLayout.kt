package xyz.aprildown.theme.material.views

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import xyz.aprildown.theme.Theme
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

        val theme = Theme.get()
        collapsingToolbarLayout?.run {
            val color = theme.colorPrimary
            setContentScrimColor(color)
            setStatusBarScrimColor(color)
            val textColor = theme.toolbarTitleColor
            setCollapsedTitleTextColor(textColor)
            setExpandedTitleColor(textColor)
        }
    }
}