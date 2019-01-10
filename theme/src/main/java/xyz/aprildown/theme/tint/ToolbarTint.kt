package xyz.aprildown.theme.tint

import android.graphics.PorterDuff
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import xyz.aprildown.theme.utils.tint

internal class ToolbarTint private constructor() {

    init {
        throw IllegalStateException()
    }

    companion object {

        fun setOverflowButtonColor(toolbar: Toolbar, @ColorInt color: Int) {
            val overflowDrawable = toolbar.overflowIcon
            if (overflowDrawable != null) {
                toolbar.overflowIcon = overflowDrawable.tint(color)
            }
        }

        fun tintMenu(
            menu: Menu,
            @ColorInt menuIconColor: Int,
            @ColorInt subIconColor: Int = menuIconColor
        ) {
            for (i in 0 until menu.size()) {
                val item = menu.getItem(i)
                colorMenuItem(item, menuIconColor)
                colorSubMenus(item, subIconColor)
            }
        }

        /**
         * Sets the color filter and/or the alpha transparency on a [MenuItem]'s icon.
         *
         * @param menuItem The [MenuItem] to theme.
         * @param color The color to set for the color filter or `null` for no changes.
         */
        private fun colorMenuItem(menuItem: MenuItem, color: Int?) {
            menuItem.icon?.let { icon ->
                val drawable = icon.mutate()
                color?.let { drawable.setColorFilter(it, PorterDuff.Mode.SRC_IN) }
                menuItem.icon = drawable
            }
        }

        /**
         * Sets the color filter and/or the alpha transparency on a [MenuItem]'s sub menus
         *
         * @param item The [MenuItem] to theme.
         * @param color The color to set for the color filter or `null` for no changes.
         */
        private fun colorSubMenus(item: MenuItem, color: Int?) {
            if (item.hasSubMenu()) {
                item.subMenu?.let { menu ->
                    val size = menu.size()
                    for (i in 0 until size) {
                        val menuItem = menu.getItem(i)
                        colorMenuItem(menuItem, color)
                        colorSubMenus(menuItem, color)
                    }
                }
            }
        }
    }
}