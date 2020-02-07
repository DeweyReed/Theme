package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.view.View
import com.google.android.material.chip.Chip
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.float
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Chip.md
 */
internal class ChipTint : BaseTint<Chip>(
    attrs = R.styleable.Theme_Chip,
    defStyleAttr = R.attr.chipStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_Chip_Entry
        // R.style.Widget_MaterialComponents_Chip_Action
        // R.style.Widget_MaterialComponents_Chip_Choice
        // R.style.Widget_MaterialComponents_Chip_Filter
        val chip = view
        withColorOrResourceId(
            R.styleable.Theme_Chip_android_textColor,
            applySolidColor = {
                chip.setTextColor(it)
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_choice_chip_text_color -> {
                        chip.setTextColor(chip.mtrl_choice_chip_text_color())
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_Chip_chipBackgroundColor,
            applySolidColor = {
                chip.chipBackgroundColor = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_choice_chip_background_color -> {
                        chip.chipBackgroundColor = chip.mtrl_choice_chip_background_color()
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_Chip_rippleColor,
            applySolidColor = {
                chip.rippleColor = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_choice_chip_ripple_color -> {
                        chip.rippleColor = chip.mtrl_choice_chip_ripple_color()
                    }
                }
            }
        )

        matchThemeColor(R.styleable.Theme_Chip_chipStrokeColor)?.let {
            chip.chipStrokeColor = it.toColorStateList()
        }
        matchThemeColor(R.styleable.Theme_Chip_closeIconTint)?.let {
            chip.closeIconTint = it.toColorStateList()
        }
        matchThemeColor(R.styleable.Theme_Chip_chipIconTint)?.let {
            chip.chipIconTint = it.toColorStateList()
        }
        // TODO: We're unable to set chipSurfaceColor programmatically now.
        // matchThemeColor(R.styleable.Theme_Chip_chipSurfaceColor)?.let {
        // }
    }
)

// R.color.mtrl_choice_chip_text_color
private fun View.mtrl_choice_chip_text_color(): ColorStateList {
    val colorPrimary = Theme.get().colorPrimary
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            colorPrimary,
            colorPrimary,
            colorOnSurface.adjustAlpha(0.87f),
            colorOnSurface.adjustAlpha(0.33f)
        )
    )
}

// R.color.mtrl_choice_chip_background_color
private fun View.mtrl_choice_chip_background_color(): ColorStateList {
    val colorPrimaryAlpha24 = Theme.get().colorPrimary.adjustAlpha(0.24f)
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            colorPrimaryAlpha24,
            colorPrimaryAlpha24,
            colorOnSurface.adjustAlpha(0.10f),
            colorOnSurface.adjustAlpha(0.12f)
        )
    )
}

// R.color.mtrl_choice_chip_ripple_color
private fun View.mtrl_choice_chip_ripple_color(): ColorStateList {
    val context = context
    val colorPrimary = Theme.get().colorPrimary
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf()
        ),
        intArrayOf(
            colorPrimary.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_pressed_alpha)),
            colorOnSurface.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_focused_alpha)),
            colorOnSurface.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_focused_alpha)),
            colorOnSurface.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_hovered_alpha)),
            colorOnSurface.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_default_alpha))
        )
    )
}
