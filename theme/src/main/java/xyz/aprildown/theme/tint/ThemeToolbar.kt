package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.content.withStyledAttributes
import androidx.core.view.MenuItemCompat
import androidx.core.view.forEach
import com.google.android.material.appbar.MaterialToolbar
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.colorStateList
import xyz.aprildown.theme.utils.drawable
import xyz.aprildown.theme.utils.float
import xyz.aprildown.theme.utils.setMaterialBackgroundColor
import xyz.aprildown.theme.utils.tinted
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/TopAppBar.md
 * [R.style.Widget_MaterialComponents_Toolbar]
 * [R.style.Widget_MaterialComponents_Toolbar_Primary]
 * [R.style.Widget_MaterialComponents_Toolbar_Surface]
 */
internal class ThemeToolbar(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialToolbar(context, attrs) {

    internal var colorOnToolbar: ColorStateList? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.Theme_Toolbar, R.attr.toolbarStyle) {
            ThemeHelper(this@ThemeToolbar, this).initTheme()
        }
    }

    private fun ThemeHelper<ThemeToolbar>.initTheme() {
        val toolbar = view
        val context = toolbar.context
        matchThemeColor(R.styleable.Theme_Toolbar_android_background)?.let {
            /** [MaterialToolbar.initBackground] */
            toolbar.setMaterialBackgroundColor(it)
        }
        matchThemeColor(R.styleable.Theme_Toolbar_navigationIconTint)?.let {
            toolbar.setNavigationIconTint(it)
        }

        /**
         * Here is the problem.
         * Normally, we use AppTheme.AppBarOverlay and AppTheme.PopupOverlay to define toolbar text
         * and icons color. This means we know if the primary color is light before hand.
         * However, with Theme, we don't know that, so we have to calculate at run time.
         */

        fun tintToolbarMenu(color: ColorStateList) {
            colorOnToolbar = color
            toolbar.setTitleTextColor(color)
            navigationIcon = navigationIcon
            overflowIcon = overflowIcon
        }

        withColorOrResourceId(
            R.styleable.Theme_Toolbar_titleTextColor,
            applySolidColor = {
                tintToolbarMenu(it.toColorStateList())
            },
            applyResource = { resourceId ->
                context.colorStateList(resourceId)?.let {
                    tintToolbarMenu(it)
                }
            }
        )

        withColorOrResourceId(
            R.styleable.Theme_Toolbar_subtitleTextColor,
            applySolidColor = {
                toolbar.setSubtitleTextColor(it)
            },
            applyResource = { resourceId ->
                when (resourceId) {
                    R.color.material_on_primary_emphasis_medium -> {
                        toolbar.setSubtitleTextColor(material_on_primary_emphasis_medium(context))
                    }
                }
            }
        )
    }

    override fun setNavigationIcon(resId: Int) {
        navigationIcon = context.drawable(resId)
    }

    override fun setNavigationIcon(icon: Drawable?) {
        super.setNavigationIcon(icon?.tintedNavigationIcon())
    }

    private fun Drawable.tintedNavigationIcon(): Drawable {
        val color = colorOnToolbar ?: return this
        return when (this) {
            is DrawerArrowDrawable -> {
                this.color = color.defaultColor
                this
            }
            else -> {
                tinted(color)
            }
        }
    }

    override fun setOverflowIcon(icon: Drawable?) {
        val currentToolbarTextColor = colorOnToolbar
        if (currentToolbarTextColor == null) {
            super.setOverflowIcon(icon)
        } else {
            super.setOverflowIcon(icon?.tinted(currentToolbarTextColor))
            menu.tintMenu(currentToolbarTextColor)
        }
    }

    /**
     * I notice that menu items will be created after getMenu is called
     * so I post a runnable to tint them.
     */
    override fun getMenu(): Menu {
        try {
            return super.getMenu()
        } finally {
            removeCallbacks(updateMenuRunnable)
            post(updateMenuRunnable)
        }
    }

    private val updateMenuRunnable = Runnable {
        colorOnToolbar?.let {
            super.getMenu().tintMenu(it)
        }
    }
}

/** [R.color.material_on_primary_emphasis_medium] */
private fun material_on_primary_emphasis_medium(context: Context): ColorStateList {
    return ColorStateList(
        arrayOf(intArrayOf()),
        intArrayOf(
            Theme.get().colorOnPrimary
                .adjustAlpha(context.float(R.dimen.material_emphasis_medium))
        )
    )
}

internal fun Menu.tintMenu(csl: ColorStateList) {
    fun MenuItem.tint(csl: ColorStateList) {
        if (MenuItemCompat.getIconTintList(this) == null) {
            MenuItemCompat.setIconTintList(this, csl)
        }
    }

    fun MenuItem.tintSubMenu(csl: ColorStateList) {
        subMenu?.let { subMenu ->
            subMenu.forEach { subMenuItem ->
                subMenuItem.tint(csl)
                subMenuItem.tintSubMenu(csl)
            }
        }
    }

    forEach { menuItem ->
        menuItem.tint(csl)
        menuItem.tintSubMenu(csl)
    }
}
