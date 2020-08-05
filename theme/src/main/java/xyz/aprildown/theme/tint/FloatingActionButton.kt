package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.float
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/FloatingActionButton.md
 */
internal class FloatingActionButtonTint : BaseTint<FloatingActionButton>(
    attrs = R.styleable.Theme_FloatingActionButton,
    defStyleAttr = R.attr.floatingActionButtonStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_FloatingActionButton
        val fab = view
        withColorOrResourceId(
            R.styleable.Theme_FloatingActionButton_backgroundTint,
            applySolidColor = {
                ViewCompat.setBackgroundTintList(fab, it.toColorStateList())
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_fab_bg_color_selector -> {
                        ViewCompat.setBackgroundTintList(fab, fab.mtrl_fab_bg_color_selector())
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_FloatingActionButton_tint,
            applySolidColor = {
                ImageViewCompat.setImageTintList(fab, it.toColorStateList())
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_fab_icon_text_color_selector -> {
                        ImageViewCompat.setImageTintList(
                            fab,
                            fab.mtrl_fab_icon_text_color_selector()
                        )
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_FloatingActionButton_rippleColor,
            applySolidColor = {
                fab.rippleColor = it
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_fab_ripple_color -> {
                        fab.setRippleColor(fab.mtrl_fab_ripple_color())
                    }
                }
            }
        )
    }
)

// R.color.mtrl_fab_bg_color_selector
private fun View.mtrl_fab_bg_color_selector(): ColorStateList {
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

// R.color.mtrl_fab_ripple_color
private fun View.mtrl_fab_ripple_color(): ColorStateList {
    val context = context
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
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_pressed_alpha)),
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_focused_alpha)),
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_focused_alpha)),
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_hovered_alpha)),
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_default_alpha))
        )
    )
}

// R.color.mtrl_fab_icon_text_color_selector
private fun View.mtrl_fab_icon_text_color_selector(): ColorStateList {
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
                    R.color.mtrl_fab_icon_text_color_selector -> {
                        fab.setTextColor(fab.mtrl_fab_icon_text_color_selector())
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
                    R.color.mtrl_fab_bg_color_selector -> {
                        ViewCompat.setBackgroundTintList(
                            fab,
                            fab.mtrl_fab_bg_color_selector()
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
                    R.color.mtrl_fab_icon_text_color_selector -> {
                        fab.iconTint = fab.mtrl_fab_icon_text_color_selector()
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
                    R.color.mtrl_fab_ripple_color -> {
                        fab.rippleColor = fab.mtrl_fab_ripple_color()
                    }
                }
            }
        )
    }
)
