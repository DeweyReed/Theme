package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.drawerlayout.widget.DrawerLayout
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.toolbarIconColor

internal class ThemeDrawerLayout(
    context: Context,
    attrs: AttributeSet? = null
) : DrawerLayout(context, attrs) {

    private var lastColor: Int? = null
    private var arrowDrawable: DrawerArrowDrawable? = null

    init {
        invalidateColor(get(context).toolbarIconColor)
    }

    private fun invalidateColor(color: Int?) {
        if (color == null) {
            return
        }
        this.lastColor = color
        this.arrowDrawable?.color = color
    }

    override fun addDrawerListener(listener: DrawerLayout.DrawerListener) {
        super.addDrawerListener(listener)
        if (listener is ActionBarDrawerToggle) {
            this.arrowDrawable = listener.drawerArrowDrawable
        }
        invalidateColor(lastColor)
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun setDrawerListener(listener: DrawerLayout.DrawerListener) {
        super.setDrawerListener(listener)
        if (listener is ActionBarDrawerToggle) {
            this.arrowDrawable = listener.drawerArrowDrawable
        }
        invalidateColor(lastColor)
    }
}
