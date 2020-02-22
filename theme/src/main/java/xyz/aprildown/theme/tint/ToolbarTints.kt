package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.colorStateList
import xyz.aprildown.theme.utils.float
import xyz.aprildown.theme.utils.getMaterialBackgroundColor
import xyz.aprildown.theme.utils.isLightColor
import xyz.aprildown.theme.utils.setMaterialBackgroundColor
import xyz.aprildown.theme.utils.tinted
import xyz.aprildown.theme.utils.toColorStateList
import xyz.aprildown.theme.utils.withAlpha

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/AppBarLayout.md
 */
internal class AppBarLayoutTint : BaseTint<AppBarLayout>(
    attrs = R.styleable.Theme_AppBarLayout,
    defStyleAttr = R.attr.appBarLayoutStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_AppBarLayout_Surface
        // R.style.Widget_MaterialComponents_AppBarLayout_Primary
        val appBarLayout = view
        matchThemeColor(R.styleable.Theme_AppBarLayout_android_background)?.let {
            appBarLayout.setMaterialBackgroundColor(it)
        }
    }
)

private fun View.findAppBarLayout(): AppBarLayout? {
    var parent = parent
    while (parent != null && parent !is AppBarLayout) {
        parent = parent.parent
    }
    return parent as? AppBarLayout
}

internal class CollapsingToolbarLayoutTint : BaseTint<CollapsingToolbarLayout>(
    attrs = R.styleable.Theme_CollapsingToolbarLayout,
    onTint = {
        // R.style.Widget_Design_CollapsingToolbar
        val ctl = view
        val context = ctl.context
        val isPrimaryLight = Theme.get().isPrimaryLight
        var isCollapsedToolbarLight = isPrimaryLight
        matchThemeColor(R.styleable.Theme_CollapsingToolbarLayout_contentScrim)?.let {
            isCollapsedToolbarLight = it.isLightColor
            ctl.setContentScrimColor(it)
        }
        matchThemeColor(R.styleable.Theme_CollapsingToolbarLayout_statusBarScrim)?.let {
            ctl.setStatusBarScrimColor(it)
        }

        // This is hack and ignores your setters in the activity's onCreate.
        withColorOrResourceId(
            R.styleable.Theme_CollapsingToolbarLayout_expandedTitleTextAppearance,
            applyDefault = {
                // Post because AppBarLayout isn't available now.
                ctl.post {
                    val isAppbarLayoutLight =
                        ctl.findAppBarLayout()?.getMaterialBackgroundColor()?.isLightColor
                            ?: isPrimaryLight
                    context.textColorOnToolbar(isAppbarLayoutLight)?.let {
                        ctl.setExpandedTitleTextColor(it)
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_CollapsingToolbarLayout_collapsedTitleTextAppearance,
            applyDefault = {
                context.textColorOnToolbar(isCollapsedToolbarLight)?.let {
                    ctl.setCollapsedTitleTextColor(it)
                }
            }
        )
    }
)

private var isNearestToolbarBackgroundLight = false

internal class ToolbarTint : BaseTint<Toolbar>(
    attrs = R.styleable.Theme_Toolbar,
    defStyleAttr = R.attr.toolbarStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_Toolbar
        // R.style.Widget_MaterialComponents_Toolbar_Primary
        // R.style.Widget_MaterialComponents_Toolbar_Surface
        val toolbar = view
        val context = toolbar.context
        isNearestToolbarBackgroundLight = Theme.get().isPrimaryLight
        matchThemeColor(R.styleable.Theme_Toolbar_android_background)?.let {
            isNearestToolbarBackgroundLight = it.isLightColor
            // Check MaterialToolbar.initBackground
            toolbar.setMaterialBackgroundColor(it)
        }
        /**
         * Here is the problem.
         * Normally, we use AppTheme.AppBarOverlay and AppTheme.PopupOverlay to define toolbar text
         * and icons color. This means we know if the primary color is light before hand.
         * However, with Theme, we don't know that so we have to calculate at run time.
         */

        fun tintToolbarMenu(color: ColorStateList) {
            toolbar.setTitleTextColor(color)
            // The navigationIcon may not be inflated now, post it.
            // But it'll override any tint in the Activity's onCreate
            val navigationIcon = toolbar.navigationIcon
            if (navigationIcon != null) {
                toolbar.navigationIcon = navigationIcon.tinted(color)
            } else {
                toolbar.post {
                    toolbar.navigationIcon?.let {
                        toolbar.navigationIcon = it.tinted(color)
                    }
                }
            }
            toolbar.post {
                // Use the same color.
                toolbar.overflowIcon?.let {
                    toolbar.overflowIcon = it.tinted(color)
                }
                toolbar.menu.tintMenu(color)
            }
        }

        withColorOrResourceId(
            R.styleable.Theme_Toolbar_titleTextColor,
            applySolidColor = {
                toolbar.setTitleTextColor(it)
                tintToolbarMenu(it.toColorStateList())
            },
            applyResource = { resourceId ->
                if (resourceId == R.color.abc_primary_text_material_dark ||
                    resourceId == R.color.abc_primary_text_material_light
                ) {
                    context.textColorOnToolbar(isNearestToolbarBackgroundLight)?.let { color ->
                        tintToolbarMenu(color)
                    }
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
                        toolbar.setSubtitleTextColor(toolbar.material_on_primary_emphasis_medium())
                    }
                    R.color.abc_secondary_text_material_dark,
                    R.color.abc_secondary_text_material_light -> {
                        toolbar.context.colorStateList(
                            if (isNearestToolbarBackgroundLight) {
                                R.color.abc_secondary_text_material_light
                            } else {
                                R.color.abc_secondary_text_material_dark
                            }
                        )?.let {
                            toolbar.setSubtitleTextColor(it)
                        }
                    }
                }
            }
        )
    }
)

// R.color.material_on_primary_emphasis_medium
private fun View.material_on_primary_emphasis_medium(): ColorStateList {
    return ColorStateList(
        arrayOf(intArrayOf()),
        intArrayOf(Theme.get().colorOnPrimary withAlpha context.float(R.dimen.material_emphasis_medium))
    )
}

internal fun Context.textColorOnToolbar(isToolbarLight: Boolean): ColorStateList? {
    return colorStateList(
        if (isToolbarLight) {
            // Weired names.
            R.color.abc_primary_text_material_light
        } else {
            R.color.abc_primary_text_material_dark
        }
    )
}

internal fun Menu.tintMenu(csl: ColorStateList) {
    fun MenuItem.tint(csl: ColorStateList) {
        icon?.let {
            icon = icon.tinted(csl)
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

internal fun Menu.tintMenuWithHack(context: Context) {
    context.textColorOnToolbar(isNearestToolbarBackgroundLight)?.let {
        tintMenu(it)
    }
}
