package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.widget.CompoundButtonCompat
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.color.MaterialColors
import com.google.android.material.radiobutton.MaterialRadioButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.layer
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * [MaterialCheckBox]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Checkbox.md
 */
internal class CheckBoxTint : BaseTint<AppCompatCheckBox>(
    attrs = R.styleable.Theme_CheckBox,
    defStyleAttr = android.R.attr.checkboxStyle,
    onTint = {
        val checkBox = view
        if (checkBox is MaterialCheckBox) {
            withColorOrResourceId(
                R.styleable.Theme_CheckBox_buttonTint,
                onColor = {
                    CompoundButtonCompat.setButtonTintList(checkBox, it.toColorStateList())
                },
                fallback = {
                    createCompoundButtonTint()?.let {
                        CompoundButtonCompat.setButtonTintList(checkBox, it)
                    }
                }
            )
        }
    }
)

/**
 * [MaterialRadioButton]
 *https://github.com/material-components/material-components-android/blob/master/docs/components/RadioButton.md
 */
internal class RadioButtonTint : BaseTint<AppCompatRadioButton>(
    attrs = intArrayOf(),
    defStyleAttr = android.R.attr.radioButtonStyle,
    onTint = {
        val radioButton = view
        if (radioButton is MaterialRadioButton) {
            createCompoundButtonTint()?.let {
                CompoundButtonCompat.setButtonTintList(radioButton, it)
            }
        }
    }
)

private fun ThemeHelper<*>.createCompoundButtonTint(): ColorStateList? {
    val context = view.context
    val colorControlActivated =
        findAttributeColor(R.attr.colorControlActivated)
            ?: return null
    val colorSurface = context.themeColor(R.attr.colorSurface)
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_enabled, -android.R.attr.state_checked)
        ),
        intArrayOf(
            layer(colorSurface, colorControlActivated, MaterialColors.ALPHA_FULL),
            layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_MEDIUM),
            layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED),
            layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED)
        )
    )
}
