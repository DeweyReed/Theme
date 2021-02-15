package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.view.ViewCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.colorStateList
import xyz.aprildown.theme.utils.setMaterialBackgroundColor
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.tinted
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/BottomAppBar.md
 * [R.style.Widget_MaterialComponents_BottomAppBar]
 * [R.style.Widget_MaterialComponents_BottomAppBar_Colored]
 * [R.style.Widget_MaterialComponents_BottomAppBar_PrimarySurface]
 */
internal class BottomAppBarTint : BaseTint<BottomAppBar>(
    attrs = R.styleable.Theme_BottomAppBar,
    defStyleAttr = R.attr.bottomAppBarStyle,
    onTint = {
        val bottomAppBar = view
        val context = bottomAppBar.context
        matchThemeColor(R.styleable.Theme_BottomAppBar_backgroundTint)?.let {
            bottomAppBar.backgroundTint = it.toColorStateList()
        }

        /**
         * [R.style.Widget_MaterialComponents_BottomAppBar] doesn't have a materialThemeOverlay
         * to define colorControlNormal so we have to do it on our own.
         * If we can find colorControlNormal, it means we're using Colored.
         * If we can't find it, we use [R.style.ThemeOverlay_MaterialComponents_BottomAppBar_Surface].
         */
        (findAttributeColor(R.attr.colorControlNormal)?.toColorStateList()
            ?: context.colorStateList(R.color.material_on_surface_emphasis_medium))?.let { colorOnBar ->
            bottomAppBar.navigationIcon?.let {
                bottomAppBar.navigationIcon = it.tinted(colorOnBar)
            }
            bottomAppBar.overflowIcon?.let {
                bottomAppBar.overflowIcon = it.tinted(colorOnBar)
            }
            bottomAppBar.menu.tintMenu(colorOnBar)
        }
    }
)

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/BottomNavigationView.md
 * [R.style.Widget_MaterialComponents_BottomNavigationView]
 * [R.style.Widget_MaterialComponents_BottomNavigationView_Colored]
 */
internal class BottomNavigationViewTint : BaseTint<BottomNavigationView>(
    attrs = R.styleable.Theme_BottomNavigationView,
    defStyleAttr = R.attr.bottomNavigationStyle,
    onTint = {
        val bottomNavigationView = view
        matchThemeColor(R.styleable.Theme_BottomNavigationView_android_background)?.let {
            bottomNavigationView.setMaterialBackgroundColor(it)
        }
        matchThemeColor(R.styleable.Theme_BottomNavigationView_backgroundTint)?.let {
            ViewCompat.setBackgroundTintList(bottomNavigationView, it.toColorStateList())
        }
        withColorOrResourceId(
            R.styleable.Theme_BottomNavigationView_itemIconTint,
            applySolidColor = {
                bottomNavigationView.itemIconTintList = it.toColorStateList()
            },
            applyResource = {
                bottomNavigationView.withItemIconTint(it)
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_BottomNavigationView_itemTextColor,
            applySolidColor = {
                bottomNavigationView.itemTextColor = it.toColorStateList()
            },
            applyResource = {
                bottomNavigationView.withItemTextTint(it)
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_BottomNavigationView_itemRippleColor,
            applySolidColor = {
                bottomNavigationView.itemRippleColor = it.toColorStateList()
            },
            applyResource = {
                bottomNavigationView.withItemRippleTint(it)
            }
        )
    }
)

private fun BottomNavigationView.withItemIconTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_bottom_nav_item_tint -> {
            itemIconTintList = mtrl_bottom_nav_item_tint(context)
        }
        R.color.mtrl_bottom_nav_colored_item_tint -> {
            itemIconTintList = mtrl_bottom_nav_colored_item_tint()
        }
    }
}

/** [R.color.mtrl_bottom_nav_item_tint] */
private fun mtrl_bottom_nav_item_tint(context: Context): ColorStateList {
    val theme = Theme.get()
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf()
        ),
        intArrayOf(
            theme.colorPrimary,
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.6f)
        )
    )
}

/** [R.color.mtrl_bottom_nav_colored_item_tint] */
private fun mtrl_bottom_nav_colored_item_tint(): ColorStateList {
    val colorOnPrimary = Theme.get().colorOnPrimary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf()
        ),
        intArrayOf(
            colorOnPrimary,
            colorOnPrimary.adjustAlpha(0.6f)
        )
    )
}

private fun BottomNavigationView.withItemTextTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_bottom_nav_item_tint -> {
            itemTextColor = mtrl_bottom_nav_item_tint(context)
        }
        R.color.mtrl_bottom_nav_colored_item_tint -> {
            itemTextColor = mtrl_bottom_nav_colored_item_tint()
        }
    }
}

private fun BottomNavigationView.withItemRippleTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_bottom_nav_ripple_color -> {
            itemRippleColor = mtrl_bottom_nav_ripple_color(context)
        }
        R.color.mtrl_bottom_nav_colored_ripple_color -> {
            itemRippleColor = mtrl_bottom_nav_colored_ripple_color()
        }
    }
}

/** [R.color.mtrl_bottom_nav_ripple_color] */
private fun mtrl_bottom_nav_ripple_color(context: Context): ColorStateList {
    val colorPrimary = Theme.get().colorPrimary
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed, android.R.attr.state_selected),
            intArrayOf(
                android.R.attr.state_focused,
                android.R.attr.state_hovered,
                android.R.attr.state_selected
            ),
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_selected),
            intArrayOf(android.R.attr.state_hovered, android.R.attr.state_selected),
            intArrayOf(android.R.attr.state_selected),

            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf()
        ),
        intArrayOf(
            colorPrimary.adjustAlpha(0.08f),
            colorPrimary.adjustAlpha(0.16f),
            colorPrimary.adjustAlpha(0.12f),
            colorPrimary.adjustAlpha(0.04f),
            colorPrimary.adjustAlpha(0f),

            colorOnSurface.adjustAlpha(0.08f),
            colorOnSurface.adjustAlpha(0.16f),
            colorOnSurface.adjustAlpha(0.12f),
            colorOnSurface.adjustAlpha(0.04f),
            colorOnSurface.adjustAlpha(0f)
        )
    )
}

/** [R.color.mtrl_bottom_nav_colored_ripple_color] */
private fun mtrl_bottom_nav_colored_ripple_color(): ColorStateList {
    val colorOnPrimary = Theme.get().colorOnPrimary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf()
        ),
        intArrayOf(
            colorOnPrimary.adjustAlpha(0.16f),
            colorOnPrimary.adjustAlpha(0.32f),
            colorOnPrimary.adjustAlpha(0.24f),
            colorOnPrimary.adjustAlpha(0.08f),
            colorOnPrimary.adjustAlpha(0f)
        )
    )
}
