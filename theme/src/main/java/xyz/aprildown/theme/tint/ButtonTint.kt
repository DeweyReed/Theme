package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
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
 * https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialButton.md
 */
internal class ButtonTint : BaseTint<AppCompatButton>(
    attrs = R.styleable.Theme_Button,
    defStyleAttr = R.attr.materialButtonStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_Button
        // R.style.Widget_MaterialComponents_Button_UnelevatedButton
        // R.style.Widget_MaterialComponents_Button_OutlinedButton
        // R.style.Widget_MaterialComponents_Button_TextButton
        val button = view
        if (button is MaterialButton) {
            withColorOrResourceId(
                R.styleable.Theme_Button_android_textColor,
                applySolidColor = {
                    button.setTextColor(it)
                },
                applyResource = {
                    withTextColor(it, button)
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Button_backgroundTint,
                applySolidColor = {
                    ViewCompat.setBackgroundTintList(button, it.toColorStateList())
                },
                applyResource = {
                    withBackgroundTint(it, button)
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Button_iconTint,
                applySolidColor = {
                    button.iconTint = it.toColorStateList()
                },
                applyResource = {
                    withIconTint(it, button)
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Button_strokeColor,
                applySolidColor = {
                    button.strokeColor = it.toColorStateList()
                },
                applyResource = {
                    withStrokeColor(it, button)
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Button_rippleColor,
                applySolidColor = {
                    button.rippleColor = it.toColorStateList()
                },
                applyResource = {
                    withRippleColor(it, button)
                }
            )
        }
    }
)

private fun withTextColor(resourceId: Int, view: MaterialButton) {
    when (resourceId) {
        R.color.mtrl_btn_text_color_selector -> {
            view.mtrl_btn_text_color_selector()
        }
        R.color.mtrl_text_btn_text_color_selector -> {
            view.mtrl_text_btn_text_color_selector()
        }
        else -> null
    }?.let {
        view.setTextColor(it)
    }
}

/**
 * Icon tint shares the same states with text colors.
 */
private fun withIconTint(resourceId: Int, view: MaterialButton) {
    when (resourceId) {
        R.color.mtrl_btn_text_color_selector -> {
            view.mtrl_btn_text_color_selector()
        }
        R.color.mtrl_text_btn_text_color_selector -> {
            view.mtrl_text_btn_text_color_selector()
        }
        else -> null
    }?.let {
        view.iconTint = it
    }
}

// R.color.mtrl_btn_text_color_selector
private fun View.mtrl_btn_text_color_selector(): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf()
    ),
    intArrayOf(
        Theme.get().colorOnPrimary,
        context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.38f)
    )
)

// R.color.mtrl_text_btn_text_color_selector
private fun View.mtrl_text_btn_text_color_selector(): ColorStateList {
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

private fun withBackgroundTint(resourceId: Int, view: MaterialButton) {
    when (resourceId) {
        R.color.mtrl_btn_bg_color_selector -> {
            view.mtrl_btn_bg_color_selector()
        }
        R.color.mtrl_btn_text_btn_bg_color_selector -> {
            view.mtrl_btn_text_btn_bg_color_selector()
        }
        else -> null
    }?.let {
        ViewCompat.setBackgroundTintList(view, it)
    }
}

// R.color.mtrl_btn_bg_color_selector
private fun View.mtrl_btn_bg_color_selector(): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf()
    ),
    intArrayOf(
        Theme.get().colorPrimary,
        context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.12f)
    )
)

// R.color.mtrl_btn_text_btn_bg_color_selector
private fun View.mtrl_btn_text_btn_bg_color_selector(): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked)
    ),
    intArrayOf(
        Theme.get().colorPrimary.adjustAlpha(0.08f),
        Color.TRANSPARENT
    )
)

private fun withStrokeColor(resourceId: Int, view: MaterialButton) {
    when (resourceId) {
        R.color.mtrl_btn_stroke_color_selector -> {
            view.mtrl_btn_stroke_color_selector()
        }
        else -> null
    }?.let {
        view.strokeColor = it
    }
}

// R.color.mtrl_btn_stroke_color_selector
private fun View.mtrl_btn_stroke_color_selector(): ColorStateList = ColorStateList(
    // It's "android.R.attr.state_checked" not "android.R.attr.checked".
    arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked)
    ),
    intArrayOf(
        Theme.get().colorPrimary,
        context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.12f)
    )
)

private fun withRippleColor(resourceId: Int, view: MaterialButton) {
    when (resourceId) {
        R.color.mtrl_btn_ripple_color -> {
            view.mtrl_btn_ripple_color()
        }
        R.color.mtrl_btn_text_btn_ripple_color -> {
            view.mtrl_btn_text_btn_ripple_color()
        }
        else -> null
    }?.let {
        view.rippleColor = it
    }
}

// R.color.mtrl_btn_ripple_color
private fun View.mtrl_btn_ripple_color(): ColorStateList {
    val context = context
    val color = Theme.get().colorOnPrimary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf()
        ),
        intArrayOf(
            color.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_pressed_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_focused_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_focused_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_hovered_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_default_alpha))
        )
    )
}

// R.color.mtrl_btn_text_btn_ripple_color
private fun View.mtrl_btn_text_btn_ripple_color(): ColorStateList {
    val color = Theme.get().colorPrimary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf()
        ),
        intArrayOf(
            color.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_pressed_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_focused_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_focused_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_hovered_alpha)),
            color.adjustAlpha(context.float(R.dimen.mtrl_low_ripple_default_alpha))
        )
    )
}
