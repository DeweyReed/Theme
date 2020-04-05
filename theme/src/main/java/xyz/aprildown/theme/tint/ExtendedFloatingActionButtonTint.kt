package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 *https://github.com/material-components/material-components-android/blob/master/docs/components/ExtendedFloatingActionButton.md
 */
internal class ExtendedFloatingActionButtonTint : BaseTint<ExtendedFloatingActionButton>(
    attrs = R.styleable.Theme_ExtendedFloatingActionButton,
    defStyleAttr = R.attr.extendedFloatingActionButtonStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_ExtendedFloatingActionButton
        // R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon
        val fab = view
        withColorOrResourceId(
            R.styleable.Theme_ExtendedFloatingActionButton_android_textColor,
            applySolidColor = {
                fab.setTextColor(it)
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_extended_fab_text_color_selector -> {
                        fab.setTextColor(fab.mtrl_extended_fab_text_color_selector())
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_ExtendedFloatingActionButton_backgroundTint,
            applySolidColor = {
                ViewCompat.setBackgroundTintList(fab, it.toColorStateList())
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_extended_fab_bg_color_selector -> {
                        ViewCompat.setBackgroundTintList(
                            fab,
                            fab.mtrl_extended_fab_bg_color_selector()
                        )
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_ExtendedFloatingActionButton_iconTint,
            applySolidColor = {
                fab.iconTint = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_extended_fab_text_color_selector -> {
                        fab.iconTint = fab.mtrl_extended_fab_text_color_selector()
                    }
                }
            }
        )
        matchThemeColor(R.styleable.Theme_ExtendedFloatingActionButton_strokeColor)?.let {
            fab.strokeColor = it.toColorStateList()
        }
        withColorOrResourceId(
            R.styleable.Theme_ExtendedFloatingActionButton_rippleColor,
            applySolidColor = {
                fab.rippleColor = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_extended_fab_ripple_color -> {
                        fab.rippleColor = mtrl_extended_fab_ripple_color()
                    }
                }
            }
        )
    }
)

// R.color.mtrl_extended_fab_text_color_selector
private fun View.mtrl_extended_fab_text_color_selector(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorOnSecondary,
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.38f)
        )
    )
}

// R.color.mtrl_extended_fab_bg_color_selector
private fun View.mtrl_extended_fab_bg_color_selector(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorSecondary,
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.12f)
        )
    )
}

// R.color.mtrl_extended_fab_ripple_color
private fun mtrl_extended_fab_ripple_color(): ColorStateList {
    val colorOnSecondary = Theme.get().colorOnSecondary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf()
        ),
        intArrayOf(
            colorOnSecondary.adjustAlpha(0.32f),
            colorOnSecondary.adjustAlpha(0.24f),
            colorOnSecondary.adjustAlpha(0.24f),
            colorOnSecondary.adjustAlpha(0.8f),
            colorOnSecondary.adjustAlpha(0f)
        )
    )
}
