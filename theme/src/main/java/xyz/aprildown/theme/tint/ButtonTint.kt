package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
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
    onTint = { view, helper ->
        if (view is MaterialButton) {
            helper.findThemeColor(
                R.styleable.Theme_Button_android_textColor,
                fallback = { resourceId ->
                    val theme = Theme.get()
                    when (resourceId) {
                        R.color.mtrl_btn_text_color_selector -> {
                            theme.mtrl_btn_text_color_selector()
                        }
                        R.color.mtrl_text_btn_text_color_selector -> {
                            theme.mtrl_text_btn_text_color_selector()
                        }
                        else -> null
                    }?.let {
                        view.setTextColor(it)
                    }
                },
                onGet = {
                    view.setTextColor(it)
                }
            )
            // helper.findThemeColor(R.styleable.Theme_Button_backgroundTint) {
            //     ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(it))
            // }
        }
    }
)

// R.color.mtrl_btn_text_color_selector
private fun Theme.mtrl_btn_text_color_selector(): ColorStateList = ColorStateList(
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
private fun Theme.mtrl_text_btn_text_color_selector(): ColorStateList {
    val colorPrimary = colorPrimary
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
