package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.button.MaterialButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor

/**
 * [MaterialButton]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialButton.md
 */
internal class ButtonTint : BaseTint<AppCompatButton>(
    attrs = R.styleable.Theme_Button,
    defStyleAttr = R.attr.materialButtonStyle,
    onTint = { helper ->
        val button = helper.view
        if (button is MaterialButton) {
            helper.findThemeColor(
                R.styleable.Theme_Button_android_textColor,
                fallback = { resourceId ->
                    withTextColor(resourceId, button)
                },
                onGet = {
                    button.setTextColor(it)
                }
            )
            // helper.findThemeColor(
            //     R.styleable.Theme_Button_backgroundTint,
            //     fallback = { resourceId ->
            //         withBackgroundTint(resourceId, view)
            //     },
            //     onGet = {
            //         ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(it))
            //     }
            // )
        }
    }
)

private fun withTextColor(resourceId: Int, view: AppCompatButton) {
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

private fun withBackgroundTint(resourceId: Int, view: AppCompatButton) {
}
