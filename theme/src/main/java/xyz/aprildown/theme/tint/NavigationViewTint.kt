package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.InsetDrawable
import android.view.View
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/NavigationView.md
 */
internal class NavigationViewTint : BaseTint<NavigationView>(
    attrs = R.styleable.Theme_NavigationView,
    defStyleAttr = R.attr.navigationViewStyle,
    onTint = {
        // Why is this style private?
        // R.style.Widget_MaterialComponents_NavigationView
        val nav = view
        withColorOrResourceId(
            R.styleable.Theme_NavigationView_itemIconTint,
            applySolidColor = {
                nav.itemIconTintList = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_navigation_item_icon_tint -> {
                        nav.itemIconTintList = nav.mtrl_navigation_item_icon_tint()
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_NavigationView_itemShapeFillColor,
            applySolidColor = {
                ((nav.itemBackground as? InsetDrawable)?.drawable as? MaterialShapeDrawable)
                    ?.fillColor = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_navigation_item_background_color -> {
                        ((nav.itemBackground as? InsetDrawable)?.drawable as? MaterialShapeDrawable)
                            ?.fillColor = mtrl_navigation_item_background_color()
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_NavigationView_itemTextColor,
            applySolidColor = {
                nav.itemTextColor = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_navigation_item_text_color -> {
                        nav.itemTextColor = nav.mtrl_navigation_item_text_color()
                    }
                }
            }
        )
    }
)

// R.color.mtrl_navigation_item_icon_tint
private fun View.mtrl_navigation_item_icon_tint(): ColorStateList {
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            colorOnSurface.adjustAlpha(0.38f),
            colorOnSurface
        )
    )
}

// R.color.mtrl_navigation_item_background_color
private fun mtrl_navigation_item_background_color(): ColorStateList {
    val colorPrimary12 = Theme.get().colorPrimary.adjustAlpha(0.12f)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_activated),
            intArrayOf(android.R.attr.state_checked),
            intArrayOf()
        ),
        intArrayOf(
            colorPrimary12,
            colorPrimary12,
            Color.TRANSPARENT
        )
    )
}

// R.color.mtrl_navigation_item_text_color
private fun View.mtrl_navigation_item_text_color(): ColorStateList {
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            colorOnSurface.adjustAlpha(0.38f),
            colorOnSurface
        )
    )
}
