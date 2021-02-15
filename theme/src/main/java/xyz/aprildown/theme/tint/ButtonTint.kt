package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import com.google.android.material.button.MaterialButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.float
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Button.md
 * [R.style.Widget_MaterialComponents_Button]
 * [R.style.Widget_MaterialComponents_Button_UnelevatedButton]
 * [R.style.Widget_MaterialComponents_Button_OutlinedButton]
 * [R.style.Widget_MaterialComponents_Button_TextButton]
 */
internal class ButtonTint : BaseTint<AppCompatButton>(
    attrs = R.styleable.Theme_Button,
    defStyleAttr = R.attr.materialButtonStyle,
    onTint = {
        val button = view
        matchThemeColor(R.styleable.Theme_Button_android_background)?.let {
            button.setBackgroundColor(it)
        }
        withColorOrResourceId(
            R.styleable.Theme_Button_android_textColor,
            applySolidColor = {
                button.setTextColor(it)
            },
            applyResource = {
                button.withTextColor(it)
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_Button_backgroundTint,
            applySolidColor = {
                ViewCompat.setBackgroundTintList(button, it.toColorStateList())
            },
            applyResource = {
                button.withBackgroundTint(it)
            }
        )
        if (button is MaterialButton) {
            withColorOrResourceId(
                R.styleable.Theme_Button_iconTint,
                applySolidColor = {
                    button.iconTint = it.toColorStateList()
                },
                applyResource = {
                    button.withIconTint(it)
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Button_strokeColor,
                applySolidColor = {
                    button.strokeColor = it.toColorStateList()
                },
                applyResource = {
                    button.withStrokeColor(it)
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Button_rippleColor,
                applySolidColor = {
                    button.rippleColor = it.toColorStateList()
                },
                applyResource = {
                    button.withRippleColor(it)
                }
            )
        }
    }
)

private fun TextView.withTextColor(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_btn_text_color_selector -> {
            mtrl_btn_text_color_selector(context)
        }
        R.color.mtrl_text_btn_text_color_selector -> {
            mtrl_text_btn_text_color_selector(context)
        }
        else -> null
    }?.let {
        setTextColor(it)
    }
}

/**
 * Icon tint shares the same states with text colors.
 */
private fun MaterialButton.withIconTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_btn_text_color_selector -> {
            mtrl_btn_text_color_selector(context)
        }
        R.color.mtrl_text_btn_text_color_selector -> {
            mtrl_text_btn_text_color_selector(context)
        }
        else -> null
    }?.let {
        iconTint = it
    }
}

/** [R.color.mtrl_btn_text_color_selector] */
private fun mtrl_btn_text_color_selector(context: Context): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf()
    ),
    intArrayOf(
        Theme.get().colorOnPrimary,
        context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.38f)
    )
)

/** [R.color.mtrl_text_btn_text_color_selector] */
private fun mtrl_text_btn_text_color_selector(context: Context): ColorStateList {
    val theme = Theme.get()
    val colorPrimary = theme.colorPrimary
    // We need to use the view's context to get the correct color.
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(
                android.R.attr.state_checkable,
                android.R.attr.state_checked,
                android.R.attr.state_enabled
            ),
            intArrayOf(
                android.R.attr.state_checkable,
                -android.R.attr.state_checked,
                android.R.attr.state_enabled
            ),
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            colorPrimary,
            colorOnSurface.adjustAlpha(0.6f),
            colorPrimary,
            colorOnSurface.adjustAlpha(0.38f)
        )
    )
}

private fun View.withBackgroundTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_btn_bg_color_selector -> {
            mtrl_btn_bg_color_selector(context)
        }
        R.color.mtrl_btn_text_btn_bg_color_selector -> {
            mtrl_btn_text_btn_bg_color_selector()
        }
        else -> null
    }?.let {
        ViewCompat.setBackgroundTintList(this, it)
    }
}

/** [R.color.mtrl_btn_bg_color_selector] */
private fun mtrl_btn_bg_color_selector(context: Context): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf()
    ),
    intArrayOf(
        Theme.get().colorPrimary,
        context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.12f)
    )
)

/** [R.color.mtrl_btn_text_btn_bg_color_selector] */
private fun mtrl_btn_text_btn_bg_color_selector(): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked)
    ),
    intArrayOf(
        Theme.get().colorPrimary.adjustAlpha(0.08f),
        Color.TRANSPARENT
    )
)

private fun MaterialButton.withStrokeColor(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_btn_stroke_color_selector -> {
            mtrl_btn_stroke_color_selector(context)
        }
        else -> null
    }?.let {
        strokeColor = it
    }
}

/** [R.color.mtrl_btn_stroke_color_selector] */
private fun mtrl_btn_stroke_color_selector(context: Context): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.12f)
        )
    )
}

private fun MaterialButton.withRippleColor(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_btn_ripple_color -> {
            mtrl_btn_ripple_color(context)
        }
        R.color.mtrl_btn_text_btn_ripple_color -> {
            mtrl_btn_text_btn_ripple_color(context)
        }
        else -> null
    }?.let {
        rippleColor = it
    }
}

/** [R.color.mtrl_btn_ripple_color] */
private fun mtrl_btn_ripple_color(context: Context): ColorStateList {
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
            colorOnPrimary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_pressed_alpha)),
            colorOnPrimary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_focused_alpha)),
            colorOnPrimary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_focused_alpha)),
            colorOnPrimary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_hovered_alpha)),
            colorOnPrimary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_default_alpha))
        )
    )
}

/** [R.color.mtrl_btn_text_btn_ripple_color] */
private fun mtrl_btn_text_btn_ripple_color(context: Context): ColorStateList {
    val colorPrimary = Theme.get().colorPrimary
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
            colorPrimary.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_focused_alpha)),
            colorPrimary.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_focused_alpha)),
            colorPrimary.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_hovered_alpha)),
            colorPrimary.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_default_alpha))
        )
    )
}
