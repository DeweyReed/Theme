package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import com.google.android.material.tabs.TabLayout
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.setMaterialBackgroundColor
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Tabs.md
 * [R.style.Widget_MaterialComponents_TabLayout]
 * [R.style.Widget_MaterialComponents_TabLayout_Colored]
 */
internal class TabLayoutTint : BaseTint<TabLayout>(
    attrs = R.styleable.Theme_TabLayout,
    defStyleAttr = R.attr.tabStyle,
    onTint = {
        val tabLayout = view
        matchThemeColor(R.styleable.Theme_TabLayout_android_background)?.let {
            tabLayout.setMaterialBackgroundColor(it)
        }
        withColorOrResourceId(
            R.styleable.Theme_TabLayout_tabIconTint,
            applySolidColor = {
                tabLayout.tabIconTint = it.toColorStateList()
            },
            applyResource = {
                tabLayout.withTabIconTint(it)
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_TabLayout_tabTextColor,
            applySolidColor = {
                tabLayout.tabTextColors = it.toColorStateList()
            },
            applyResource = {
                tabLayout.withTabTextColor(it)
            }
        )
        matchThemeColor(R.styleable.Theme_TabLayout_tabIndicatorColor)?.let {
            tabLayout.setSelectedTabIndicatorColor(it)
        }
        withColorOrResourceId(
            R.styleable.Theme_TabLayout_tabRippleColor,
            applySolidColor = {
                tabLayout.tabRippleColor = it.toColorStateList()
            },
            applyResource = {
                tabLayout.withTabRippleColor(it)
            }
        )
    }
)

private fun TabLayout.withTabIconTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_tabs_icon_color_selector -> {
            mtrl_tabs_icon_color_selector()
        }
        R.color.mtrl_tabs_icon_color_selector_colored -> {
            mtrl_tabs_icon_color_selector_colored()
        }
        else -> null
    }?.let {
        tabIconTint = it
    }
}

private fun TabLayout.withTabTextColor(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_tabs_icon_color_selector -> {
            mtrl_tabs_icon_color_selector()
        }
        R.color.mtrl_tabs_icon_color_selector_colored -> {
            mtrl_tabs_icon_color_selector_colored()
        }
        else -> null
    }?.let {
        tabTextColors = it
    }
}

/** [R.color.mtrl_tabs_icon_color_selector] */
private fun View.mtrl_tabs_icon_color_selector(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_selected),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.6f)
        )
    )
}

/** [R.color.mtrl_tabs_icon_color_selector_colored] */
private fun mtrl_tabs_icon_color_selector_colored(): ColorStateList {
    val colorOnPrimary = Theme.get().colorOnPrimary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_selected),
            intArrayOf()
        ),
        intArrayOf(
            colorOnPrimary,
            colorOnPrimary.adjustAlpha(0.6f)
        )
    )
}

private fun TabLayout.withTabRippleColor(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_tabs_ripple_color -> {
            mtrl_tabs_ripple_color(context)
        }
        R.color.mtrl_tabs_colored_ripple_color -> {
            mtrl_tabs_colored_ripple_color()
        }
        else -> null
    }?.let {
        tabRippleColor = it
    }
}

/** [R.color.mtrl_tabs_ripple_color] */
private fun mtrl_tabs_ripple_color(context: Context): ColorStateList {
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

/** [R.color.mtrl_tabs_colored_ripple_color] */
private fun mtrl_tabs_colored_ripple_color(): ColorStateList {
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
