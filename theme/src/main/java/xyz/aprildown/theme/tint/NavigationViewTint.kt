package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.InsetDrawable
import androidx.core.view.ViewCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/NavigationView.md
 * [R.style.Widget_MaterialComponents_NavigationView]
 */
internal class NavigationViewTint : BaseTint<NavigationView>(
    attrs = R.styleable.Theme_NavigationView,
    defStyleAttr = R.attr.navigationViewStyle,
    onTint = {
        val nav = view
        val context = nav.context
        matchThemeColor(R.styleable.Theme_NavigationView_android_background)?.let {
            ViewCompat.setBackgroundTintList(nav, it.toColorStateList())
        }
        withColorOrResourceId(
            R.styleable.Theme_NavigationView_itemIconTint,
            applySolidColor = {
                nav.itemIconTintList = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_navigation_item_icon_tint -> {
                        nav.itemIconTintList = mtrl_navigation_item_icon_tint(context)
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
                        nav.itemTextColor = mtrl_navigation_item_text_color(context)
                    }
                }
            }
        )
    }
)

/** [R.color.mtrl_navigation_item_icon_tint] */
private fun mtrl_navigation_item_icon_tint(context: Context): ColorStateList {
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

/** [R.color.mtrl_navigation_item_background_color] */
private fun mtrl_navigation_item_background_color(): ColorStateList {
    val colorPrimaryAlpha12 = Theme.get().colorPrimary.adjustAlpha(0.12f)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_activated),
            intArrayOf(android.R.attr.state_checked),
            intArrayOf()
        ),
        intArrayOf(
            colorPrimaryAlpha12,
            colorPrimaryAlpha12,
            Color.TRANSPARENT
        )
    )
}

/** [R.color.mtrl_navigation_item_text_color] */
private fun mtrl_navigation_item_text_color(context: Context): ColorStateList {
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
